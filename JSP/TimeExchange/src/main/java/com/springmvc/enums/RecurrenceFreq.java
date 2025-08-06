package com.springmvc.enums;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 반복 주기 (DB enum과 1:1 매핑: NONE, DAILY, WEEKLY, MONTHLY)
 * - isRecurring(): 반복 여부
 * - toIcsRRule(...): iCalendar RRULE 문자열 생성 (서버 측에서 쓰거나, FullCalendar rrule 변환 시 참고)
 * - expandOccurrences(...): start~end 범위의 발생일(LocalDate) 전개 (todo_occurrence 생성에 사용)
 */
public enum RecurrenceFreq {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY;

    /** DB 문자열을 안전하게 매핑 (null, 대소문자 혼용 대응) */
    public static RecurrenceFreq fromDb(String v) {
        if (v == null || v.isBlank()) return NONE;
        switch (v.trim().toUpperCase(Locale.ROOT)) {
            case "DAILY": return DAILY;
            case "WEEKLY": return WEEKLY;
            case "MONTHLY": return MONTHLY;
            case "NONE": return NONE;
            default: throw new IllegalArgumentException("Unknown freq: " + v);
        }
    }

    public boolean isRecurring() {
        return this != NONE;
    }

    /** 날짜 무결성 체크 */
    public static void validateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start_date and end_date must not be null for recurring todos.");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end_date must be on or after start_date.");
        }
    }

    /**
     * RRULE(ICS) 문자열 생성
     * - UNTIL은 규칙상 UTC로 적는 게 안전합니다.
     * - KST 기준으로 "마감일 다음날 00:00 직전"까지 포함되도록 end+1일의 00:00(KST)을 UTC로 변환해 UNTIL 설정.
     * - WEEKLY: byWeekdays가 없으면 시작일 요일 1개로 고정
     * - MONTHLY: byMonthDay가 없으면 시작일의 일자 사용
     */
    public String toIcsRRule(LocalDate start, LocalDate end,
                             Set<DayOfWeek> byWeekdays, Integer byMonthDay) {
        if (this == NONE) return ""; // 단건은 RRULE 없음
        validateRange(start, end);

        // DTSTART (all-day 기준, 로컬 날짜)
        String dtStart = start.format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd

        // UNTIL: end의 다음날 00:00 KST 직전 => end.plusDays(1) 00:00 KST -> UTC 변환 -> yyyyMMdd'T'HHmmss'Z'
        ZonedDateTime untilKstStartOfNextDay = end.plusDays(1).atStartOfDay(ZoneId.of("Asia/Seoul"));
        ZonedDateTime untilUtc = untilKstStartOfNextDay.withZoneSameInstant(ZoneOffset.UTC);
        String until = untilUtc.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

        StringBuilder sb = new StringBuilder();
        sb.append("FREQ=").append(name()).append(';');
        sb.append("DTSTART=").append(dtStart).append(';');
        sb.append("UNTIL=").append(until);

        switch (this) {
            case WEEKLY -> {
                Set<DayOfWeek> set = (byWeekdays == null || byWeekdays.isEmpty())
                        ? EnumSet.of(start.getDayOfWeek())
                        : EnumSet.copyOf(byWeekdays);
                sb.append(";BYDAY=").append(toIcsByDay(set));
            }
            case MONTHLY -> {
                int dom = (byMonthDay == null || byMonthDay <= 0)
                        ? start.getDayOfMonth()
                        : byMonthDay;
                sb.append(";BYMONTHDAY=").append(dom);
            }
            default -> { /* DAILY은 추가 파라미터 없음 */ }
        }
        return sb.toString();
    }

    /** 주중 표기(ICS BYDAY): MO,TU,WE,TH,FR,SA,SU */
    private static String toIcsByDay(Set<DayOfWeek> days) {
        return days.stream()
                .sorted(Comparator.comparingInt(DayOfWeek::getValue))
                .map(RecurrenceFreq::icsDay)
                .collect(Collectors.joining(","));
    }
    private static String icsDay(DayOfWeek d) {
        return switch (d) {
            case MONDAY -> "MO";
            case TUESDAY -> "TU";
            case WEDNESDAY -> "WE";
            case THURSDAY -> "TH";
            case FRIDAY -> "FR";
            case SATURDAY -> "SA";
            case SUNDAY -> "SU";
        };
    }

    /**
     * 발생일 전개(occurrence 생성용)
     * @param start start_date
     * @param end end_date (포함)
     * @param byWeekdays WEEKLY일 때 요일 지정(없으면 start의 요일 1개)
     * @param byMonthDay MONTHLY일 때 일자 지정(없으면 start의 일자, 29~31 보정)
     * @return LocalDate 리스트(하루짜리 발생일)
     */
    public List<LocalDate> expandOccurrences(LocalDate start, LocalDate end,
                                             Set<DayOfWeek> byWeekdays, Integer byMonthDay) {
        if (!isRecurring()) {
            // NONE의 경우 단일 발생만 필요하다면 start만 반환하거나 빈 리스트로 처리
            return start == null ? List.of() : List.of(start);
        }
        validateRange(start, end);

        List<LocalDate> dates = new ArrayList<>();
        switch (this) {
            case DAILY -> {
                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                    dates.add(d);
                }
            }
            case WEEKLY -> {
                Set<DayOfWeek> set = (byWeekdays == null || byWeekdays.isEmpty())
                        ? EnumSet.of(start.getDayOfWeek())
                        : EnumSet.copyOf(byWeekdays);
                // 시작 주부터 end까지, 각 요일을 채움
                LocalDate cursor = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                while (!cursor.isAfter(end)) {
                    for (DayOfWeek w : set) {
                        LocalDate candidate = cursor.with(TemporalAdjusters.nextOrSame(w));
                        if (!candidate.isBefore(start) && !candidate.isAfter(end)) {
                            dates.add(candidate);
                        }
                    }
                    cursor = cursor.plusWeeks(1);
                }
                Collections.sort(dates);
            }
            case MONTHLY -> {
                int dom = (byMonthDay == null || byMonthDay <= 0)
                        ? start.getDayOfMonth()
                        : byMonthDay;
                for (LocalDate m = start; !m.isAfter(end); m = m.plusMonths(1)) {
                    int last = YearMonth.from(m).lengthOfMonth();
                    int day = Math.min(dom, last); // 31일 보정
                    LocalDate d = LocalDate.of(m.getYear(), m.getMonth(), day);
                    if (!d.isBefore(start) && !d.isAfter(end)) {
                        dates.add(d);
                    }
                }
            }
            default -> { /* no-op */ }
        }
        return dates;
    }

    /** FullCalendar rrule 옵션(JSON에 넣을 값) 생성 보조 (서버에서 DTO 만들 때 참고) */
    public Map<String, Object> toFullCalendarRRule(LocalDate start, LocalDate end,
                                                    Set<DayOfWeek> byWeekdays, Integer byMonthDay) {
        if (this == NONE) return Map.of();
        validateRange(start, end);
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("freq", name().toLowerCase(Locale.ROOT)); // daily/weekly/monthly

        r.put("dtstart", start.toString()); // yyyy-MM-dd
        // FullCalendar는 until을 로컬 날짜 문자열로 줘도 파싱 가능. end 포함 처리하려면 end 그대로 두는 게 일반적
        r.put("until", end.toString());

        if (this == WEEKLY) {
            Set<DayOfWeek> set = (byWeekdays == null || byWeekdays.isEmpty())
                    ? EnumSet.of(start.getDayOfWeek())
                    : EnumSet.copyOf(byWeekdays);
            List<String> days = set.stream()
                    .sorted(Comparator.comparingInt(DayOfWeek::getValue))
                    .map(RecurrenceFreq::fcDay) // "mo","tu"...
                    .toList();
            r.put("byweekday", days);
        } else if (this == MONTHLY) {
            int dom = (byMonthDay == null || byMonthDay <= 0)
                    ? start.getDayOfMonth()
                    : byMonthDay;
            r.put("bymonthday", List.of(dom));
        }
        return r;
    }

    private static String fcDay(DayOfWeek d) {
        return switch (d) {
            case MONDAY -> "mo";
            case TUESDAY -> "tu";
            case WEDNESDAY -> "we";
            case THURSDAY -> "th";
            case FRIDAY -> "fr";
            case SATURDAY -> "sa";
            case SUNDAY -> "su";
        };
    }
}
