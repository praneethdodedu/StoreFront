package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.Course;

/**
 * @author Praneeth.dodedu
 *
 */
public interface ICourseRepository extends CrudRepository<Course, Long> {

	public Course findByCourseId(Long courseId);

}
