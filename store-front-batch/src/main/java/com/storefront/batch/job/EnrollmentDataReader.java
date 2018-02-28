package com.storefront.batch.job;

import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_ACCESS_TOKEN;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.storefront.batch.model.Enrollment;
import com.storefront.batch.service.IEnrollmentManagement;

/**
 * @author Praneeth.dodedu
 *
 */
public class EnrollmentDataReader implements ItemReader<Enrollment> {

	private static final Logger logger = LoggerFactory.getLogger(EnrollmentDataReader.class);

	@Autowired
	private IEnrollmentManagement enrollmentManagement;

	private int nextEnrollmentIndex;
	public List<Enrollment> enrollments;

	@Value(PROPERTY_KEY_ACP_ACCESS_TOKEN)
	private String accessToken;

	public EnrollmentDataReader() {
		nextEnrollmentIndex = 0;
	}

	@Override
	public Enrollment read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("Inside EnrollmentDataReader::read() method");
		if (isUserListNotInstantiated()) {
			enrollments = enrollmentManagement.getAllEnrollments(accessToken);
		}
		Enrollment nextEnrollment = null;
		if (nextEnrollmentIndex < enrollments.size()) {
			nextEnrollment = enrollments.get(nextEnrollmentIndex);
			nextEnrollmentIndex++;
		}
		return nextEnrollment;
	}

	public boolean isUserListNotInstantiated() {
		return this.enrollments == null;
	}

}
