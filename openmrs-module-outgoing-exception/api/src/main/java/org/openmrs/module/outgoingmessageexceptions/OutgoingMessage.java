/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.outgoingmessageexceptions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import javax.persistence.Lob;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Please note that a corresponding table schema must be created in liquibase.xml.
 */
@Entity(name = "outgoingmessageexceptions.OutgoingMessage")
@Table(name = "outgoingmessageexceptions_message")
public class OutgoingMessage extends BaseOpenmrsData {
	
	@Id
	@GeneratedValue
	@Column(name = "outgoingmessageexceptions_message_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	private User owner;
	
	@Lob
	@Basic
	@Column(name = "messageBody")
	private String messageBody;
	
	@Basic
	@Column(name = "timestamp")
	private Date timestamp;
	
	@Lob
	@Basic
	@Column(name = "failureReason")
	private String failureReason;
	
	@Basic
	@Column(name = "destination")
	private String destination;
	
	@Basic
	@Column(name = "type")
	private String type;
	
	@Basic
	@Column(name = "failure")
	private Boolean failure;
	
	@Basic
	@Column(name = "retried")
	private Boolean retried;
	
	@Basic
	@Column(name = "retryTimestamp")
	private Date retryTimestamp;
	
	@Lob
	@Basic
	@Column(name = "retryResult")
	private String retryResult;
	
	public OutgoingMessage() {
	}
	
	public OutgoingMessage(Integer id, Integer ownerId, String messageBody, Date timestamp, String failureReason,
	    String destination, String type, boolean failure) {
		this.id = id;
		this.owner = new User(ownerId);
		this.messageBody = messageBody;
		this.timestamp = timestamp;
		this.failureReason = failureReason;
		this.destination = destination;
		this.type = type;
		this.failure = failure;
		this.retried = false;
		this.retryTimestamp = null;
		this.retryResult = null;
	}
	
	public OutgoingMessage(Integer id, Integer ownerId, String messageBody, Date timestamp, String failureReason,
	    String destination, String type, boolean failure, boolean retried, Date retryTimestamp, String retryResult) {
		this.id = id;
		this.owner = new User(ownerId);
		this.messageBody = messageBody;
		this.timestamp = timestamp;
		this.failureReason = failureReason;
		this.destination = destination;
		this.type = type;
		this.failure = failure;
		this.retried = retried;
		this.retryTimestamp = retryTimestamp;
		this.retryResult = retryResult;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getUuid() {
		return super.getUuid();
	}
	
	@Override
	public void setUuid(String uuid) {
		super.setUuid(uuid);
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Boolean getFailure() {
		return failure;
	}
	
	public void setFailure(Boolean failure) {
		this.failure = failure;
	}
	
	public String getMessageBody() {
		return messageBody;
	}
	
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	public String getFailureReason() {
		return failureReason;
	}
	
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	public Boolean getRetried() {
		return retried;
	}
	
	public void setRetried(Boolean retried) {
		this.retried = retried;
	}
	
	public Date getRetryTimestamp() {
		return retryTimestamp;
	}
	
	public void setRetryTimestamp(Date retryTimestamp) {
		this.retryTimestamp = retryTimestamp;
	}
	
	public String getRetryResult() {
		return retryResult;
	}
	
	public void setRetryResult(String retryResult) {
		this.retryResult = retryResult;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		OutgoingMessage that = (OutgoingMessage) o;

		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (id != null ? id.hashCode() : 0);
		return result;
	}

	public static class OutgoingMessageSerializer extends StdSerializer<OutgoingMessage> {
		
		public OutgoingMessageSerializer() {
			this(null);
		}
		
		public OutgoingMessageSerializer(Class<OutgoingMessage> t) {
			super(t);
		}
		
		@Override
		public void serialize(OutgoingMessage object, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeStartObject();
			jgen.writeNumberField("id", object.id);
			jgen.writeStringField("messageBody", object.messageBody);
			jgen.writeStringField("timestamp", object.timestamp.toString());
			jgen.writeStringField("failureReason", object.failureReason);
			jgen.writeStringField("destination", object.destination);
			jgen.writeStringField("type", object.type);
			jgen.writeBooleanField("failure", object.failure);
			
			if (null != object.getOwner()) {
				jgen.writeNumberField("owner", object.getOwner().getUserId());
			}
			
			jgen.writeEndObject();
		}
	}
	
	public static class OutgoingMessageGsonSerializer implements JsonSerializer<OutgoingMessage> {
		
		@Override
		public JsonElement serialize(OutgoingMessage src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			
			object.addProperty("id", src.id);
			if (src.owner != null) {
				JsonObject owner = new JsonObject();
				
				owner.addProperty("uuid", src.owner.getUuid());
				owner.addProperty("name", src.owner.getDisplayString());
				
				object.add("user", owner);
			}
			object.addProperty("destination", src.destination);
			object.addProperty("timestamp", src.timestamp.toString());
			object.addProperty("failure", src.failure);
			object.addProperty("type", src.type);
			object.addProperty("messageBody", src.messageBody);
			
			return object;
		}
	}
}
