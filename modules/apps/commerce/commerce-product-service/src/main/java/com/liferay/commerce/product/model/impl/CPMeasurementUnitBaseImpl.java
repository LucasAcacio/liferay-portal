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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalServiceUtil;

/**
 * The extended model base implementation for the CPMeasurementUnit service. Represents a row in the &quot;CPMeasurementUnit&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPMeasurementUnitImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CPMeasurementUnitImpl
 * @see CPMeasurementUnit
 * @generated
 */
public abstract class CPMeasurementUnitBaseImpl
	extends CPMeasurementUnitModelImpl implements CPMeasurementUnit {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp measurement unit model instance should use the <code>CPMeasurementUnit</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CPMeasurementUnitLocalServiceUtil.addCPMeasurementUnit(this);
		}
		else {
			CPMeasurementUnitLocalServiceUtil.updateCPMeasurementUnit(this);
		}
	}

}