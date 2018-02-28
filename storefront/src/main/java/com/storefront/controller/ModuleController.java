package com.storefront.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Module;
import com.storefront.service.IModuleManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@RestController
@RequestMapping
public class ModuleController {

	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	private IModuleManagement moduleManagement;

	/**
	 * This method will returns the all the Module details
	 * 
	 * @return list of modules
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/modules")
	public @ResponseBody ResponseEntity<List<Module>> getAllModules() throws StoreFrontException {

		try {
			return new ResponseEntity<List<Module>>(moduleManagement.findAll(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching Module details for particular module: ", exception);
			return null;
		}
	}

	/**
	 * This method will returns the Module details for a given module
	 * 
	 * @param moduleId
	 * @return Modules
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/modules/{moduleId}")
	public @ResponseBody ResponseEntity<Module> getModuleDetails(@PathVariable Long moduleId)
			throws StoreFrontException {

		try {
			return new ResponseEntity<Module>(moduleManagement.findModuleByModuleId(moduleId), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching Module details for particular module: ", exception);
			return null;
		}
	}

	/**
	 * This method will returns the fluidic player for particular module ID.
	 * 
	 * @param moduleId
	 * @return Modules
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/getFluidicPlayerUrl")
	public @ResponseBody ResponseEntity<String> getFluidicPlayerUrl(@PathParam(value = "courseId") String courseId,
			@PathParam(value = "moduleId") String moduleId, OAuth2Authentication authentication)
			throws StoreFrontException {
		try {
			return new ResponseEntity<String>(moduleManagement.getFluidicPlayerUrl(courseId, moduleId, authentication), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching fludic player for particular module: ", exception);
			return null;
		}
	}

}
