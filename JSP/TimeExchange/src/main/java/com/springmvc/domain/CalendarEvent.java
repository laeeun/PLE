package com.springmvc.domain;

public class CalendarEvent {
	public Long id;         // occurrence_id
    public String title;    // todo.title
    public String start;    // yyyy-MM-dd (all-day)
    public String end;     
    private boolean completed;
    
    
	public CalendarEvent(Long id, String title, String start, String end, boolean completed) {
		super();
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
    
    
}
