package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.LearningProgram;

/**
 * @author Praneeth.dodedu
 *
 */
public interface ILearningProgramRepository extends CrudRepository<LearningProgram, Long> {

}
