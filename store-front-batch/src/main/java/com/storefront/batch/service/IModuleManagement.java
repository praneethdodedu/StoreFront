package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.Module;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IModuleManagement {

	public List<Module> getModulesByCourseId(Long courseId, String token) throws StoreFrontException;
}
