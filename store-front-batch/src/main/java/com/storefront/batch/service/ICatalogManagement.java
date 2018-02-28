package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.Catalog;

public interface ICatalogManagement {

	public List<Catalog> getAllCatalogs(String token, Long offsetId) throws StoreFrontException;

	public void saveCatalogs(List<Catalog> allCatalogs);

}
