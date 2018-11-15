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

package com.liferay.portal.spring.aop;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author Shuyang Zhou
 */
public class ServiceWrapperProxyUtil {

	public static Closeable injectFieldProxy(
			Object springServiceProxy, String fieldName, Class<?> wrapperClass)
		throws Exception {

		ServiceBeanAopProxy serviceBeanAopProxy =
			ProxyUtil.fetchInvocationHandler(
				springServiceProxy, ServiceBeanAopProxy.class);

		if (serviceBeanAopProxy == null) {
			throw new IllegalArgumentException(
				springServiceProxy + " is not a Spring service proxy");
		}

		final Object targetService = serviceBeanAopProxy.getTarget();

		Class<?> clazz = targetService.getClass();

		Field field = null;

		while (clazz != null) {
			try {
				field = ReflectionUtil.getDeclaredField(clazz, fieldName);

				break;
			}
			catch (NoSuchFieldException nsfe) {
				clazz = clazz.getSuperclass();
			}
		}

		if (field == null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to locate field ", fieldName, " in ",
					String.valueOf(targetService)));
		}

		final Field finalField = field;

		final Object previousValue = finalField.get(targetService);

		Constructor<?>[] constructors = wrapperClass.getDeclaredConstructors();

		Constructor<?> constructor = constructors[0];

		constructor.setAccessible(true);

		finalField.set(targetService, constructor.newInstance(previousValue));

		return new Closeable() {

			@Override
			public void close() throws IOException {
				try {
					finalField.set(targetService, previousValue);
				}
				catch (ReflectiveOperationException roe) {
					throw new IOException(roe);
				}
			}

		};
	}

}