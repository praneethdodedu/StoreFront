package com.storefront.json.model;

import lombok.Data;

public @Data class MyCourse {

	private Long id;
	private String name;
	private String type;

	public MyCourse() {
		// TODO Auto-generated constructor stub
	}

	public MyCourse(Long id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

}
