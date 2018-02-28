package com.storefront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.storefront.model.Catalog;

public interface ICatalogRepository extends CrudRepository<Catalog, String> {

	public Catalog findCatalogByCatalogId(String catalogId);

	@Query("FROM Catalog c WHERE c.catalogName LIKE CONCAT('%',:catalogName,'%')")
	public List<Catalog> searchByCatalogName(@Param("catalogName") String catalogName);

}
