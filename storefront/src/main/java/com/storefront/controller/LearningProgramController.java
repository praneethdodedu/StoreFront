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
import com.storefront.model.LearningProgram;
import com.storefront.service.ILearningProgramManagement;

@RestController
@RequestMapping
public class LearningProgramController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LearningProgramController.class);

	@Autowired
	private ILearningProgramManagement learningProgramManagement;

	/**
	 * This method returns all leaning programs list
	 * 
	 * @return Learning program List
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/learningPrograms")
	public @ResponseBody ResponseEntity<List<LearningProgram>> getAllCourses() throws StoreFrontException {
		try {
			return new ResponseEntity<List<LearningProgram>>(learningProgramManagement.findAll(), HttpStatus.OK);
		} catch (Exception exception) {
			LOGGER.info("Error while fetching learning program list: ", exception);
			return null;
		}
	}

	/**
	 * This method return Learning Program details by id
	 * 
	 * @param learningProgramId
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/learningPrograms/{learningProgramId}")
	public @ResponseBody ResponseEntity<LearningProgram> getCourseByCourseId(@PathVariable Long learningProgramId)
			throws StoreFrontException {
		try {
			return new ResponseEntity<LearningProgram>(
					learningProgramManagement.findLearningProgramById(learningProgramId), HttpStatus.OK);
		} catch (Exception exception) {
			LOGGER.info("Error while fetching Learning Programs details for particular learnignProgram id: ",
					exception);
			return null;
		}
	}

}
