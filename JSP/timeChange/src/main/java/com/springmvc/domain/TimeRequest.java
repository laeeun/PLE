package com.springmvc.domain;

public class TimeRequest {
    private String memberId;
    
    private int durationTime; // 충전/차감 시간 (분 단위)

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

	@Override
	public String toString() {
		return "TimeRequest [memberId=" + memberId + ", durationTime=" + durationTime + "]";
	}

	
    
    
}
