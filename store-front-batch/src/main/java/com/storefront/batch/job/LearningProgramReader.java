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

import com.storefront.batch.model.LearningProgram;
import com.storefront.batch.service.ILearningProgramManagement;

/**
 * @author Praneeth.dodedu
 *
 */
public class LearningProgramReader implements ItemReader<LearningProgram> {

	private static final Logger logger = LoggerFactory.getLogger(LearningProgramReader.class);

	@Autowired
	private ILearningProgramManagement learningProgramManagement;

	private int nextLearningProgramIndex;
	private List<LearningProgram> learningPrograms;
	@Value("#{jobParameters[offsetId]}")
	public Long offsetId;
	public int limit = 10;

	@Value(PROPERTY_KEY_ACP_ACCESS_TOKEN)
	private String accessToken;

	public LearningProgramReader() {
		nextLearningProgramIndex = 0;
	}

	@Override
	public LearningProgram read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("Inside LearningProgramReader::read() method");
		if (isLearningProgramListNotInstantiated())
			learningPrograms = learningProgramManagement.getAllLearningPrograms(accessToken, offsetId);
		LearningProgram learningProgram = null;
		if (nextLearningProgramIndex < learningPrograms.size()) {
			learningProgram = learningPrograms.get(nextLearningProgramIndex);
			nextLearningProgramIndex++;
		}
		if (nextLearningProgramIndex == limit) {
			offsetId = offsetId + limit;
			nextLearningProgramIndex = 0;
			learningPrograms = null;
		}
		return learningProgram;
	}

	private boolean isLearningProgramListNotInstantiated() {
		return this.learningPrograms == null;
	}

}
