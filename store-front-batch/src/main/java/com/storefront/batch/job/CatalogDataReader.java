package com.storefront.batch.job;

import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_ACCESS_TOKEN;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.storefront.batch.model.Catalog;
import com.storefront.batch.service.ICatalogManagement;

public class CatalogDataReader implements ItemReader<Catalog> {

	private static final Logger logger = LoggerFactory.getLogger(CourseDataReader.class);

	@Autowired
	private ICatalogManagement catalogManagement;

	private int nextCatalogIndex;
	private List<Catalog> catalogs;
	@Value("#{jobParameters[offsetId]}")
	public Long offsetId;
	public int limit = 10;

	@Value(PROPERTY_KEY_ACP_ACCESS_TOKEN)
	private String accessToken;

	public CatalogDataReader() {
		nextCatalogIndex = 0;
	}

	@Override
	public Catalog read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("Inside CatalogDataReader::read() method");
		if (isCatalogListNotInstantiated())
			catalogs = catalogManagement.getAllCatalogs(accessToken, offsetId);
		Catalog nextCatalog = null;
		if (nextCatalogIndex < catalogs.size()) {
			nextCatalog = catalogs.get(nextCatalogIndex);
			nextCatalogIndex++;
		}
		if (nextCatalogIndex == limit) {
			offsetId = offsetId + limit;
			nextCatalogIndex = 0;
			catalogs = null;
		}
		return nextCatalog;
	}

	private boolean isCatalogListNotInstantiated() {
		return this.catalogs == null;
	}
}