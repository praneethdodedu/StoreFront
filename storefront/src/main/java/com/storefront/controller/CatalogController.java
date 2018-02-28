package com.storefront.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.service.ICatalogManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@RestController
@RequestMapping
public class CatalogController {

	private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

	@Autowired
	private ICatalogManagement catalogManagement;

	/**
	 * This method will query and retrieve all the catalogs available
	 * 
	 */
	@GetMapping(value = "/catalogs")
	public @ResponseBody ResponseEntity<List<Catalog>> getAllCatalog() throws StoreFrontException {

		try {
			return new ResponseEntity<List<Catalog>>(catalogManagement.findAll(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching catalog list: ", exception);
			return null;
		}
	}

	/**
	 * This method returns catalog details for particular catalog id
	 * 
	 * @param catalogId
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/catalogs/{catalogId}")
	public @ResponseBody ResponseEntity<Catalog> getCatalogByCatalogId(@PathVariable String catalogId)
			throws StoreFrontException {
		try {
			return new ResponseEntity<Catalog>(catalogManagement.findCatalogByCatalogId(catalogId), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching catalog details for particular catalog: ", exception);
			return null;
		}
	}
}
