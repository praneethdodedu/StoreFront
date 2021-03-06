package com.storefront.batch.json.model;

import java.util.List;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class LearningProgramResponse {
	
	private Links links;
	private List<ResponseData> data;

}
