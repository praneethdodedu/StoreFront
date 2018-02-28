package com.storefront.service.impl;

import static com.storefront.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.util.ApplicationConstants.PARAM_KEY_CATALOG_ID;
import static com.storefront.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_COURSE_ALL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.CourseResponse;
import com.storefront.json.model.ResponseData;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.model.CoursePublish;
import com.storefront.model.Enrollment;
import com.storefront.model.Module;
import com.storefront.model.User;
import com.storefront.model.UserCourseOperation;
import com.storefront.repository.ICourseRepository;
import com.storefront.repository.IEnrollmentRepository;
import com.storefront.repository.IModuleRepository;
import com.storefront.repository.IUserCourseRepository;
import com.storefront.service.ICourseManagement;
import com.storefront.service.IEnrollmentManagement;
import com.storefront.service.IModuleManagement;
import com.storefront.service.IUserManagement;
import com.storefront.util.CommonsUtil;
import com.storefront.util.HTTPClientUtil;
import com.storefront.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class CourseManagementImpl implements ICourseManagement {

	private static Logger logger = LoggerFactory.getLogger(CourseManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_COURSE_ALL)
	private String courselisturl;

	@Autowired
	private ICourseRepository courseRepo;

	@Autowired
	private IModuleRepository moduleRepository;

	@Autowired
	private IEnrollmentRepository enrollmentRepository;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private IModuleManagement iModuleManagement;

	@Autowired
	private IUserManagement iUserManagement;

	@Autowired
	private IUserManagement userManagement;

	@Autowired
	private IEnrollmentManagement enrollmentManagement;

	@Autowired
	private IUserCourseRepository userCourseRepository;

	@Override
	public Course findCourseByCourseId(Long courseId, User user) throws StoreFrontException {
		Course course = courseRepo.findByCourseId(courseId);
		Enrollment enrollment = enrollmentManagement.findByUserAndCourse(user, course);
		UserCourseOperation courseOperation = userCourseRepository
				.findOne(user.getUserId() + "_" + course.getCourseId());
		if (enrollment != null) {
			course.setEnrolled(true);
			course.setProgressCount(enrollment.getProgressCount());
		}
		if (courseOperation != null)
			course.setBookMarked(courseOperation.isBookMarked());
		return course;
	}

	@Override
	public List<Course> searchCoursesByCourseName(String courseName) {
		return courseRepo.searchByCourseName(courseName);
	}

	@Override
	public List<Course> getAllPublishedCourses(String accessToken) throws StoreFrontException {
		List<Course> courses = (List<Course>) courseRepo.findAll();
		User user = userManagement.getCurrentUser(accessToken);
		Iterator<Course> iterable = courses.iterator();
		while (iterable.hasNext()) {
			Course course = iterable.next();
			if (course.getCoursePublish() == null || course.getCoursePublish().getIsPublished() == null
					|| !course.getCoursePublish().getIsPublished()) {
				iterable.remove();
			}
			UserCourseOperation courseOperation = userCourseRepository
					.findOne(user.getUserId() + "_" + course.getCourseId());
			if (courseOperation != null)
				course.setBookMarked(courseOperation.isBookMarked());
		}
		return courses;
	}

	@Override
	public Course publishCourse(Long courseId, String paymentType, Double amount, boolean isPublished,
			String trialPeriod) {
		Course course = courseRepo.findByCourseId(courseId);
		if (course.getCoursePublish() != null) {
			course.getCoursePublish().setAmount(amount);
			course.getCoursePublish().setIsPublished(isPublished);
			course.getCoursePublish().setPaymentType(paymentType);
			course.getCoursePublish().setTrialPeriod(trialPeriod);
		}
		if (course.getCoursePublish() == null) {
			CoursePublish coursePublish = new CoursePublish();
			coursePublish.setCoursePublishId(course.getCourseId());
			coursePublish.setAmount(amount);
			coursePublish.setIsPublished(isPublished);
			coursePublish.setPaymentType(paymentType);
			coursePublish.setTrialPeriod(trialPeriod);
			course.setCoursePublish(coursePublish);
		}
		return courseRepo.save(course);
	}

	@Override
	public void findCoursesByCatalogId(Catalog catalog, String token) throws StoreFrontException {
		URIBuilder builder = null;

		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(courselisturl);
			builder.addParameter(PARAM_KEY_CATALOG_ID, catalog.getCatalogId().toString());
			URL url = builder.build().toURL();
			String courseResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(courseResp)) {
				CourseResponse courseResponse = null;
				try {
					courseResponse = (CourseResponse) jsonMapper.fromJson(courseResp, CourseResponse.class);
					List<Course> courses = new ArrayList<Course>();
					for (ResponseData data : courseResponse.getData()) {
						User userAuthor = null;
						if (data.getRelationships() != null && data.getRelationships().getAuthors() != null) {
							try {
								userAuthor = iUserManagement.fetchUserByUserId(
										Long.parseLong(data.getRelationships().getAuthors().getData().get(0).getId()));
							} catch (NumberFormatException | StoreFrontException exception) {
								logger.error("Error in fetching user by user id for author : {}", exception);
							}
						}
						Course course = new Course(Long.parseLong(data.getId()), data.getAttributes().getName(),
								data.getAttributes().getDescription(), data.getAttributes().getOverview(),
								data.getAttributes().getState(), data.getAttributes().getCourseType(),
								data.getAttributes().getEnrollmentType(),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()), true,
								data.getAttributes().getTags());
						course.setAuthor(userAuthor);
						courses.add(course);
					}
					catalog.setCourses(courses);
				} catch (Exception exception) {
					logger.error("Error in fetching course list for particluar catalog from ACP {}", exception);
					throw new StoreFrontException("Error in fetching course list for particluar catalog from ACP  : "
							+ exception.getMessage());
				}
			}
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP to get the course list for particluar catalog: {}", exception);
			throw new StoreFrontException(
					"Error while connecting to ACP to get the course list for particluar catalog" + exception);
		}

	}

	@Override
	public List<Course> getAllCourses(String accessToken) throws StoreFrontException {
		URIBuilder builder = null;

		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + accessToken);
		try {
			builder = new URIBuilder(courselisturl);
			URL url = builder.build().toURL();
			String courseResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(courseResp)) {
				CourseResponse courseResponse = null;
				try {
					courseResponse = (CourseResponse) jsonMapper.fromJson(courseResp, CourseResponse.class);
					List<Course> courses = new ArrayList<>();
					for (ResponseData data : courseResponse.getData()) {
						User userAuthor = null;
						if (data.getRelationships() != null && data.getRelationships().getAuthors() != null) {
							try {
								userAuthor = iUserManagement.fetchUserByUserId(
										Long.parseLong(data.getRelationships().getAuthors().getData().get(0).getId()));
							} catch (NumberFormatException | StoreFrontException exception) {
								logger.error("Error in fetching user by user id for author : {}", exception);
							}
						}
						Course course = new Course(Long.parseLong(data.getId()), data.getAttributes().getName(),
								data.getAttributes().getDescription(), data.getAttributes().getOverview(),
								data.getAttributes().getState(), data.getAttributes().getCourseType(),
								data.getAttributes().getEnrollmentType(),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()), true,
								data.getAttributes().getTags());
						course.setAuthor(userAuthor);
						try {
							List<Module> modules = iModuleManagement.getModulesByCourseId(course.getCourseId(),
									accessToken);
							course.setModule(modules);
						} catch (Exception exception) {
							logger.error("Error in fetching module list for particluar course{}", exception);
						}
						courses.add(course);
					}
					return courses;
				} catch (Exception exception) {
					logger.error("Error in fetching course list from ACP {}", exception);
					throw new StoreFrontException(
							"Error in fetching course list from ACP  : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP to get the course list: {}", exception);
			throw new StoreFrontException("Error while connecting to ACP to get the course list" + exception);
		}
	}

	@Override
	public List<Course> findAll() {
		return (List<Course>) courseRepo.findAll();
	}

	@Override
	public List<String> getAllCourseTypes() {
		return courseRepo.findAllCourseType();
	}

	@Override
	public List<String> getAllCourseStates() {
		return courseRepo.findAllCourseState();
	}

	@Override
	public List<String> getAllCourseTags() {
		List<String> tags = new ArrayList<>();
		courseRepo.findAll().forEach(course -> {
			tags.addAll(course.getTags());
		});
		Set<String> noDuplicateTags = new HashSet<>();
		noDuplicateTags.addAll(tags);
		tags.clear();
		tags.addAll(noDuplicateTags);
		return tags;
	}

	@Override
	public List<Course> getAllCourseByAuthor(String accessToken) throws StoreFrontException {
		logger.info("Inside the CourseManagement method");
		return courseRepo.findAllCourseByAuthor(userManagement.getCurrentUser(accessToken));
	}

	@Override
	public Course disableOrEnableCoursePreview(String courseId, boolean isPreviewCourse, String moduleIds) {
		Course course = courseRepo.findByCourseId(Long.parseLong(courseId));
		if (course.getCoursePublish() != null)
			course.getCoursePublish().setIsPreview(isPreviewCourse);
		if (course.getCoursePublish() == null) {
			CoursePublish coursePublish = new CoursePublish();
			coursePublish.setCoursePublishId(course.getCourseId());
			coursePublish.setIsPreview(isPreviewCourse);
			course.setCoursePublish(coursePublish);
		}
		courseRepo.save(course);
		List<String> moduleIDlist = Arrays.asList(moduleIds.split(","));
		moduleIDlist.forEach(moduleId -> {
			Module module = moduleRepository.findByModuleId(Long.parseLong(moduleId));
			module.setPreview(isPreviewCourse);
			moduleRepository.save(module);
		});
		return course;
	}

	@Override
	public List<Course> getAllPreviewCourses() throws StoreFrontException {
		List<Course> courses = (List<Course>) courseRepo.findAll();
		Iterator<Course> iterable = courses.iterator();
		while (iterable.hasNext()) {
			Course course = iterable.next();
			if (course.getCoursePublish() == null || !course.getCoursePublish().getIsPreview()) {
				iterable.remove();
			}
		}
		return courses;
	}

	@Override
	public Course enrollCourse(String courseId, String accessToken) throws StoreFrontException {
		logger.info("Inside the CourseManagement method");
		User user = userManagement.getCurrentUser(accessToken);
		Enrollment enrollment = new Enrollment(user.getUserId() + "_" + courseId, "courseInstanceEnrollment", false,
				"0", "0", new Date(), null, null);
		enrollmentRepository.save(enrollment);
		return courseRepo.findOne(Long.parseLong(courseId));
	}

	@Override
	public Course bookMarkCourse(String courseId, boolean isBookMarked, String accessToken) throws StoreFrontException {
		User user = userManagement.getCurrentUser(accessToken);
		UserCourseOperation courseOperation = new UserCourseOperation();
		courseOperation.setBookMarked(isBookMarked);
		courseOperation.setUserCourseId(user.getUserId() + "_" + courseId);
		userCourseRepository.save(courseOperation);
		Course course = courseRepo.findOne(Long.parseLong(courseId));
		course.setBookMarked(isBookMarked);
		return course;
	}

}
