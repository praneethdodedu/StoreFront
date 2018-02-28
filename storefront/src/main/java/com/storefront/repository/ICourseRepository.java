package com.storefront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.storefront.model.Course;
import com.storefront.model.User;

public interface ICourseRepository extends CrudRepository<Course, Long> {

	public Course findByCourseId(Long courseId);

	@Query("FROM Course c WHERE c.courseName LIKE CONCAT('%',:courseName,'%')")
	public List<Course> searchByCourseName(@Param("courseName") String courseName);

	@Query("select distinct coursetype from Course")
	public List<String> findAllCourseType();

	@Query("select distinct courseState from Course")
	public List<String> findAllCourseState();

	@Query("FROM Course c WHERE author=:author")
	public List<Course> findAllCourseByAuthor(@Param("author") User author);

}
