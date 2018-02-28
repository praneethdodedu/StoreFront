package com.storefront.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.storefront.batch.model.Enrollment;
import com.storefront.batch.service.IEnrollmentManagement;

/**
 * @author Praneeth.dodedu
 *
 */
public class EnrollmentDataWriter implements ItemWriter<Enrollment> {

	private static final Logger logger = LoggerFactory.getLogger(EnrollmentDataWriter.class);

	@Autowired
	private IEnrollmentManagement enrollmentManagement;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Enrollment> enrollments) throws Exception {
		logger.info("This is EnrollmentDataWriter::writer method");
		enrollmentManagement.saveEnrollments((List<Enrollment>) enrollments);
	}

}
