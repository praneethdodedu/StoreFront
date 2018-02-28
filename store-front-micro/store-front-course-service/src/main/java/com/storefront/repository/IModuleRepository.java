package com.storefront.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.model.Module;

public interface IModuleRepository extends CrudRepository<Module, Long> {

	public Module findByModuleId(Long moduleId);

}
