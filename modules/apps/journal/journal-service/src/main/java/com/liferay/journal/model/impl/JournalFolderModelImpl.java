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

package com.liferay.journal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderModel;
import com.liferay.journal.model.JournalFolderSoap;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the JournalFolder service. Represents a row in the &quot;JournalFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link JournalFolderModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link JournalFolderImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderImpl
 * @see JournalFolder
 * @see JournalFolderModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class JournalFolderModelImpl extends BaseModelImpl<JournalFolder>
	implements JournalFolderModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a journal folder model instance should use the {@link JournalFolder} interface instead.
	 */
	public static final String TABLE_NAME = "JournalFolder";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "folderId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "parentFolderId", Types.BIGINT },
			{ "treePath", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description_", Types.VARCHAR },
			{ "restrictionType", Types.INTEGER },
			{ "lastPublishDate", Types.TIMESTAMP },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("restrictionType", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table JournalFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentFolderId LONG,treePath STRING null,name VARCHAR(100) null,description_ STRING null,restrictionType INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table JournalFolder";
	public static final String ORDER_BY_JPQL = " ORDER BY journalFolder.parentFolderId ASC, journalFolder.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY JournalFolder.parentFolderId ASC, JournalFolder.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.journal.model.JournalFolder"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.journal.model.JournalFolder"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.journal.model.JournalFolder"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long FOLDERID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long NAME_COLUMN_BITMASK = 8L;
	public static final long PARENTFOLDERID_COLUMN_BITMASK = 16L;
	public static final long STATUS_COLUMN_BITMASK = 32L;
	public static final long UUID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static JournalFolder toModel(JournalFolderSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		JournalFolder model = new JournalFolderImpl();

		model.setUuid(soapModel.getUuid());
		model.setFolderId(soapModel.getFolderId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setParentFolderId(soapModel.getParentFolderId());
		model.setTreePath(soapModel.getTreePath());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setRestrictionType(soapModel.getRestrictionType());
		model.setLastPublishDate(soapModel.getLastPublishDate());
		model.setStatus(soapModel.getStatus());
		model.setStatusByUserId(soapModel.getStatusByUserId());
		model.setStatusByUserName(soapModel.getStatusByUserName());
		model.setStatusDate(soapModel.getStatusDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<JournalFolder> toModels(JournalFolderSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<JournalFolder> models = new ArrayList<JournalFolder>(soapModels.length);

		for (JournalFolderSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.journal.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.journal.model.JournalFolder"));

	public JournalFolderModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _folderId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFolderId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _folderId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return JournalFolder.class;
	}

	@Override
	public String getModelClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<JournalFolder, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<JournalFolder, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFolder, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((JournalFolder)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<JournalFolder, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<JournalFolder, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((JournalFolder)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<JournalFolder, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<JournalFolder, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<JournalFolder, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<JournalFolder, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<JournalFolder, Object>> attributeGetterFunctions = new LinkedHashMap<String, Function<JournalFolder, Object>>();
		Map<String, BiConsumer<JournalFolder, ?>> attributeSetterBiConsumers = new LinkedHashMap<String, BiConsumer<JournalFolder, ?>>();


		attributeGetterFunctions.put("uuid", JournalFolder::getUuid);
		attributeSetterBiConsumers.put("uuid", (BiConsumer<JournalFolder, String>)JournalFolder::setUuid);
		attributeGetterFunctions.put("folderId", JournalFolder::getFolderId);
		attributeSetterBiConsumers.put("folderId", (BiConsumer<JournalFolder, Long>)JournalFolder::setFolderId);
		attributeGetterFunctions.put("groupId", JournalFolder::getGroupId);
		attributeSetterBiConsumers.put("groupId", (BiConsumer<JournalFolder, Long>)JournalFolder::setGroupId);
		attributeGetterFunctions.put("companyId", JournalFolder::getCompanyId);
		attributeSetterBiConsumers.put("companyId", (BiConsumer<JournalFolder, Long>)JournalFolder::setCompanyId);
		attributeGetterFunctions.put("userId", JournalFolder::getUserId);
		attributeSetterBiConsumers.put("userId", (BiConsumer<JournalFolder, Long>)JournalFolder::setUserId);
		attributeGetterFunctions.put("userName", JournalFolder::getUserName);
		attributeSetterBiConsumers.put("userName", (BiConsumer<JournalFolder, String>)JournalFolder::setUserName);
		attributeGetterFunctions.put("createDate", JournalFolder::getCreateDate);
		attributeSetterBiConsumers.put("createDate", (BiConsumer<JournalFolder, Date>)JournalFolder::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", JournalFolder::getModifiedDate);
		attributeSetterBiConsumers.put("modifiedDate", (BiConsumer<JournalFolder, Date>)JournalFolder::setModifiedDate);
		attributeGetterFunctions.put("parentFolderId", JournalFolder::getParentFolderId);
		attributeSetterBiConsumers.put("parentFolderId", (BiConsumer<JournalFolder, Long>)JournalFolder::setParentFolderId);
		attributeGetterFunctions.put("treePath", JournalFolder::getTreePath);
		attributeSetterBiConsumers.put("treePath", (BiConsumer<JournalFolder, String>)JournalFolder::setTreePath);
		attributeGetterFunctions.put("name", JournalFolder::getName);
		attributeSetterBiConsumers.put("name", (BiConsumer<JournalFolder, String>)JournalFolder::setName);
		attributeGetterFunctions.put("description", JournalFolder::getDescription);
		attributeSetterBiConsumers.put("description", (BiConsumer<JournalFolder, String>)JournalFolder::setDescription);
		attributeGetterFunctions.put("restrictionType", JournalFolder::getRestrictionType);
		attributeSetterBiConsumers.put("restrictionType", (BiConsumer<JournalFolder, Integer>)JournalFolder::setRestrictionType);
		attributeGetterFunctions.put("lastPublishDate", JournalFolder::getLastPublishDate);
		attributeSetterBiConsumers.put("lastPublishDate", (BiConsumer<JournalFolder, Date>)JournalFolder::setLastPublishDate);
		attributeGetterFunctions.put("status", JournalFolder::getStatus);
		attributeSetterBiConsumers.put("status", (BiConsumer<JournalFolder, Integer>)JournalFolder::setStatus);
		attributeGetterFunctions.put("statusByUserId", JournalFolder::getStatusByUserId);
		attributeSetterBiConsumers.put("statusByUserId", (BiConsumer<JournalFolder, Long>)JournalFolder::setStatusByUserId);
		attributeGetterFunctions.put("statusByUserName", JournalFolder::getStatusByUserName);
		attributeSetterBiConsumers.put("statusByUserName", (BiConsumer<JournalFolder, String>)JournalFolder::setStatusByUserName);
		attributeGetterFunctions.put("statusDate", JournalFolder::getStatusDate);
		attributeSetterBiConsumers.put("statusDate", (BiConsumer<JournalFolder, Date>)JournalFolder::setStatusDate);


		_attributeGetterFunctions = Collections.unmodifiableMap(attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap((Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getFolderId() {
		return _folderId;
	}

	@Override
	public void setFolderId(long folderId) {
		_columnBitmask |= FOLDERID_COLUMN_BITMASK;

		if (!_setOriginalFolderId) {
			_setOriginalFolderId = true;

			_originalFolderId = _folderId;
		}

		_folderId = folderId;
	}

	public long getOriginalFolderId() {
		return _originalFolderId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getParentFolderId() {
		return _parentFolderId;
	}

	@Override
	public void setParentFolderId(long parentFolderId) {
		_columnBitmask = -1L;

		if (!_setOriginalParentFolderId) {
			_setOriginalParentFolderId = true;

			_originalParentFolderId = _parentFolderId;
		}

		_parentFolderId = parentFolderId;
	}

	public long getOriginalParentFolderId() {
		return _originalParentFolderId;
	}

	@JSON
	@Override
	public String getTreePath() {
		if (_treePath == null) {
			return "";
		}
		else {
			return _treePath;
		}
	}

	@Override
	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask = -1L;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	@Override
	public int getRestrictionType() {
		return _restrictionType;
	}

	@Override
	public void setRestrictionType(int restrictionType) {
		_restrictionType = restrictionType;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@JSON
	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	@JSON
	@Override
	public long getStatusByUserId() {
		return _statusByUserId;
	}

	@Override
	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	@Override
	public String getStatusByUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getStatusByUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
	}

	@JSON
	@Override
	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return "";
		}
		else {
			return _statusByUserName;
		}
	}

	@Override
	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	@JSON
	@Override
	public Date getStatusDate() {
		return _statusDate;
	}

	@Override
	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	@Override
	public long getContainerModelId() {
		return getFolderId();
	}

	@Override
	public void setContainerModelId(long containerModelId) {
		_folderId = containerModelId;
	}

	@Override
	public long getParentContainerModelId() {
		return getParentFolderId();
	}

	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_parentFolderId = parentContainerModelId;
	}

	@Override
	public String getContainerModelName() {
		return String.valueOf(getName());
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				JournalFolder.class.getName()));
	}

	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws PortalException {
		if (!isInTrash()) {
			return null;
		}

		com.liferay.trash.kernel.model.TrashEntry trashEntry = com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return trashEntry;
		}

		com.liferay.portal.kernel.trash.TrashHandler trashHandler = getTrashHandler();

		if (Validator.isNotNull(trashHandler.getContainerModelClassName(
						getPrimaryKey()))) {
			ContainerModel containerModel = null;

			try {
				containerModel = trashHandler.getParentContainerModel(this);
			}
			catch (NoSuchModelException nsme) {
				return null;
			}

			while (containerModel != null) {
				if (containerModel instanceof TrashedModel) {
					TrashedModel trashedModel = (TrashedModel)containerModel;

					return trashedModel.getTrashEntry();
				}

				trashHandler = com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil.getTrashHandler(trashHandler.getContainerModelClassName(
							containerModel.getContainerModelId()));

				if (trashHandler == null) {
					return null;
				}

				containerModel = trashHandler.getContainerModel(containerModel.getParentContainerModelId());
			}
		}

		return null;
	}

	@Override
	public long getTrashEntryClassPK() {
		return getPrimaryKey();
	}

	/**
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil.getTrashHandler(getModelClassName());
	}

	@Override
	public boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer() {
		com.liferay.portal.kernel.trash.TrashHandler trashHandler = getTrashHandler();

		if ((trashHandler == null) ||
				Validator.isNull(trashHandler.getContainerModelClassName(
						getPrimaryKey()))) {
			return false;
		}

		try {
			ContainerModel containerModel = trashHandler.getParentContainerModel(this);

			if (containerModel == null) {
				return false;
			}

			if (containerModel instanceof TrashedModel) {
				return ((TrashedModel)containerModel).isInTrash();
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isInTrashExplicitly() {
		if (!isInTrash()) {
			return false;
		}

		com.liferay.trash.kernel.model.TrashEntry trashEntry = com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isInTrashImplicitly() {
		if (!isInTrash()) {
			return false;
		}

		com.liferay.trash.kernel.model.TrashEntry trashEntry = com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDenied() {
		if (getStatus() == WorkflowConstants.STATUS_DENIED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDraft() {
		if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInactive() {
		if (getStatus() == WorkflowConstants.STATUS_INACTIVE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isIncomplete() {
		if (getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isScheduled() {
		if (getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			JournalFolder.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public JournalFolder toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (JournalFolder)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		JournalFolderImpl journalFolderImpl = new JournalFolderImpl();

		journalFolderImpl.setUuid(getUuid());
		journalFolderImpl.setFolderId(getFolderId());
		journalFolderImpl.setGroupId(getGroupId());
		journalFolderImpl.setCompanyId(getCompanyId());
		journalFolderImpl.setUserId(getUserId());
		journalFolderImpl.setUserName(getUserName());
		journalFolderImpl.setCreateDate(getCreateDate());
		journalFolderImpl.setModifiedDate(getModifiedDate());
		journalFolderImpl.setParentFolderId(getParentFolderId());
		journalFolderImpl.setTreePath(getTreePath());
		journalFolderImpl.setName(getName());
		journalFolderImpl.setDescription(getDescription());
		journalFolderImpl.setRestrictionType(getRestrictionType());
		journalFolderImpl.setLastPublishDate(getLastPublishDate());
		journalFolderImpl.setStatus(getStatus());
		journalFolderImpl.setStatusByUserId(getStatusByUserId());
		journalFolderImpl.setStatusByUserName(getStatusByUserName());
		journalFolderImpl.setStatusDate(getStatusDate());

		journalFolderImpl.resetOriginalValues();

		return journalFolderImpl;
	}

	@Override
	public int compareTo(JournalFolder journalFolder) {
		int value = 0;

		if (getParentFolderId() < journalFolder.getParentFolderId()) {
			value = -1;
		}
		else if (getParentFolderId() > journalFolder.getParentFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().compareToIgnoreCase(journalFolder.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalFolder)) {
			return false;
		}

		JournalFolder journalFolder = (JournalFolder)obj;

		long primaryKey = journalFolder.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		JournalFolderModelImpl journalFolderModelImpl = this;

		journalFolderModelImpl._originalUuid = journalFolderModelImpl._uuid;

		journalFolderModelImpl._originalFolderId = journalFolderModelImpl._folderId;

		journalFolderModelImpl._setOriginalFolderId = false;

		journalFolderModelImpl._originalGroupId = journalFolderModelImpl._groupId;

		journalFolderModelImpl._setOriginalGroupId = false;

		journalFolderModelImpl._originalCompanyId = journalFolderModelImpl._companyId;

		journalFolderModelImpl._setOriginalCompanyId = false;

		journalFolderModelImpl._setModifiedDate = false;

		journalFolderModelImpl._originalParentFolderId = journalFolderModelImpl._parentFolderId;

		journalFolderModelImpl._setOriginalParentFolderId = false;

		journalFolderModelImpl._originalName = journalFolderModelImpl._name;

		journalFolderModelImpl._originalStatus = journalFolderModelImpl._status;

		journalFolderModelImpl._setOriginalStatus = false;

		journalFolderModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<JournalFolder> toCacheModel() {
		JournalFolderCacheModel journalFolderCacheModel = new JournalFolderCacheModel();

		journalFolderCacheModel.uuid = getUuid();

		String uuid = journalFolderCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			journalFolderCacheModel.uuid = null;
		}

		journalFolderCacheModel.folderId = getFolderId();

		journalFolderCacheModel.groupId = getGroupId();

		journalFolderCacheModel.companyId = getCompanyId();

		journalFolderCacheModel.userId = getUserId();

		journalFolderCacheModel.userName = getUserName();

		String userName = journalFolderCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			journalFolderCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			journalFolderCacheModel.createDate = createDate.getTime();
		}
		else {
			journalFolderCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			journalFolderCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			journalFolderCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		journalFolderCacheModel.parentFolderId = getParentFolderId();

		journalFolderCacheModel.treePath = getTreePath();

		String treePath = journalFolderCacheModel.treePath;

		if ((treePath != null) && (treePath.length() == 0)) {
			journalFolderCacheModel.treePath = null;
		}

		journalFolderCacheModel.name = getName();

		String name = journalFolderCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			journalFolderCacheModel.name = null;
		}

		journalFolderCacheModel.description = getDescription();

		String description = journalFolderCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			journalFolderCacheModel.description = null;
		}

		journalFolderCacheModel.restrictionType = getRestrictionType();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			journalFolderCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			journalFolderCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		journalFolderCacheModel.status = getStatus();

		journalFolderCacheModel.statusByUserId = getStatusByUserId();

		journalFolderCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = journalFolderCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			journalFolderCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			journalFolderCacheModel.statusDate = statusDate.getTime();
		}
		else {
			journalFolderCacheModel.statusDate = Long.MIN_VALUE;
		}

		return journalFolderCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<JournalFolder, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<JournalFolder, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFolder, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((JournalFolder)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<JournalFolder, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<JournalFolder, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFolder, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((JournalFolder)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = JournalFolder.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			JournalFolder.class, ModelWrapper.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _folderId;
	private long _originalFolderId;
	private boolean _setOriginalFolderId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _parentFolderId;
	private long _originalParentFolderId;
	private boolean _setOriginalParentFolderId;
	private String _treePath;
	private String _name;
	private String _originalName;
	private String _description;
	private int _restrictionType;
	private Date _lastPublishDate;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private long _columnBitmask;
	private JournalFolder _escapedModel;
}