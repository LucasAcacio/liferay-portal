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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ListTypeModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account.properties",
	scope = ServiceScope.PROTOTYPE, service = UserAccountResource.class
)
public class UserAccountResourceImpl extends BaseUserAccountResourceImpl {

	@Override
	public boolean deleteUserAccount(Long userAccountId) throws Exception {
		_userService.deleteUser(userAccountId);

		return true;
	}

	@Override
	public UserAccount getMyUserAccount(Long myUserAccountId) throws Exception {
		return _toUserAccount(_userService.getUserById(myUserAccountId));
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_userService.getOrganizationUsers(
					organizationId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					new UserLastNameComparator(true)),
				this::_toUserAccount),
			pagination,
			_userService.getOrganizationUsersCount(
				organizationId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public UserAccount getUserAccount(Long userAccountId) throws Exception {
		return _toUserAccount(_userService.getUserById(userAccountId));
	}

	@Override
	public Page<UserAccount> getUserAccountsPage(Pagination pagination)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(contextCompany.getCompanyId())) {
			throw new PrincipalException.MustBeCompanyAdmin(permissionChecker);
		}

		return Page.of(
			transform(
				_userLocalService.getUsers(
					contextCompany.getCompanyId(), false,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toUserAccount),
			pagination,
			_userLocalService.getUsersCount(
				contextCompany.getCompanyId(), false,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountsPage(
			Long webSiteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_userService.getGroupUsers(
					webSiteId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					new UserLastNameComparator(true)),
				this::_toUserAccount),
			pagination,
			_userService.getGroupUsersCount(
				webSiteId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public UserAccount postUserAccount(MultipartBody multipartBody)
		throws Exception {

		UserAccount userAccount = multipartBody.getValueAsInstance(
			"userAccount", UserAccount.class);

		long prefixId = _getListTypeId(
			userAccount.getHonorificPrefix(),
			ListTypeConstants.CONTACT_PREFIX);
		long suffixId = _getListTypeId(
			userAccount.getHonorificSuffix(),
			ListTypeConstants.CONTACT_SUFFIX);

		Calendar calendar = Calendar.getInstance();

		if (userAccount.getBirthDate() == null) {
			calendar.setTime(new Date(0));
		}
		else {
			calendar.setTime(userAccount.getBirthDate());
		}

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT,
			contextCompany.getCompanyId(), true, null, null,
			Validator.isNull(userAccount.getAlternateName()),
			userAccount.getAlternateName(), userAccount.getEmail(),
			0, StringPool.BLANK, LocaleUtil.getDefault(),
			userAccount.getGivenName(), StringPool.BLANK,
			userAccount.getFamilyName(), prefixId, suffixId, true,
			calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DATE),
			calendar.get(Calendar.YEAR), userAccount.getJobTitle(),
			null, null, null, null, false, new ServiceContext());

		byte[] bytes = _getImageBytes(
			multipartBody.getBinaryFile("file"));

		_userLocalService.updatePortrait(user.getUserId(), bytes);

		return _toUserAccount(user);
	}

	@Override
	public UserAccount putUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		long prefixId = _getListTypeId(
			userAccount.getHonorificPrefix(), ListTypeConstants.CONTACT_PREFIX);
		long suffixId = _getListTypeId(
			userAccount.getHonorificSuffix(), ListTypeConstants.CONTACT_SUFFIX);

		Calendar calendar = Calendar.getInstance();

		if (userAccount.getBirthDate() == null) {
			calendar.setTime(new Date(0));
		}
		else {
			calendar.setTime(userAccount.getBirthDate());
		}

		ContactInformation contactInformation =
			userAccount.getContactInformation();

		return _toUserAccount(
			_userLocalService.updateUser(
				user.getUserId(), user.getPassword(), null, null, false,
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				userAccount.getAlternateName(), userAccount.getEmail(),
				user.getFacebookId(), user.getOpenId(), false, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), userAccount.getGivenName(),
				user.getMiddleName(), userAccount.getFamilyName(), prefixId,
				suffixId, true, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR),
				_getOrElse(contactInformation, ContactInformation::getSms),
				_getOrElse(contactInformation, ContactInformation::getFacebook),
				_getOrElse(contactInformation, ContactInformation::getJabber),
				_getOrElse(contactInformation, ContactInformation::getSkype),
				_getOrElse(contactInformation, ContactInformation::getTwitter),
				userAccount.getJobTitle(), user.getGroupIds(),
				user.getOrganizationIds(), user.getRoleIds(), null,
				user.getUserGroupIds(), new ServiceContext()));
	}

	private long _getListTypeId(String name, String type) {
		if (name == null) {
			return 0;
		}

		return Optional.ofNullable(
			_listTypeService.getListType(name, type)
		).map(
			ListTypeModel::getListTypeId
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to find honorific title: " + name)
		);
	}

	private byte[] _getImageBytes(BinaryFile binaryFile) throws IOException {
		InputStream inputStream = binaryFile.getInputStream();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		StreamUtil.transfer(inputStream, byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}

	private String _getListTypeMessage(long listTypeId) throws PortalException {
		if (listTypeId == 0) {
			return null;
		}

		ListType listType = _listTypeService.getListType(listTypeId);

		return LanguageUtil.get(
			contextAcceptLanguage.getPreferredLocale(), listType.getName());
	}

	private String _getOrElse(
		ContactInformation contactInformation,
		Function<ContactInformation, String> function) {

		return Optional.ofNullable(
			contactInformation
		).map(
			function
		).orElse(
			null
		);
	}

	private ThemeDisplay _getThemeDisplay(Group group) {
		return new ThemeDisplay() {
			{
				setPortalURL(StringPool.BLANK);
				setSiteGroupId(group.getGroupId());
			}
		};
	}

	private ContactInformation _toContactInformation(Contact contact) {
		if (contact == null) {
			return null;
		}

		return new ContactInformation() {
			{
				facebook = contact.getFacebookSn();
				jabber = contact.getJabberSn();
				skype = contact.getSkypeSn();
				sms = contact.getSmsSn();
				twitter = contact.getTwitterSn();
			}
		};
	}

	private UserAccount _toUserAccount(User user) throws PortalException {
		Contact contact = user.getContact();

		return new UserAccount() {
			{
				additionalName = user.getMiddleName();
				alternateName = user.getScreenName();
				birthDate = user.getBirthday();
				contactInformation = _toContactInformation(contact);
				email = user.getEmailAddress();
				familyName = user.getLastName();
				givenName = user.getFirstName();
				honorificPrefix = _getListTypeMessage(contact.getPrefixId());
				honorificSuffix = _getListTypeMessage(contact.getSuffixId());
				id = user.getUserId();
				jobTitle = user.getJobTitle();
				name = user.getFullName();

				setDashboardURL(
					() -> {
						Group group = user.getGroup();

						return group.getDisplayURL(
							_getThemeDisplay(group), true);
					});
				setImage(
					() -> {
						if (user.getPortraitId() == 0) {
							return null;
						}

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPathImage(_portal.getPathImage());
							}
						};

						return user.getPortraitURL(themeDisplay);
					});
				setProfileURL(
					() -> {
						Group group = user.getGroup();

						return group.getDisplayURL(_getThemeDisplay(group));
					});
			}
		};
	}

	@Reference
	private ListTypeService _listTypeService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}