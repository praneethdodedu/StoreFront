package com.storefront.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.model.LearningProgram;
import com.storefront.model.User;
import com.storefront.repository.IUserRepository;
import com.storefront.service.ICatalogManagement;
import com.storefront.service.ICourseManagement;
import com.storefront.service.ILearningProgramManagement;
import com.storefront.service.IUserManagement;

@RestController
@RequestMapping(value = "/authenticate")
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private IUserRepository userRepo;

	@Autowired
	private ICourseManagement courseManagement;

	@Autowired
	private IUserManagement iUserManagement;

	@Autowired
	private ICatalogManagement catalogManagement;

	@Autowired
	private ILearningProgramManagement learningProgramManagement;

	/**
	 * GetAllJobs returns true on authentication success else returns false
	 * 
	 **/
	@PostMapping
	public ResponseEntity<Boolean> getAllJobs(@RequestParam String userName, @RequestParam String password)
			throws StoreFrontException {
		User authUser = null;
		try {
			authUser = userRepo.findByUserName(userName);
			if (authUser != null) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		} catch (Exception ex) {
			return null;
		}
	}

	@GetMapping(value = "/index")
	public String index(HttpServletRequest request, OAuth2Authentication auth) throws StoreFrontException {
		LOGGER.info("Inside index method of Auth controller");
		final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		List<Course> courses = courseManagement.getAllCourses(details.getTokenValue());
		List<Catalog> catalogs = catalogManagement.getAllCatalogs(details.getTokenValue());
		List<LearningProgram> learningPrograms = learningProgramManagement
				.getAllLearningPrograms(details.getTokenValue());
		User user = iUserManagement.getCurrentUser(details.getTokenValue());
		user.setCatalogs(catalogs);
		user.setCourses(courses);
		user.setLearningPrograms(learningPrograms);
		iUserManagement.saveUser(user);
		return "index";
	}

	@GetMapping(value = "/isLoggedIn")
	public HttpStatus isLoggedIn() throws StoreFrontException {
		LOGGER.info("Inside index method of Auth controller");
		return null;
	}

}
