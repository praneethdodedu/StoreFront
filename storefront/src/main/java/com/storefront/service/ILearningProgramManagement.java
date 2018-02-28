package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.model.LearningProgram;

public interface ILearningProgramManagement {

	public List<LearningProgram> getAllLearningPrograms(String accessToken) throws StoreFrontException;
	
	public List<LearningProgram> findAll() throws StoreFrontException;

	public LearningProgram findLearningProgramById(Long learningProgramId) throws StoreFrontException;

}
