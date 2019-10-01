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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.lang.reflect.Field;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class StringUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws ReflectiveOperationException {

		if (!absolutePath.contains("poshi")) {
			_checkReplaceCalls(fileName, content);
		}

		return content;
	}

	private void _checkReplaceCalls(String fileName, String content)
		throws ReflectiveOperationException {

		Matcher matcher = _stringUtilReplacePattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (parametersList.size() != 3) {
				continue;
			}

			String secondParameter = parametersList.get(1);

			Matcher singleLengthMatcher = _singleLengthStringPattern.matcher(
				secondParameter);

			if (!singleLengthMatcher.find()) {
				continue;
			}

			String fieldName = singleLengthMatcher.group(2);

			if (fieldName != null) {
				Field field = StringPool.class.getDeclaredField(fieldName);

				String value = (String)field.get(null);

				if (value.length() != 1) {
					continue;
				}
			}

			String method = matcher.group(1);

			StringBundler sb = new StringBundler(5);

			sb.append("Use StringUtil.");
			sb.append(method);
			sb.append("(String, char, char) or StringUtil.");
			sb.append(method);
			sb.append("(String, char, String) instead");

			addMessage(
				fileName, sb.toString(), "string_methods.markdown",
				getLineNumber(content, matcher.start()));
		}
	}

	private static final Pattern _singleLengthStringPattern = Pattern.compile(
		"^(\".\"|StringPool\\.([A-Z_]+))$");
	private static final Pattern _stringUtilReplacePattern = Pattern.compile(
		"StringUtil\\.(replace(First|Last)?)\\(");

}