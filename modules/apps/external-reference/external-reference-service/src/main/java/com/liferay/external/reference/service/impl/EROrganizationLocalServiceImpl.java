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

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.EROrganizationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

/**
 * @author Dylan Rebelak
 */
public class EROrganizationLocalServiceImpl
	extends EROrganizationLocalServiceBaseImpl {

	@Override
	public Organization addOrUpdateOrganization(
			String externalReferenceCode, long userId,
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, long statusId, String comments, boolean site,
			boolean hasLogo, byte[] logoBytes, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Organization organization =
			organizationLocalService.fetchOrganizationByReferenceCode(
				user.getCompanyId(), externalReferenceCode);

		if (organization == null) {
			organization = organizationLocalService.addOrganization(
				userId, parentOrganizationId, name, type, regionId, countryId,
				statusId, comments, site, serviceContext);

			organization.setExternalReferenceCode(externalReferenceCode);

			PortalUtil.updateImageId(
				organization, hasLogo, logoBytes, "logoId",
				_userFileUploadsSettings.getImageMaxSize(),
				_userFileUploadsSettings.getImageMaxHeight(),
				_userFileUploadsSettings.getImageMaxWidth());

			organization = organizationLocalService.updateOrganization(
				organization);
		}
		else {
			organizationLocalService.updateOrganization(
				user.getCompanyId(), organization.getOrganizationId(),
				parentOrganizationId, name, type, regionId, countryId, statusId,
				comments, hasLogo, logoBytes, site, serviceContext);
		}

		return organization;
	}

	@ServiceReference(type = UserFileUploadsSettings.class)
	private UserFileUploadsSettings _userFileUploadsSettings;

}