package com.storefront.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.model.UserCourseOperation;

public interface IUserCourseRepository extends CrudRepository<UserCourseOperation, String> {

}
