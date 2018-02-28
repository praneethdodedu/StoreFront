package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.storefront.batch.model.Enrollment;

/**
 * @author Praneeth.dodedu
 *
 */
public class EnrollmentDataProcessor implements ItemProcessor<Enrollment, Enrollment> {

	private static final Logger log = LoggerFactory.getLogger(EnrollmentDataProcessor.class);

	@Override
	public Enrollment process(final Enrollment enrollment) throws Exception {
		log.info("Inside EnrollmentDataProcessor:: process method");
		return enrollment;
	}

}
