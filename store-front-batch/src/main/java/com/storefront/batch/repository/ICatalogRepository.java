package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.Catalog;

/**
 * @author Praneeth.dodedu
 *
 */
public interface ICatalogRepository extends CrudRepository<Catalog, Long> {

	public Catalog findCatalogByCatalogId(Long catalogId);

}
