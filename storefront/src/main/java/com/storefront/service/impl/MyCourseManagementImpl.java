package com.storefront.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.MyCourse;
import com.storefront.model.Course;
import com.storefront.model.Enrollment;
import com.storefront.model.LearningProgram;
import com.storefront.model.User;
import com.storefront.service.ICourseManagement;
import com.storefront.service.IEnrollmentManagement;
import com.storefront.service.IMyCourseManagement;
import com.storefront.service.IUserManagement;

@Service
public class MyCourseManagementImpl implements IMyCourseManagement {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCourseManagementImpl.class);

	@Autowired
	private IUserManagement userManagement;

	@Autowired
	private ICourseManagement courseManagement;

	@Autowired
	private IEnrollmentManagement enrollmentManagement;

	@Override
	public List<MyCourse> getAllMyCourses(String accessToken) throws StoreFrontException {
		LOGGER.info("Inside getAllMyCourses method of MyCourseManagementImpl class");
		User user = userManagement.getCurrentUser(accessToken);
		List<MyCourse> myCourses = new ArrayList<>();
		List<Enrollment> myEnrollments = enrollmentManagement.findAllByUserId(user);
		List<LearningProgram> myLearningPrograms = user.getLearningPrograms();
		myLearningPrograms.forEach(learningProgram -> {
			MyCourse myCourse = new MyCourse(learningProgram.getLearningProgramId(),
					learningProgram.getLearningProgramName(), "LearningProgram");
			myCourses.add(myCourse);
		});
		for (Enrollment enrollment : myEnrollments) {
			// if ("1310769_504068".equals(enrollment.getEnrollmentId())
			// || "1310769_505514".equals(enrollment.getEnrollmentId())) {
			Course course = courseManagement.findCourseByCourseId(
					Long.parseLong(
							enrollment.getEnrollmentId().substring(enrollment.getEnrollmentId().indexOf("_") + 1)),
					accessToken);
			MyCourse myCourse = new MyCourse(course.getCourseId(), course.getCourseName(), "Course");
			myCourses.add(myCourse);
			// }
		}
		return myCourses;
	}

}
