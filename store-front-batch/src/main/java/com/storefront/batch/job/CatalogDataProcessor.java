package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.storefront.batch.model.Catalog;

public class CatalogDataProcessor implements ItemProcessor<Catalog, Catalog> {

	private static final Logger log = LoggerFactory.getLogger(UserDataProcessor.class);

	@Override
	public Catalog process(final Catalog catalog) throws Exception {
		log.info("Inside CatalogDataProcessor:: process method");
		return catalog;
	}

}