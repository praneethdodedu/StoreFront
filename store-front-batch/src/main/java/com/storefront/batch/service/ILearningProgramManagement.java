package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.LearningProgram;

public interface ILearningProgramManagement {

	public List<LearningProgram> getAllLearningPrograms(String accessToken, Long offsetId) throws StoreFrontException;

	public void saveLearningPrograms(List<LearningProgram> allLearningPrograms);

}
