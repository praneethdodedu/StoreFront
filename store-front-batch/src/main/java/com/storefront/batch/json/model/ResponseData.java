package com.storefront.batch.json.model;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class ResponseData {

	private String id;
	private String type;
	private Attributes attributes;
	private Relationship relationships;

}
