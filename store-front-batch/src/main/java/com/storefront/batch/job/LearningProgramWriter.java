package com.storefront.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.storefront.batch.model.LearningProgram;
import com.storefront.batch.service.ILearningProgramManagement;

/**
 * @author Praneeth.dodedu
 *
 */
public class LearningProgramWriter implements ItemWriter<LearningProgram> {

	private static final Logger logger = LoggerFactory.getLogger(LearningProgramWriter.class);

	@Autowired
	private ILearningProgramManagement iLearningProgramManagement;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends LearningProgram> learningPrograms) throws Exception {
		logger.info("This is LearningProgramWriter::writer method");
		iLearningProgramManagement.saveLearningPrograms((List<LearningProgram>) learningPrograms);
	}

}
