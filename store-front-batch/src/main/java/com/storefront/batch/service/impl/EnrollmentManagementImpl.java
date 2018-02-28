package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.CHARACTER_KEY_FORWARD_SLASH;
import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PATH_VARIABLE_COURSE_ENROLLMENT;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_USERS_ALL;

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
import com.storefront.batch.json.model.EnrollmentResponse;
import com.storefront.batch.json.model.ResponseData;
import com.storefront.batch.model.Course;
import com.storefront.batch.model.Enrollment;
import com.storefront.batch.model.User;
import com.storefront.batch.repository.ICourseRepository;
import com.storefront.batch.repository.IEnrollmentRepository;
import com.storefront.batch.repository.IUserReository;
import com.storefront.batch.service.IEnrollmentManagement;
import com.storefront.batch.util.CommonsUtil;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class EnrollmentManagementImpl implements IEnrollmentManagement {

	private static final Logger logger = LoggerFactory.getLogger(EnrollmentManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_USERS_ALL)
	private String userListUrl;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private ICourseRepository iCourseRepository;

	@Autowired
	private IEnrollmentRepository enrollmentRepository;

	@Autowired
	private IUserReository iUserReository;

	@Override
	public void saveEnrollments(List<Enrollment> enrollments) throws StoreFrontException {
		enrollmentRepository.save(enrollments);
	}

	@Override
	public List<Enrollment> getAllEnrollments(String accessToken) {
		List<User> users = (List<User>) iUserReository.findAll();
		List<Enrollment> enrollments = new ArrayList<>();
		users.forEach(user -> {
			try {
				enrollments.addAll(getEnrollments(user, accessToken));
			} catch (StoreFrontException exception) {
				logger.error("Error getting enrollment for particular user from ACP {}", exception);
			}
		});
		return enrollments;
	}

	@Override
	public List<Enrollment> getEnrollments(User user, String token) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(userListUrl + CHARACTER_KEY_FORWARD_SLASH + user.getUserId()
					+ CHARACTER_KEY_FORWARD_SLASH + PATH_VARIABLE_COURSE_ENROLLMENT);
			URL url = builder.build().toURL();
			String enrollmentResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(enrollmentResp)) {
				EnrollmentResponse enrollmentResponse = null;
				try {
					enrollmentResponse = (EnrollmentResponse) jsonMapper.fromJson(enrollmentResp,
							EnrollmentResponse.class);
					List<Enrollment> enrollments = new ArrayList<>();
					for (ResponseData data : enrollmentResponse.getData()) {
						Course course = iCourseRepository
								.findByCourseId(Long.parseLong(data.getRelationships().getCourse().getData().getId()));
						if (course != null) {
							Enrollment enrollment = new Enrollment(data.getId(), data.getType(),
									data.getAttributes().isPassed(), data.getAttributes().getPointsEarned().toString(),
									data.getAttributes().getProgressPercent().toString(),
									data.getAttributes().getDateEnrolled() != null ? commonsUtil
											.parseJsonStringIntoDate(data.getAttributes().getDateEnrolled()) : null,
									data.getAttributes().getDateStarted() != null
											? commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateStarted())
											: null,
									data.getAttributes().getDateCompleted() != null ? commonsUtil
											.parseJsonStringIntoDate(data.getAttributes().getDateCompleted()) : null);
							enrollment.setCourse(course);
							enrollment.setUser(user);
							enrollments.add(enrollment);
						}
					}
					return enrollments;
				} catch (Exception exception) {
					logger.error("Error parsing the Enrollment list from ACP {}", exception);
					throw new StoreFrontException(
							"Error parsing the Enrollment list from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP while getting Enrollment list : {}", exception);
			throw new StoreFrontException("Error while connecting to ACP while getting Enrollment list: " + exception);
		}
	}

}
