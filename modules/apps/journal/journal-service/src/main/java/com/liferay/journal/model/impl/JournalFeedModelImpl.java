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

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFeedModel;
import com.liferay.journal.model.JournalFeedSoap;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

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
 * The base model implementation for the JournalFeed service. Represents a row in the &quot;JournalFeed&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link JournalFeedModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link JournalFeedImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedImpl
 * @see JournalFeed
 * @see JournalFeedModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class JournalFeedModelImpl extends BaseModelImpl<JournalFeed>
	implements JournalFeedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a journal feed model instance should use the {@link JournalFeed} interface instead.
	 */
	public static final String TABLE_NAME = "JournalFeed";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "id_", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "feedId", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description_", Types.VARCHAR },
			{ "DDMStructureKey", Types.VARCHAR },
			{ "DDMTemplateKey", Types.VARCHAR },
			{ "DDMRendererTemplateKey", Types.VARCHAR },
			{ "delta", Types.INTEGER },
			{ "orderByCol", Types.VARCHAR },
			{ "orderByType", Types.VARCHAR },
			{ "targetLayoutFriendlyUrl", Types.VARCHAR },
			{ "targetPortletId", Types.VARCHAR },
			{ "contentField", Types.VARCHAR },
			{ "feedFormat", Types.VARCHAR },
			{ "feedVersion", Types.DOUBLE },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("id_", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("feedId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMStructureKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMTemplateKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMRendererTemplateKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("delta", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("orderByCol", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("orderByType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("targetLayoutFriendlyUrl", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("targetPortletId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("contentField", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("feedFormat", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("feedVersion", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table JournalFeed (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,feedId VARCHAR(75) null,name VARCHAR(75) null,description_ STRING null,DDMStructureKey VARCHAR(75) null,DDMTemplateKey VARCHAR(75) null,DDMRendererTemplateKey VARCHAR(75) null,delta INTEGER,orderByCol VARCHAR(75) null,orderByType VARCHAR(75) null,targetLayoutFriendlyUrl VARCHAR(255) null,targetPortletId VARCHAR(200) null,contentField VARCHAR(75) null,feedFormat VARCHAR(75) null,feedVersion DOUBLE,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table JournalFeed";
	public static final String ORDER_BY_JPQL = " ORDER BY journalFeed.feedId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY JournalFeed.feedId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.journal.model.JournalFeed"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.journal.model.JournalFeed"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.journal.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.journal.model.JournalFeed"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long FEEDID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static JournalFeed toModel(JournalFeedSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		JournalFeed model = new JournalFeedImpl();

		model.setUuid(soapModel.getUuid());
		model.setId(soapModel.getId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFeedId(soapModel.getFeedId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setDDMStructureKey(soapModel.getDDMStructureKey());
		model.setDDMTemplateKey(soapModel.getDDMTemplateKey());
		model.setDDMRendererTemplateKey(soapModel.getDDMRendererTemplateKey());
		model.setDelta(soapModel.getDelta());
		model.setOrderByCol(soapModel.getOrderByCol());
		model.setOrderByType(soapModel.getOrderByType());
		model.setTargetLayoutFriendlyUrl(soapModel.getTargetLayoutFriendlyUrl());
		model.setTargetPortletId(soapModel.getTargetPortletId());
		model.setContentField(soapModel.getContentField());
		model.setFeedFormat(soapModel.getFeedFormat());
		model.setFeedVersion(soapModel.getFeedVersion());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<JournalFeed> toModels(JournalFeedSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<JournalFeed> models = new ArrayList<JournalFeed>(soapModels.length);

		for (JournalFeedSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.journal.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.journal.model.JournalFeed"));

	public JournalFeedModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _id;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _id;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return JournalFeed.class;
	}

	@Override
	public String getModelClassName() {
		return JournalFeed.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<JournalFeed, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<JournalFeed, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFeed, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((JournalFeed)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<JournalFeed, Object>> attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<JournalFeed, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((JournalFeed)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<JournalFeed, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<JournalFeed, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<JournalFeed, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<JournalFeed, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<JournalFeed, Object>> attributeGetterFunctions = new LinkedHashMap<String, Function<JournalFeed, Object>>();
		Map<String, BiConsumer<JournalFeed, ?>> attributeSetterBiConsumers = new LinkedHashMap<String, BiConsumer<JournalFeed, ?>>();


		attributeGetterFunctions.put("uuid", JournalFeed::getUuid);
		attributeSetterBiConsumers.put("uuid", (BiConsumer<JournalFeed, String>)JournalFeed::setUuid);
		attributeGetterFunctions.put("id", JournalFeed::getId);
		attributeSetterBiConsumers.put("id", (BiConsumer<JournalFeed, Long>)JournalFeed::setId);
		attributeGetterFunctions.put("groupId", JournalFeed::getGroupId);
		attributeSetterBiConsumers.put("groupId", (BiConsumer<JournalFeed, Long>)JournalFeed::setGroupId);
		attributeGetterFunctions.put("companyId", JournalFeed::getCompanyId);
		attributeSetterBiConsumers.put("companyId", (BiConsumer<JournalFeed, Long>)JournalFeed::setCompanyId);
		attributeGetterFunctions.put("userId", JournalFeed::getUserId);
		attributeSetterBiConsumers.put("userId", (BiConsumer<JournalFeed, Long>)JournalFeed::setUserId);
		attributeGetterFunctions.put("userName", JournalFeed::getUserName);
		attributeSetterBiConsumers.put("userName", (BiConsumer<JournalFeed, String>)JournalFeed::setUserName);
		attributeGetterFunctions.put("createDate", JournalFeed::getCreateDate);
		attributeSetterBiConsumers.put("createDate", (BiConsumer<JournalFeed, Date>)JournalFeed::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", JournalFeed::getModifiedDate);
		attributeSetterBiConsumers.put("modifiedDate", (BiConsumer<JournalFeed, Date>)JournalFeed::setModifiedDate);
		attributeGetterFunctions.put("feedId", JournalFeed::getFeedId);
		attributeSetterBiConsumers.put("feedId", (BiConsumer<JournalFeed, String>)JournalFeed::setFeedId);
		attributeGetterFunctions.put("name", JournalFeed::getName);
		attributeSetterBiConsumers.put("name", (BiConsumer<JournalFeed, String>)JournalFeed::setName);
		attributeGetterFunctions.put("description", JournalFeed::getDescription);
		attributeSetterBiConsumers.put("description", (BiConsumer<JournalFeed, String>)JournalFeed::setDescription);
		attributeGetterFunctions.put("DDMStructureKey", JournalFeed::getDDMStructureKey);
		attributeSetterBiConsumers.put("DDMStructureKey", (BiConsumer<JournalFeed, String>)JournalFeed::setDDMStructureKey);
		attributeGetterFunctions.put("DDMTemplateKey", JournalFeed::getDDMTemplateKey);
		attributeSetterBiConsumers.put("DDMTemplateKey", (BiConsumer<JournalFeed, String>)JournalFeed::setDDMTemplateKey);
		attributeGetterFunctions.put("DDMRendererTemplateKey", JournalFeed::getDDMRendererTemplateKey);
		attributeSetterBiConsumers.put("DDMRendererTemplateKey", (BiConsumer<JournalFeed, String>)JournalFeed::setDDMRendererTemplateKey);
		attributeGetterFunctions.put("delta", JournalFeed::getDelta);
		attributeSetterBiConsumers.put("delta", (BiConsumer<JournalFeed, Integer>)JournalFeed::setDelta);
		attributeGetterFunctions.put("orderByCol", JournalFeed::getOrderByCol);
		attributeSetterBiConsumers.put("orderByCol", (BiConsumer<JournalFeed, String>)JournalFeed::setOrderByCol);
		attributeGetterFunctions.put("orderByType", JournalFeed::getOrderByType);
		attributeSetterBiConsumers.put("orderByType", (BiConsumer<JournalFeed, String>)JournalFeed::setOrderByType);
		attributeGetterFunctions.put("targetLayoutFriendlyUrl", JournalFeed::getTargetLayoutFriendlyUrl);
		attributeSetterBiConsumers.put("targetLayoutFriendlyUrl", (BiConsumer<JournalFeed, String>)JournalFeed::setTargetLayoutFriendlyUrl);
		attributeGetterFunctions.put("targetPortletId", JournalFeed::getTargetPortletId);
		attributeSetterBiConsumers.put("targetPortletId", (BiConsumer<JournalFeed, String>)JournalFeed::setTargetPortletId);
		attributeGetterFunctions.put("contentField", JournalFeed::getContentField);
		attributeSetterBiConsumers.put("contentField", (BiConsumer<JournalFeed, String>)JournalFeed::setContentField);
		attributeGetterFunctions.put("feedFormat", JournalFeed::getFeedFormat);
		attributeSetterBiConsumers.put("feedFormat", (BiConsumer<JournalFeed, String>)JournalFeed::setFeedFormat);
		attributeGetterFunctions.put("feedVersion", JournalFeed::getFeedVersion);
		attributeSetterBiConsumers.put("feedVersion", (BiConsumer<JournalFeed, Double>)JournalFeed::setFeedVersion);
		attributeGetterFunctions.put("lastPublishDate", JournalFeed::getLastPublishDate);
		attributeSetterBiConsumers.put("lastPublishDate", (BiConsumer<JournalFeed, Date>)JournalFeed::setLastPublishDate);


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
	public long getId() {
		return _id;
	}

	@Override
	public void setId(long id) {
		_id = id;
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
	public String getFeedId() {
		if (_feedId == null) {
			return "";
		}
		else {
			return _feedId;
		}
	}

	@Override
	public void setFeedId(String feedId) {
		_columnBitmask = -1L;

		if (_originalFeedId == null) {
			_originalFeedId = _feedId;
		}

		_feedId = feedId;
	}

	public String getOriginalFeedId() {
		return GetterUtil.getString(_originalFeedId);
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
		_name = name;
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
	public String getDDMStructureKey() {
		if (_DDMStructureKey == null) {
			return "";
		}
		else {
			return _DDMStructureKey;
		}
	}

	@Override
	public void setDDMStructureKey(String DDMStructureKey) {
		_DDMStructureKey = DDMStructureKey;
	}

	@JSON
	@Override
	public String getDDMTemplateKey() {
		if (_DDMTemplateKey == null) {
			return "";
		}
		else {
			return _DDMTemplateKey;
		}
	}

	@Override
	public void setDDMTemplateKey(String DDMTemplateKey) {
		_DDMTemplateKey = DDMTemplateKey;
	}

	@JSON
	@Override
	public String getDDMRendererTemplateKey() {
		if (_DDMRendererTemplateKey == null) {
			return "";
		}
		else {
			return _DDMRendererTemplateKey;
		}
	}

	@Override
	public void setDDMRendererTemplateKey(String DDMRendererTemplateKey) {
		_DDMRendererTemplateKey = DDMRendererTemplateKey;
	}

	@JSON
	@Override
	public int getDelta() {
		return _delta;
	}

	@Override
	public void setDelta(int delta) {
		_delta = delta;
	}

	@JSON
	@Override
	public String getOrderByCol() {
		if (_orderByCol == null) {
			return "";
		}
		else {
			return _orderByCol;
		}
	}

	@Override
	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	@JSON
	@Override
	public String getOrderByType() {
		if (_orderByType == null) {
			return "";
		}
		else {
			return _orderByType;
		}
	}

	@Override
	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	@JSON
	@Override
	public String getTargetLayoutFriendlyUrl() {
		if (_targetLayoutFriendlyUrl == null) {
			return "";
		}
		else {
			return _targetLayoutFriendlyUrl;
		}
	}

	@Override
	public void setTargetLayoutFriendlyUrl(String targetLayoutFriendlyUrl) {
		_targetLayoutFriendlyUrl = targetLayoutFriendlyUrl;
	}

	@JSON
	@Override
	public String getTargetPortletId() {
		if (_targetPortletId == null) {
			return "";
		}
		else {
			return _targetPortletId;
		}
	}

	@Override
	public void setTargetPortletId(String targetPortletId) {
		_targetPortletId = targetPortletId;
	}

	@JSON
	@Override
	public String getContentField() {
		if (_contentField == null) {
			return "";
		}
		else {
			return _contentField;
		}
	}

	@Override
	public void setContentField(String contentField) {
		_contentField = contentField;
	}

	@JSON
	@Override
	public String getFeedFormat() {
		if (_feedFormat == null) {
			return "";
		}
		else {
			return _feedFormat;
		}
	}

	@Override
	public void setFeedFormat(String feedFormat) {
		_feedFormat = feedFormat;
	}

	@JSON
	@Override
	public double getFeedVersion() {
		return _feedVersion;
	}

	@Override
	public void setFeedVersion(double feedVersion) {
		_feedVersion = feedVersion;
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

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				JournalFeed.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			JournalFeed.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public JournalFeed toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (JournalFeed)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		JournalFeedImpl journalFeedImpl = new JournalFeedImpl();

		journalFeedImpl.setUuid(getUuid());
		journalFeedImpl.setId(getId());
		journalFeedImpl.setGroupId(getGroupId());
		journalFeedImpl.setCompanyId(getCompanyId());
		journalFeedImpl.setUserId(getUserId());
		journalFeedImpl.setUserName(getUserName());
		journalFeedImpl.setCreateDate(getCreateDate());
		journalFeedImpl.setModifiedDate(getModifiedDate());
		journalFeedImpl.setFeedId(getFeedId());
		journalFeedImpl.setName(getName());
		journalFeedImpl.setDescription(getDescription());
		journalFeedImpl.setDDMStructureKey(getDDMStructureKey());
		journalFeedImpl.setDDMTemplateKey(getDDMTemplateKey());
		journalFeedImpl.setDDMRendererTemplateKey(getDDMRendererTemplateKey());
		journalFeedImpl.setDelta(getDelta());
		journalFeedImpl.setOrderByCol(getOrderByCol());
		journalFeedImpl.setOrderByType(getOrderByType());
		journalFeedImpl.setTargetLayoutFriendlyUrl(getTargetLayoutFriendlyUrl());
		journalFeedImpl.setTargetPortletId(getTargetPortletId());
		journalFeedImpl.setContentField(getContentField());
		journalFeedImpl.setFeedFormat(getFeedFormat());
		journalFeedImpl.setFeedVersion(getFeedVersion());
		journalFeedImpl.setLastPublishDate(getLastPublishDate());

		journalFeedImpl.resetOriginalValues();

		return journalFeedImpl;
	}

	@Override
	public int compareTo(JournalFeed journalFeed) {
		int value = 0;

		value = getFeedId().compareTo(journalFeed.getFeedId());

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

		if (!(obj instanceof JournalFeed)) {
			return false;
		}

		JournalFeed journalFeed = (JournalFeed)obj;

		long primaryKey = journalFeed.getPrimaryKey();

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
		JournalFeedModelImpl journalFeedModelImpl = this;

		journalFeedModelImpl._originalUuid = journalFeedModelImpl._uuid;

		journalFeedModelImpl._originalGroupId = journalFeedModelImpl._groupId;

		journalFeedModelImpl._setOriginalGroupId = false;

		journalFeedModelImpl._originalCompanyId = journalFeedModelImpl._companyId;

		journalFeedModelImpl._setOriginalCompanyId = false;

		journalFeedModelImpl._setModifiedDate = false;

		journalFeedModelImpl._originalFeedId = journalFeedModelImpl._feedId;

		journalFeedModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<JournalFeed> toCacheModel() {
		JournalFeedCacheModel journalFeedCacheModel = new JournalFeedCacheModel();

		journalFeedCacheModel.uuid = getUuid();

		String uuid = journalFeedCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			journalFeedCacheModel.uuid = null;
		}

		journalFeedCacheModel.id = getId();

		journalFeedCacheModel.groupId = getGroupId();

		journalFeedCacheModel.companyId = getCompanyId();

		journalFeedCacheModel.userId = getUserId();

		journalFeedCacheModel.userName = getUserName();

		String userName = journalFeedCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			journalFeedCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			journalFeedCacheModel.createDate = createDate.getTime();
		}
		else {
			journalFeedCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			journalFeedCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			journalFeedCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		journalFeedCacheModel.feedId = getFeedId();

		String feedId = journalFeedCacheModel.feedId;

		if ((feedId != null) && (feedId.length() == 0)) {
			journalFeedCacheModel.feedId = null;
		}

		journalFeedCacheModel.name = getName();

		String name = journalFeedCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			journalFeedCacheModel.name = null;
		}

		journalFeedCacheModel.description = getDescription();

		String description = journalFeedCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			journalFeedCacheModel.description = null;
		}

		journalFeedCacheModel.DDMStructureKey = getDDMStructureKey();

		String DDMStructureKey = journalFeedCacheModel.DDMStructureKey;

		if ((DDMStructureKey != null) && (DDMStructureKey.length() == 0)) {
			journalFeedCacheModel.DDMStructureKey = null;
		}

		journalFeedCacheModel.DDMTemplateKey = getDDMTemplateKey();

		String DDMTemplateKey = journalFeedCacheModel.DDMTemplateKey;

		if ((DDMTemplateKey != null) && (DDMTemplateKey.length() == 0)) {
			journalFeedCacheModel.DDMTemplateKey = null;
		}

		journalFeedCacheModel.DDMRendererTemplateKey = getDDMRendererTemplateKey();

		String DDMRendererTemplateKey = journalFeedCacheModel.DDMRendererTemplateKey;

		if ((DDMRendererTemplateKey != null) &&
				(DDMRendererTemplateKey.length() == 0)) {
			journalFeedCacheModel.DDMRendererTemplateKey = null;
		}

		journalFeedCacheModel.delta = getDelta();

		journalFeedCacheModel.orderByCol = getOrderByCol();

		String orderByCol = journalFeedCacheModel.orderByCol;

		if ((orderByCol != null) && (orderByCol.length() == 0)) {
			journalFeedCacheModel.orderByCol = null;
		}

		journalFeedCacheModel.orderByType = getOrderByType();

		String orderByType = journalFeedCacheModel.orderByType;

		if ((orderByType != null) && (orderByType.length() == 0)) {
			journalFeedCacheModel.orderByType = null;
		}

		journalFeedCacheModel.targetLayoutFriendlyUrl = getTargetLayoutFriendlyUrl();

		String targetLayoutFriendlyUrl = journalFeedCacheModel.targetLayoutFriendlyUrl;

		if ((targetLayoutFriendlyUrl != null) &&
				(targetLayoutFriendlyUrl.length() == 0)) {
			journalFeedCacheModel.targetLayoutFriendlyUrl = null;
		}

		journalFeedCacheModel.targetPortletId = getTargetPortletId();

		String targetPortletId = journalFeedCacheModel.targetPortletId;

		if ((targetPortletId != null) && (targetPortletId.length() == 0)) {
			journalFeedCacheModel.targetPortletId = null;
		}

		journalFeedCacheModel.contentField = getContentField();

		String contentField = journalFeedCacheModel.contentField;

		if ((contentField != null) && (contentField.length() == 0)) {
			journalFeedCacheModel.contentField = null;
		}

		journalFeedCacheModel.feedFormat = getFeedFormat();

		String feedFormat = journalFeedCacheModel.feedFormat;

		if ((feedFormat != null) && (feedFormat.length() == 0)) {
			journalFeedCacheModel.feedFormat = null;
		}

		journalFeedCacheModel.feedVersion = getFeedVersion();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			journalFeedCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			journalFeedCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return journalFeedCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<JournalFeed, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<JournalFeed, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFeed, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((JournalFeed)this));
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
		Map<String, Function<JournalFeed, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<JournalFeed, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<JournalFeed, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((JournalFeed)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = JournalFeed.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			JournalFeed.class, ModelWrapper.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _id;
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
	private String _feedId;
	private String _originalFeedId;
	private String _name;
	private String _description;
	private String _DDMStructureKey;
	private String _DDMTemplateKey;
	private String _DDMRendererTemplateKey;
	private int _delta;
	private String _orderByCol;
	private String _orderByType;
	private String _targetLayoutFriendlyUrl;
	private String _targetPortletId;
	private String _contentField;
	private String _feedFormat;
	private double _feedVersion;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private JournalFeed _escapedModel;
}