package com.storefront.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.model.LearningProgram;

public interface ILearningProgramRepository extends CrudRepository<LearningProgram, Long> {

}
