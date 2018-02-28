package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.Enrollment;

public interface IEnrollmentRepository extends CrudRepository<Enrollment, Long> {

}
