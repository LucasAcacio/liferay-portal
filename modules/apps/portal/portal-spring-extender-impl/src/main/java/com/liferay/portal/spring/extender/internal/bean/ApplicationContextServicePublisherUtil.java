/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.extender.internal.bean;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author Miguel Pastor
 * @author Preston Crary
 */
public class ApplicationContextServicePublisherUtil {

	public static List<ServiceRegistration<?>> registerContext(
		ApplicationContext applicationContext, BundleContext bundleContext,
		boolean parentContext) {

		String[] beanNames = applicationContext.getBeanDefinitionNames();

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>(
			beanNames.length + 1);

		for (String beanName : beanNames) {
			try {
				ServiceRegistration<?> serviceRegistration = _registerService(
					bundleContext, beanName,
					applicationContext.getBean(beanName));

				if (serviceRegistration != null) {
					serviceRegistrations.add(serviceRegistration);
				}
			}
			catch (BeanIsAbstractException biae) {
			}
			catch (Exception e) {
				_log.error("Unable to register service " + beanName, e);
			}
		}

		Bundle bundle = bundleContext.getBundle();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		if (parentContext) {
			properties.put(
				"org.springframework.parent.context.service.name",
				bundle.getSymbolicName());
		}
		else {
			properties.put(
				"org.springframework.context.service.name",
				bundle.getSymbolicName());
		}

		ServiceRegistration<ApplicationContext> serviceRegistration =
			bundleContext.registerService(
				ApplicationContext.class, applicationContext, properties);

		serviceRegistrations.add(serviceRegistration);

		return serviceRegistrations;
	}

	public static void unregisterContext(
		List<ServiceRegistration<?>> serviceRegistrations) {

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceReference :
					serviceRegistrations) {

				serviceReference.unregister();
			}

			serviceRegistrations.clear();
		}
	}

	private static ServiceRegistration<?> _registerService(
		BundleContext bundleContext, String beanName, Object bean) {

		Class<?> clazz = bean.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			ServiceBeanAopProxy serviceBeanAopProxy =
				ProxyUtil.fetchInvocationHandler(
					bean, ServiceBeanAopProxy.class);

			if (serviceBeanAopProxy != null) {
				Object target = serviceBeanAopProxy.getTarget();

				clazz = target.getClass();
			}
		}

		OSGiBeanProperties osgiBeanProperties = AnnotationUtils.findAnnotation(
			clazz, OSGiBeanProperties.class);

		Set<String> names = OSGiBeanProperties.Service.interfaceNames(
			bean, osgiBeanProperties,
			PropsValues.MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES);

		if (names.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping registration because of an empty list of " +
						"interfaces");
			}

			return null;
		}

		Bundle bundle = bundleContext.getBundle();

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		properties.put("bean.id", beanName);
		properties.put("origin.bundle.symbolic.name", bundle.getSymbolicName());

		if (osgiBeanProperties != null) {
			properties.putAll(
				OSGiBeanProperties.Convert.toMap(osgiBeanProperties));
		}

		return bundleContext.registerService(
			names.toArray(new String[names.size()]), bean, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationContextServicePublisherUtil.class);

}