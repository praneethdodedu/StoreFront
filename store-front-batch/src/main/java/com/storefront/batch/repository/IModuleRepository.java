package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.Module;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IModuleRepository extends CrudRepository<Module, Long> {

	public Module findByModuleId(Long moduleId);

}
