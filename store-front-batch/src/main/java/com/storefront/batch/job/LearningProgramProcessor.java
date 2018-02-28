package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.storefront.batch.model.LearningProgram;

/**
 * @author Praneeth.dodedu
 *
 */
public class LearningProgramProcessor implements ItemProcessor<LearningProgram, LearningProgram> {

	private static final Logger log = LoggerFactory.getLogger(LearningProgramProcessor.class);

	@Override
	public LearningProgram process(final LearningProgram learningProgram) throws Exception {
		log.info("Inside LearningProgramProcessor:: process method");
		return learningProgram;
	}

}