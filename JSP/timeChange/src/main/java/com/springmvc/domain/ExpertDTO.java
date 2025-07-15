package com.springmvc.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpertDTO {
	private Long expert_board_id;
	private String expert_id;
	private String title;
	private String content;
	private String category;
	private int price;
	private String available_time;
	private LocalDateTime created_at = LocalDateTime.now();
	private LocalDateTime updated_at = LocalDateTime.now();
	private String nickname;
	public ExpertDTO() {}
	public ExpertDTO(Long expert_board_id, String expert_id, String title, String content, String category, int price,
			String available_time, LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.expert_board_id = expert_board_id;
		this.expert_id = expert_id;
		this.title = title;
		this.content = content;
		this.category = category;
		this.price = price;
		this.available_time = available_time;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public String getFormattedCreatedAt() {
        if (created_at == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return created_at.format(formatter);
    }
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Long getExpert_board_id() {
		return expert_board_id;
	}
	public void setExpert_board_id(Long expert_board_id) {
		this.expert_board_id = expert_board_id;
	}
	public String getExpert_id() {
		return expert_id;
	}
	public void setExpert_id(String expert_id) {
		this.expert_id = expert_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAvailable_time() {
		return available_time;
	}
	public void setAvailable_time(String available_time) {
		this.available_time = available_time;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "ExpertDTO [expert_board_id=" + expert_board_id + ", expert_id=" + expert_id + ", title=" + title
				+ ", content=" + content + ", category=" + category + ", price=" + price + ", available_time="
				+ available_time + ", created_at=" + created_at + ", updated_at=" + updated_at + ", nickname="
				+ nickname + "]";
	}
	
	
}
