package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_CATALOG_ID;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_COURSE_ALL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.json.model.CourseResponse;
import com.storefront.batch.json.model.ResponseData;
import com.storefront.batch.model.Catalog;
import com.storefront.batch.model.Course;
import com.storefront.batch.model.CoursePublish;
import com.storefront.batch.model.Module;
import com.storefront.batch.model.User;
import com.storefront.batch.repository.ICourseRepository;
import com.storefront.batch.service.ICourseManagement;
import com.storefront.batch.service.IModuleManagement;
import com.storefront.batch.service.IUserManagement;
import com.storefront.batch.util.CommonsUtil;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

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
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private IModuleManagement iModuleManagement;

	@Autowired
	private IUserManagement iUserManagement;

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
						if (userAuthor != null) {
							Course course = new Course(Long.parseLong(data.getId()), data.getAttributes().getName(),
									data.getAttributes().getDescription(), data.getAttributes().getOverview(),
									data.getAttributes().getState(), data.getAttributes().getCourseType(),
									data.getAttributes().getEnrollmentType(),
									commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
									commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()),
									data.getAttributes().getTags());
							String courseModuleId = data.getRelationships().getCourseModules().getData().get(0).getId();
							if ("courseInstance"
									.equals(data.getRelationships().getInstances().getData().get(0).getType())) {
								course.setCourseInstance(data.getRelationships().getInstances().getData().get(0).getId()
										+ courseModuleId.substring(courseModuleId.length() - 2));
							}
							course.setAuthor(userAuthor);
							CoursePublish coursePublish = new CoursePublish();
							coursePublish.setCoursePublishId(course.getCourseId());
							course.setCoursePublish(coursePublish);
							courses.add(course);
						}
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
	public List<Course> getAllCourses(String token, Long offsetId) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(courselisturl);
			builder.addParameter("page[offset]", offsetId.toString());
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
						if (userAuthor != null) {
							Course course = new Course(Long.parseLong(data.getId()), data.getAttributes().getName(),
									data.getAttributes().getDescription(), data.getAttributes().getOverview(),
									data.getAttributes().getState(), data.getAttributes().getCourseType(),
									data.getAttributes().getEnrollmentType(),
									commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
									commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()),
									data.getAttributes().getTags());
							String courseModuleId = data.getRelationships().getCourseModules().getData().get(0).getId();
							if ("courseInstance"
									.equals(data.getRelationships().getInstances().getData().get(0).getType())) {
								course.setCourseInstance(data.getRelationships().getInstances().getData().get(0).getId()
										+ courseModuleId.substring(courseModuleId.length() - 2));
							}
							course.setAuthor(userAuthor);
							try {
								List<Module> modules = iModuleManagement.getModulesByCourseId(course.getCourseId(),
										token);
								course.setModule(modules);
							} catch (Exception exception) {
								logger.error("Error in fetching module list for particluar course{}", exception);
							}
							CoursePublish coursePublish = new CoursePublish();
							coursePublish.setCoursePublishId(course.getCourseId());
							course.setCoursePublish(coursePublish);
							courses.add(course);
						}
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
	public void saveCourses(List<Course> courses) throws StoreFrontException {
		courses.forEach(course -> {
			course.getModule();
			courseRepo.save(course);
		});
	}

}
