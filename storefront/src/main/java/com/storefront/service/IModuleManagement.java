package com.storefront.service;

import java.util.List;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Module;

public interface IModuleManagement {

	public List<Module> findAll();

	public List<Module> findModulesByCourseId(Long courseId);

	public Module findModuleByModuleId(Long moduleId);

	public List<Module> getModulesByCourseId(Long courseId, String accessToken) throws StoreFrontException;

	public String getFluidicPlayerUrl(String courseId, String moduleId, OAuth2Authentication authentication) throws StoreFrontException;
}
