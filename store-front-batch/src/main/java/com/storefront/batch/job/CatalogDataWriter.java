package com.storefront.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.storefront.batch.model.Catalog;
import com.storefront.batch.service.ICatalogManagement;

public class CatalogDataWriter implements ItemWriter<Catalog> {

	private static final Logger logger = LoggerFactory.getLogger(UserDataWriter.class);

	@Autowired
	private ICatalogManagement catalogManagement;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Catalog> catalogs) throws Exception {
		logger.info("This is CatalogDataWriter::writer method");
		catalogManagement.saveCatalogs((List<Catalog>) catalogs);
	}

}
