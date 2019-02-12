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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureModel;
import com.liferay.dynamic.data.mapping.model.DDMStructureSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the DDMStructure service. Represents a row in the &quot;DDMStructure&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link DDMStructureModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMStructureImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureImpl
 * @see DDMStructure
 * @see DDMStructureModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class DDMStructureModelImpl extends BaseModelImpl<DDMStructure>
	implements DDMStructureModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ddm structure model instance should use the {@link DDMStructure} interface instead.
	 */
	public static final String TABLE_NAME = "DDMStructure";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "structureId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "versionUserId", Types.BIGINT },
			{ "versionUserName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "parentStructureId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "structureKey", Types.VARCHAR },
			{ "version", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description_", Types.CLOB },
			{ "definition_", Types.CLOB },
			{ "storageType", Types.VARCHAR },
			{ "type_", Types.INTEGER },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("structureId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("parentStructureId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("structureKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description_", Types.CLOB);
		TABLE_COLUMNS_MAP.put("definition_", Types.CLOB);
		TABLE_COLUMNS_MAP.put("storageType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table DDMStructure (uuid_ VARCHAR(75) null,structureId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentStructureId LONG,classNameId LONG,structureKey VARCHAR(75) null,version VARCHAR(75) null,name STRING null,description_ TEXT null,definition_ TEXT null,storageType VARCHAR(75) null,type_ INTEGER,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table DDMStructure";
	public static final String ORDER_BY_JPQL = " ORDER BY ddmStructure.structureId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DDMStructure.structureId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.mapping.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.dynamic.data.mapping.model.DDMStructure"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.mapping.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.dynamic.data.mapping.model.DDMStructure"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.mapping.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.dynamic.data.mapping.model.DDMStructure"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long DESCRIPTION_COLUMN_BITMASK = 4L;
	public static final long GROUPID_COLUMN_BITMASK = 8L;
	public static final long NAME_COLUMN_BITMASK = 16L;
	public static final long PARENTSTRUCTUREID_COLUMN_BITMASK = 32L;
	public static final long STRUCTUREKEY_COLUMN_BITMASK = 64L;
	public static final long UUID_COLUMN_BITMASK = 128L;
	public static final long STRUCTUREID_COLUMN_BITMASK = 256L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DDMStructure toModel(DDMStructureSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DDMStructure model = new DDMStructureImpl();

		model.setUuid(soapModel.getUuid());
		model.setStructureId(soapModel.getStructureId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setParentStructureId(soapModel.getParentStructureId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setStructureKey(soapModel.getStructureKey());
		model.setVersion(soapModel.getVersion());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setDefinition(soapModel.getDefinition());
		model.setStorageType(soapModel.getStorageType());
		model.setType(soapModel.getType());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DDMStructure> toModels(DDMStructureSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DDMStructure> models = new ArrayList<DDMStructure>(soapModels.length);

		for (DDMStructureSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.dynamic.data.mapping.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.dynamic.data.mapping.model.DDMStructure"));

	public DDMStructureModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _structureId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setStructureId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _structureId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructure.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<DDMStructure, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<DDMStructure, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDMStructure, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((DDMStructure)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<DDMStructure, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<DDMStructure, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((DDMStructure)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<DDMStructure, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<DDMStructure, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<DDMStructure, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<DDMStructure, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<DDMStructure, Object>> attributeGetterFunctions = new LinkedHashMap<String, Function<DDMStructure, Object>>();
		Map<String, BiConsumer<DDMStructure, ?>> attributeSetterBiConsumers = new LinkedHashMap<String, BiConsumer<DDMStructure, ?>>();


		attributeGetterFunctions.put("uuid", DDMStructure::getUuid);
		attributeSetterBiConsumers.put("uuid", (BiConsumer<DDMStructure, String>)DDMStructure::setUuid);
		attributeGetterFunctions.put("structureId", DDMStructure::getStructureId);
		attributeSetterBiConsumers.put("structureId", (BiConsumer<DDMStructure, Long>)DDMStructure::setStructureId);
		attributeGetterFunctions.put("groupId", DDMStructure::getGroupId);
		attributeSetterBiConsumers.put("groupId", (BiConsumer<DDMStructure, Long>)DDMStructure::setGroupId);
		attributeGetterFunctions.put("companyId", DDMStructure::getCompanyId);
		attributeSetterBiConsumers.put("companyId", (BiConsumer<DDMStructure, Long>)DDMStructure::setCompanyId);
		attributeGetterFunctions.put("userId", DDMStructure::getUserId);
		attributeSetterBiConsumers.put("userId", (BiConsumer<DDMStructure, Long>)DDMStructure::setUserId);
		attributeGetterFunctions.put("userName", DDMStructure::getUserName);
		attributeSetterBiConsumers.put("userName", (BiConsumer<DDMStructure, String>)DDMStructure::setUserName);
		attributeGetterFunctions.put("versionUserId", DDMStructure::getVersionUserId);
		attributeSetterBiConsumers.put("versionUserId", (BiConsumer<DDMStructure, Long>)DDMStructure::setVersionUserId);
		attributeGetterFunctions.put("versionUserName", DDMStructure::getVersionUserName);
		attributeSetterBiConsumers.put("versionUserName", (BiConsumer<DDMStructure, String>)DDMStructure::setVersionUserName);
		attributeGetterFunctions.put("createDate", DDMStructure::getCreateDate);
		attributeSetterBiConsumers.put("createDate", (BiConsumer<DDMStructure, Date>)DDMStructure::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", DDMStructure::getModifiedDate);
		attributeSetterBiConsumers.put("modifiedDate", (BiConsumer<DDMStructure, Date>)DDMStructure::setModifiedDate);
		attributeGetterFunctions.put("parentStructureId", DDMStructure::getParentStructureId);
		attributeSetterBiConsumers.put("parentStructureId", (BiConsumer<DDMStructure, Long>)DDMStructure::setParentStructureId);
		attributeGetterFunctions.put("classNameId", DDMStructure::getClassNameId);
		attributeSetterBiConsumers.put("classNameId", (BiConsumer<DDMStructure, Long>)DDMStructure::setClassNameId);
		attributeGetterFunctions.put("structureKey", DDMStructure::getStructureKey);
		attributeSetterBiConsumers.put("structureKey", (BiConsumer<DDMStructure, String>)DDMStructure::setStructureKey);
		attributeGetterFunctions.put("version", DDMStructure::getVersion);
		attributeSetterBiConsumers.put("version", (BiConsumer<DDMStructure, String>)DDMStructure::setVersion);
		attributeGetterFunctions.put("name", DDMStructure::getName);
		attributeSetterBiConsumers.put("name", (BiConsumer<DDMStructure, String>)DDMStructure::setName);
		attributeGetterFunctions.put("description", DDMStructure::getDescription);
		attributeSetterBiConsumers.put("description", (BiConsumer<DDMStructure, String>)DDMStructure::setDescription);
		attributeGetterFunctions.put("definition", DDMStructure::getDefinition);
		attributeSetterBiConsumers.put("definition", (BiConsumer<DDMStructure, String>)DDMStructure::setDefinition);
		attributeGetterFunctions.put("storageType", DDMStructure::getStorageType);
		attributeSetterBiConsumers.put("storageType", (BiConsumer<DDMStructure, String>)DDMStructure::setStorageType);
		attributeGetterFunctions.put("type", DDMStructure::getType);
		attributeSetterBiConsumers.put("type", (BiConsumer<DDMStructure, Integer>)DDMStructure::setType);
		attributeGetterFunctions.put("lastPublishDate", DDMStructure::getLastPublishDate);
		attributeSetterBiConsumers.put("lastPublishDate", (BiConsumer<DDMStructure, Date>)DDMStructure::setLastPublishDate);


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
	public long getStructureId() {
		return _structureId;
	}

	@Override
	public void setStructureId(long structureId) {
		_structureId = structureId;
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
	public long getVersionUserId() {
		return _versionUserId;
	}

	@Override
	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	@Override
	public String getVersionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getVersionUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setVersionUserUuid(String versionUserUuid) {
	}

	@JSON
	@Override
	public String getVersionUserName() {
		if (_versionUserName == null) {
			return "";
		}
		else {
			return _versionUserName;
		}
	}

	@Override
	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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
	public long getParentStructureId() {
		return _parentStructureId;
	}

	@Override
	public void setParentStructureId(long parentStructureId) {
		_columnBitmask |= PARENTSTRUCTUREID_COLUMN_BITMASK;

		if (!_setOriginalParentStructureId) {
			_setOriginalParentStructureId = true;

			_originalParentStructureId = _parentStructureId;
		}

		_parentStructureId = parentStructureId;
	}

	public long getOriginalParentStructureId() {
		return _originalParentStructureId;
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public String getStructureKey() {
		if (_structureKey == null) {
			return "";
		}
		else {
			return _structureKey;
		}
	}

	@Override
	public void setStructureKey(String structureKey) {
		_columnBitmask |= STRUCTUREKEY_COLUMN_BITMASK;

		if (_originalStructureKey == null) {
			_originalStructureKey = _structureKey;
		}

		_structureKey = structureKey;
	}

	public String getOriginalStructureKey() {
		return GetterUtil.getString(_originalStructureKey);
	}

	@JSON
	@Override
	public String getVersion() {
		if (_version == null) {
			return "";
		}
		else {
			return _version;
		}
	}

	@Override
	public void setVersion(String version) {
		_version = version;
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
	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getName(), languageId,
			useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	@Override
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	@Override
	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	@Override
	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(LocalizationUtil.updateLocalization(getName(), "Name",
					name, languageId, defaultLanguageId));
		}
		else {
			setName(LocalizationUtil.removeLocalization(getName(), "Name",
					languageId));
		}
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(LocalizationUtil.updateLocalization(nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
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
	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getDescription(), languageId,
			useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	@Override
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	@Override
	public void setDescription(String description) {
		_columnBitmask |= DESCRIPTION_COLUMN_BITMASK;

		if (_originalDescription == null) {
			_originalDescription = _description;
		}

		_description = description;
	}

	@Override
	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		setDescription(LocalizationUtil.updateLocalization(descriptionMap,
				getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	public String getOriginalDescription() {
		return GetterUtil.getString(_originalDescription);
	}

	@JSON
	@Override
	public String getDefinition() {
		if (_definition == null) {
			return "";
		}
		else {
			return _definition;
		}
	}

	@Override
	public void setDefinition(String definition) {
		_definition = definition;
	}

	@JSON
	@Override
	public String getStorageType() {
		if (_storageType == null) {
			return "";
		}
		else {
			return _storageType;
		}
	}

	@Override
	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	@JSON
	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_type = type;
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

	public String getClassName() {
		return null;
	}

	public void setClassName(String className) {
	}

	public com.liferay.dynamic.data.mapping.model.DDMForm getDDMForm() {
		return null;
	}

	public void setDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm) {
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				DDMStructure.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DDMStructure.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> nameMap = getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		Map<Locale, String> descriptionMap = getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getName();

		if (xml == null) {
			return "";
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(DDMStructure.class.getName(),
				getPrimaryKey(), defaultLocale, availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String name = getName(defaultLocale);

		if (Validator.isNull(name)) {
			setName(getName(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setName(getName(defaultLocale), defaultLocale, defaultLocale);
		}

		String description = getDescription(defaultLocale);

		if (Validator.isNull(description)) {
			setDescription(getDescription(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setDescription(getDescription(defaultLocale), defaultLocale,
				defaultLocale);
		}
	}

	@Override
	public DDMStructure toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DDMStructure)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		ddmStructureImpl.setUuid(getUuid());
		ddmStructureImpl.setStructureId(getStructureId());
		ddmStructureImpl.setGroupId(getGroupId());
		ddmStructureImpl.setCompanyId(getCompanyId());
		ddmStructureImpl.setUserId(getUserId());
		ddmStructureImpl.setUserName(getUserName());
		ddmStructureImpl.setVersionUserId(getVersionUserId());
		ddmStructureImpl.setVersionUserName(getVersionUserName());
		ddmStructureImpl.setCreateDate(getCreateDate());
		ddmStructureImpl.setModifiedDate(getModifiedDate());
		ddmStructureImpl.setParentStructureId(getParentStructureId());
		ddmStructureImpl.setClassNameId(getClassNameId());
		ddmStructureImpl.setStructureKey(getStructureKey());
		ddmStructureImpl.setVersion(getVersion());
		ddmStructureImpl.setName(getName());
		ddmStructureImpl.setDescription(getDescription());
		ddmStructureImpl.setDefinition(getDefinition());
		ddmStructureImpl.setStorageType(getStorageType());
		ddmStructureImpl.setType(getType());
		ddmStructureImpl.setLastPublishDate(getLastPublishDate());

		ddmStructureImpl.resetOriginalValues();

		return ddmStructureImpl;
	}

	@Override
	public int compareTo(DDMStructure ddmStructure) {
		long primaryKey = ddmStructure.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructure)) {
			return false;
		}

		DDMStructure ddmStructure = (DDMStructure)obj;

		long primaryKey = ddmStructure.getPrimaryKey();

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
		DDMStructureModelImpl ddmStructureModelImpl = this;

		ddmStructureModelImpl._originalUuid = ddmStructureModelImpl._uuid;

		ddmStructureModelImpl._originalGroupId = ddmStructureModelImpl._groupId;

		ddmStructureModelImpl._setOriginalGroupId = false;

		ddmStructureModelImpl._originalCompanyId = ddmStructureModelImpl._companyId;

		ddmStructureModelImpl._setOriginalCompanyId = false;

		ddmStructureModelImpl._setModifiedDate = false;

		ddmStructureModelImpl._originalParentStructureId = ddmStructureModelImpl._parentStructureId;

		ddmStructureModelImpl._setOriginalParentStructureId = false;

		ddmStructureModelImpl._originalClassNameId = ddmStructureModelImpl._classNameId;

		ddmStructureModelImpl._setOriginalClassNameId = false;

		ddmStructureModelImpl._originalStructureKey = ddmStructureModelImpl._structureKey;

		ddmStructureModelImpl._originalName = ddmStructureModelImpl._name;

		ddmStructureModelImpl._originalDescription = ddmStructureModelImpl._description;

		setClassName(null);

		setDDMForm(null);

		ddmStructureModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDMStructure> toCacheModel() {
		DDMStructureCacheModel ddmStructureCacheModel = new DDMStructureCacheModel();

		ddmStructureCacheModel.uuid = getUuid();

		String uuid = ddmStructureCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddmStructureCacheModel.uuid = null;
		}

		ddmStructureCacheModel.structureId = getStructureId();

		ddmStructureCacheModel.groupId = getGroupId();

		ddmStructureCacheModel.companyId = getCompanyId();

		ddmStructureCacheModel.userId = getUserId();

		ddmStructureCacheModel.userName = getUserName();

		String userName = ddmStructureCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddmStructureCacheModel.userName = null;
		}

		ddmStructureCacheModel.versionUserId = getVersionUserId();

		ddmStructureCacheModel.versionUserName = getVersionUserName();

		String versionUserName = ddmStructureCacheModel.versionUserName;

		if ((versionUserName != null) && (versionUserName.length() == 0)) {
			ddmStructureCacheModel.versionUserName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddmStructureCacheModel.createDate = createDate.getTime();
		}
		else {
			ddmStructureCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddmStructureCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ddmStructureCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddmStructureCacheModel.parentStructureId = getParentStructureId();

		ddmStructureCacheModel.classNameId = getClassNameId();

		ddmStructureCacheModel.structureKey = getStructureKey();

		String structureKey = ddmStructureCacheModel.structureKey;

		if ((structureKey != null) && (structureKey.length() == 0)) {
			ddmStructureCacheModel.structureKey = null;
		}

		ddmStructureCacheModel.version = getVersion();

		String version = ddmStructureCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			ddmStructureCacheModel.version = null;
		}

		ddmStructureCacheModel.name = getName();

		String name = ddmStructureCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			ddmStructureCacheModel.name = null;
		}

		ddmStructureCacheModel.description = getDescription();

		String description = ddmStructureCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			ddmStructureCacheModel.description = null;
		}

		ddmStructureCacheModel.definition = getDefinition();

		String definition = ddmStructureCacheModel.definition;

		if ((definition != null) && (definition.length() == 0)) {
			ddmStructureCacheModel.definition = null;
		}

		ddmStructureCacheModel.storageType = getStorageType();

		String storageType = ddmStructureCacheModel.storageType;

		if ((storageType != null) && (storageType.length() == 0)) {
			ddmStructureCacheModel.storageType = null;
		}

		ddmStructureCacheModel.type = getType();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			ddmStructureCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			ddmStructureCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		ddmStructureCacheModel._className = getClassName();

		ddmStructureCacheModel._ddmForm = getDDMForm();

		return ddmStructureCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<DDMStructure, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<DDMStructure, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDMStructure, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((DDMStructure)this));
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
		Map<String, Function<DDMStructure, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<DDMStructure, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDMStructure, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((DDMStructure)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = DDMStructure.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			DDMStructure.class, ModelWrapper.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _structureId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _parentStructureId;
	private long _originalParentStructureId;
	private boolean _setOriginalParentStructureId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private String _structureKey;
	private String _originalStructureKey;
	private String _version;
	private String _name;
	private String _nameCurrentLanguageId;
	private String _originalName;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private String _originalDescription;
	private String _definition;
	private String _storageType;
	private int _type;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private DDMStructure _escapedModel;
}