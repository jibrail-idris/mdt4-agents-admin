package com.mt4agents.web.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DTResponse {
	private Integer sEcho;
	private Integer iTotalRecords;
	private Integer iTotalDisplayRecords;
	private List<List<?>> aaData;
	
	public DTResponse() {
		aaData = new ArrayList<List<?>>();
	}
	
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public Integer getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<List<?>> getAaData() {
		return aaData;
	}
	public void setAaData(List<List<?>> aaData) {
		this.aaData = aaData;
	}
}
