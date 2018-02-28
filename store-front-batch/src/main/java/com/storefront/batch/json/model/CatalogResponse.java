package com.storefront.batch.json.model;

import java.util.List;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class CatalogResponse {

	private Links links;
	private List<ResponseData> data;

}
