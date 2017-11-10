package com.sapient.data;

import java.util.List;

public class GroupOrderWarningResponse {
	
	private String message;
	private List<Long> groupIds;
	
	public GroupOrderWarningResponse(String message, List<Long> groupIds) {
		this.message = message;
		this.groupIds = groupIds;
	}
	
	@Override
	public String toString() {
		return "Violation for "+groupIds.toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}
}

