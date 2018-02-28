package com.storefront.json.model;

import java.util.List;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class ModuleResponse {

	private Links links;
	private List<ResponseData> data;

}
