/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.outgoingmessageexceptions.api.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingFieldName;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingOrder;
import org.openmrs.module.outgoingmessageexceptions.api.utils.OutgoingMessageExceptionsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository("outgoingmessageexceptions.OutgoingMessageExceptionsDao")
public class OutgoingMessageExceptionsDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public OutgoingMessage getItemByUuid(String uuid) {
		return (OutgoingMessage) getSession().createCriteria(OutgoingMessage.class)
		        .add(Restrictions.eq(OutgoingMessageExceptionsConstants.UUID_COLUMN_NAME, uuid)).uniqueResult();
	}
	
	public OutgoingMessage saveItem(OutgoingMessage outgoingMessage) {
		Session session = sessionFactory.getHibernateSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(outgoingMessage);
			tx.commit();
		}
		catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return outgoingMessage;
	}
	
	public OutgoingMessage getMessageById(Integer id) {
		return (OutgoingMessage) getSession().createCriteria(OutgoingMessage.class).add(Restrictions.eq("id", id))
		        .uniqueResult();
	}
	
	public List<OutgoingMessage> getPaginatedMessages(Integer page, Integer pageSize, LocalDate from,
	        SortingFieldName sortingFieldName, SortingOrder order, MessageType type, Boolean failed, Boolean retried) {
		
		Criteria selectCriteria = createSelectCriteria(from, sortingFieldName, order, type, failed, retried);
		
		selectCriteria.setFirstResult((page - 1) * pageSize);
		selectCriteria.setMaxResults(pageSize);
		
		return (List<OutgoingMessage>) selectCriteria.list();
	}
	
	public List<OutgoingMessage> getAllMessagesFrom(LocalDate from) {
		return (List<OutgoingMessage>) getSession()
		        .createCriteria(OutgoingMessage.class)
		        .add(
		            Restrictions.ge(OutgoingMessageExceptionsConstants.TIMESTAMP_COLUMN_NAME,
		                convertLocalDateToTimestamp(from))).list();
	}
	
	public List<OutgoingMessage> getAllMessages() {
		return (List<OutgoingMessage>) getSession().createCriteria(OutgoingMessage.class).list();
	}
	
	public Long getCountOfMessages(LocalDate from, SortingFieldName sortingFieldName, SortingOrder order, MessageType type,
	        Boolean failed, Boolean retried) {
		return (Long) createSelectCriteria(from, sortingFieldName, order, type, failed, retried)
		        .setProjection(Projections.rowCount()).list().get(0);
	}
	
	private Date convertLocalDateToTimestamp(LocalDate localDate) {
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
	
	public List<OutgoingMessage> getFailedMessagesByTypeChronologically(MessageType type) {
		return (List<OutgoingMessage>)getSession().createCriteria(OutgoingMessage.class)
				.add(Restrictions.eq(OutgoingMessageExceptionsConstants.FAILURE_COLUMN_NAME, true))
				.add(Restrictions.eq(OutgoingMessageExceptionsConstants.TYPE_COLUMN_NAME, type.name()))
				.addOrder(Order.asc(OutgoingMessageExceptionsConstants.TIMESTAMP_COLUMN_NAME))
				.list();
	}
	
	private Criteria createSelectCriteria(LocalDate from, SortingFieldName sortingFieldName, SortingOrder order,
	        MessageType type, Boolean failed, Boolean retried) {
		Criteria selectCriteria = getSession().createCriteria(OutgoingMessage.class);
		
		if (from != null) {
			selectCriteria.add(Restrictions.ge(OutgoingMessageExceptionsConstants.TIMESTAMP_COLUMN_NAME,
			    convertLocalDateToTimestamp(from)));
		}
		
		switch (order) {
			case ASC:
				selectCriteria.addOrder(Order.asc(sortingFieldName.name().toLowerCase()));
				break;
			case DESC:
				selectCriteria.addOrder(Order.desc(sortingFieldName.name().toLowerCase()));
				break;
			default:
				throw new IllegalArgumentException("Illegal order");
		}
		
		if (type != null) {
			selectCriteria.add(Restrictions.eq(OutgoingMessageExceptionsConstants.TYPE_COLUMN_NAME, type.name()));
		}
		
		if (failed) {
			selectCriteria.add(Restrictions.eq(OutgoingMessageExceptionsConstants.FAILURE_COLUMN_NAME, true));
		}
		
		if (!retried) {
			selectCriteria.add(Restrictions.eq(OutgoingMessageExceptionsConstants.RETRIED_COLUMN_NAME, false));
		}
		
		return selectCriteria;
	}
}
