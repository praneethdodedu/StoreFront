package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.model.Module;

/**
 * @author Praneeth.dodedu
 *
 */
public interface ICatalogManagement {

	public List<Catalog> findAll();

	public Course findCoursesByCourseId(Long courseId);

	public Module findMoudlebyModuleId(Long moduleId);

	public Catalog findCatalogByCatalogId(String catalogId);

	public List<Catalog> searchCatalogsByCatalogName(String catalogName);

	public List<Catalog> getAllCatalogs(String accessToken) throws StoreFrontException;

}
