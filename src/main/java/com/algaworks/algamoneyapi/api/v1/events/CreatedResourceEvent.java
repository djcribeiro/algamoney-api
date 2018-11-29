package com.algaworks.algamoneyapi.api.v1.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class CreatedResourceEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private HttpServletResponse response; 
	private Long id;
	
	public CreatedResourceEvent(Object source, HttpServletResponse pResponse, Long pId) {
		super(source);
		this.response = pResponse;
		this.id = pId;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
