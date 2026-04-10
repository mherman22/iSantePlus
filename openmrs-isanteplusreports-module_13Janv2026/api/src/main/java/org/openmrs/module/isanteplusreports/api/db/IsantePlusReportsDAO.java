/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.isanteplusreports.api.db;

import org.hibernate.SessionFactory;
import org.openmrs.module.isanteplusreports.Item;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;

/**
 * Database methods for {@link IsantePlusReportsService}.
 */
public interface IsantePlusReportsDAO {
	
	/*
	 * Add DAO methods here
	 */
	public SessionFactory getFactorySessionValue();
	
	public Item getItemByUuid(String uuid);
	
	public Item saveItem(Item item);
}
