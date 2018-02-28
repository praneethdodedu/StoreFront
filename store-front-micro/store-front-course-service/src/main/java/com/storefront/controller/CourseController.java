package com.storefront.controller;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Course;
import com.storefront.model.User;
import com.storefront.service.ICourseManagement;
import com.storefront.service.IUserManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@RestController
@RequestMapping
public class CourseController {

	private static Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private ICourseManagement iCourseManagement;

	@Autowired
	private IUserManagement userManagement;

	/**
	 * This method returns all courses list
	 * 
	 * @return Course List
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/courses")
	public @ResponseBody ResponseEntity<List<Course>> getAllCourses(OAuth2Authentication authentication)
			throws StoreFrontException {
		try {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			return new ResponseEntity<List<Course>>(iCourseManagement.getAllPublishedCourses(details.getTokenValue()),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching published course list: ", exception);
			return null;
		}
	}

	/**
	 * This method return course details by course id
	 * 
	 * @param courseId
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/courses/{courseId}")
	public @ResponseBody ResponseEntity<Course> getCourseByCourseId(@PathVariable Long courseId,
			OAuth2Authentication authentication) throws StoreFrontException {
		try {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			User user = userManagement.getCurrentUser(details.getTokenValue());
			return new ResponseEntity<Course>(iCourseManagement.findCourseByCourseId(courseId, user), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching course details for particular course id: ", exception);
			return null;
		}
	}

	/**
	 * This method return all distinct course type
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/getAllCourseTypes")
	public @ResponseBody ResponseEntity<List<String>> getCourseTypes() throws StoreFrontException {
		try {
			return new ResponseEntity<List<String>>(iCourseManagement.getAllCourseTypes(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all distinct course type: ", exception);
			return null;
		}
	}

	/**
	 * This method return all distinct course state
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/getAllCourseStatus")
	public @ResponseBody ResponseEntity<List<String>> getCourseStates() throws StoreFrontException {
		try {
			return new ResponseEntity<List<String>>(iCourseManagement.getAllCourseStates(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all distinct course state: ", exception);
			return null;
		}
	}

	/**
	 * This method return all distinct course tags
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/getAllCourseTags")
	public @ResponseBody ResponseEntity<List<String>> getCourseTags() throws StoreFrontException {
		try {
			return new ResponseEntity<List<String>>(iCourseManagement.getAllCourseTags(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all distinct course tag: ", exception);
			return null;
		}
	}

	/**
	 * This method return all course by Author/Admin
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/getAllCoursesByAuthor")
	public @ResponseBody ResponseEntity<List<Course>> getCoursesByAuthor(OAuth2Authentication authentication)
			throws StoreFrontException {
		try {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			return new ResponseEntity<List<Course>>(iCourseManagement.getAllCourseByAuthor(details.getTokenValue()),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all courses by Admin/author: ", exception);
			return null;
		}
	}

	/**
	 * This method will publish/set the amount for particular course
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@PostMapping(value = "/publishCourse")
	public @ResponseBody ResponseEntity<Course> publishCourse(@RequestBody Map<String, Object> payload)
			throws StoreFrontException {
		try {
			return new ResponseEntity<Course>(
					iCourseManagement.publishCourse(Long.parseLong(payload.get("courseId").toString()),
							(String) payload.get("paymentType"), Double.parseDouble(payload.get("amount").toString()),
							(boolean) payload.get("isPublished"), (String) payload.get("trialPeriod")),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all courses by Admin/author: ", exception);
			return null;
		}
	}

	/**
	 * This method will make course enable/disable to view in preview course.
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@PostMapping(value = "courses/previewCourse")
	public @ResponseBody ResponseEntity<Course> previewCourse(@RequestBody Map<String, Object> payload)
			throws StoreFrontException {
		try {
			return new ResponseEntity<Course>(iCourseManagement.disableOrEnableCoursePreview(
					payload.get("courseId").toString(), Boolean.parseBoolean(payload.get("isPreviewCourse").toString()),
					payload.get("moduleIds").toString()), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while configurating course as preview course: ", exception);
			return null;
		}
	}

	/**
	 * This method will make course enable/disable to view in preview course.
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "courses/getPreviewCourses")
	public @ResponseBody ResponseEntity<List<Course>> getPreviewCourses() throws StoreFrontException {
		try {
			return new ResponseEntity<List<Course>>(iCourseManagement.getAllPreviewCourses(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching all preview courses: ", exception);
			return null;
		}
	}

	/**
	 * This method will enroll particular course.
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@PostMapping(value = "courses/enrollCourse")
	public @ResponseBody ResponseEntity<Course> enrollCourse(@RequestBody Map<String, Object> payload,
			OAuth2Authentication authentication) throws StoreFrontException {
		try {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			return new ResponseEntity<Course>(
					iCourseManagement.enrollCourse(payload.get("courseId").toString(), details.getTokenValue()),
					HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while configurating course as preview course: ", exception);
			return null;
		}
	}

	/**
	 * This method will set the course as bookmark .
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "courses/bookMarkCourse")
	public @ResponseBody ResponseEntity<Course> bookMarkCourse(@PathParam("courseId") String courseId,
			@PathParam("isBookMarked") String isBookMarked, OAuth2Authentication authentication)
			throws StoreFrontException {
		try {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			return new ResponseEntity<Course>(iCourseManagement.bookMarkCourse(courseId,
					Boolean.parseBoolean(isBookMarked), details.getTokenValue()), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while configurating course as preview course: ", exception);
			return null;
		}
	}

}
