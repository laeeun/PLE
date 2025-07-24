package com.springmvc.domain;

import java.time.LocalDateTime;

public class PurchaseRequestDTO {
	  private Long request_id;             // 거래 요청 ID (기본키 역할)
	  private Long talent_id;              // 요청한 대상 재능 ID
	  private String buyer_id;             // 요청을 보낸 구매자 ID (member 테이블의 member_id)
	  private String seller_id;            // 요청을 받은 판매자 ID (재능 등록자)
	  private String status;               // 요청 상태 ("PENDING", "APPROVED", "REJECTED")
	  private LocalDateTime requested_at;  // 요청을 보낸 시각
	  private LocalDateTime approved_at;   // 판매자가 승인한 시각 (승인된 경우에만 값 존재)
	  public PurchaseRequestDTO(Long request_id, Long talent_id, String buyer_id, String seller_id, String status,
			LocalDateTime requested_at, LocalDateTime approved_at) {
		super();
		this.request_id = request_id;
		this.talent_id = talent_id;
		this.buyer_id = buyer_id;
		this.seller_id = seller_id;
		this.status = status;
		this.requested_at = requested_at;
		this.approved_at = approved_at;
	  }
	  public PurchaseRequestDTO() {}
	public Long getRequest_id() {
		return request_id;
	}
	public void setRequest_id(Long request_id) {
		this.request_id = request_id;
	}
	public Long getTalent_id() {
		return talent_id;
	}
	public void setTalent_id(Long talent_id) {
		this.talent_id = talent_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getRequested_at() {
		return requested_at;
	}
	public void setRequested_at(LocalDateTime requested_at) {
		this.requested_at = requested_at;
	}
	public LocalDateTime getApproved_at() {
		return approved_at;
	}
	public void setApproved_at(LocalDateTime approved_at) {
		this.approved_at = approved_at;
	}
	@Override
	public String toString() {
		return "PurchaseRequestDTO [request_id=" + request_id + ", talent_id=" + talent_id + ", buyer_id=" + buyer_id
				+ ", seller_id=" + seller_id + ", status=" + status + ", requested_at=" + requested_at
				+ ", approved_at=" + approved_at + "]";
	}	  
}
