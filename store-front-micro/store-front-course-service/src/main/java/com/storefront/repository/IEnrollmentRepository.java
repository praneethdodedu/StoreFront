package com.storefront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.storefront.model.Course;
import com.storefront.model.Enrollment;
import com.storefront.model.User;

public interface IEnrollmentRepository extends CrudRepository<Enrollment, Long> {

	@Query("FROM Enrollment e WHERE e.user=:user")
	public List<Enrollment> findAllByUserId(@Param("user") User user);
	
	@Query("FROM Enrollment e WHERE e.user=:user and e.course=:course")
	public Enrollment findByUserAndCourse(@Param("user") User user, @Param("course") Course course);

}
