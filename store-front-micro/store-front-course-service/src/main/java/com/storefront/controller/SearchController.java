package com.storefront.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.service.ICatalogManagement;
import com.storefront.service.ICourseManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@RestController
@RequestMapping
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private ICourseManagement courseManagement;

	@Autowired
	private ICatalogManagement catalogManagement;

	/**
	 * This method will search teh catalogs based on the catalog name
	 * 
	 */
	@GetMapping(value = "/catalogSearch")
	public @ResponseBody ResponseEntity<List<Catalog>> searchCatalogs(@PathParam("catalogName") String catalogName)
			throws StoreFrontException {

		try {
			return new ResponseEntity<List<Catalog>>(catalogManagement.searchCatalogsByCatalogName(catalogName),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while searching catalog list: ", exception);
			return null;
		}
	}

	/**
	 * This method will search teh catalogs based on the catalog name
	 * 
	 */
	@GetMapping(value = "/courseSearch")
	public @ResponseBody ResponseEntity<List<Course>> searchCourses(@PathParam("courseName") String courseName)
			throws StoreFrontException {

		try {
			return new ResponseEntity<List<Course>>(courseManagement.searchCoursesByCourseName(courseName),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while searching course list: ", exception);
			return null;
		}
	}

}
