package com.springmvc.domain;

import java.time.LocalDateTime;

public class RankingDTO {
	private long ranking_id; //랭킹아이디
	private long talent_id; //재능아이디
	private String member_id;//멤버아이디
	private int total_sales;//총판매량
	private double score; //점수
	private LocalDateTime created_at = LocalDateTime.now();//
	private LocalDateTime updated_at = LocalDateTime.now();//갱신시간
	private String member_nickname;
	private String category;
	
	public RankingDTO(int ranking_id, int talent_id, double score, int total_sales, LocalDateTime updated_at) {
		super();
		this.ranking_id = ranking_id;
		this.talent_id = talent_id;
		this.score = score;
		this.total_sales = total_sales;
		this.updated_at = updated_at;
	}
	
	public RankingDTO() {
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}	
	public long getRanking_id() {
		return ranking_id;
	}

	public void setRanking_id(long ranking_id) {
		this.ranking_id = ranking_id;
	}

	public long getTalent_id() {
		return talent_id;
	}

	public void setTalent_id(long talent_id) {
		this.talent_id = talent_id;
	}

	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public int getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(int total_sales) {
		this.total_sales = total_sales;
	}
	
	@Override
	public String toString() {
		return "rankingDTO [ranking_id=" + ranking_id + ", talent_id=" + talent_id + ", score=" + score
				+ ", total_sales=" + total_sales + ", updated_at=" + updated_at + "]";
	}	
}
