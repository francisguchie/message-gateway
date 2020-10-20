/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model;

public class MsgStatusAppHive {

	//Reply from appHive
/*
	{
    "Cost": 0.0,
    "Destination": "23279194407",
    "Id": "6ea123d1-6369-4159-9020-c2a551902b5f",
    "Ticket": "6ea123d1-6369-4159-9020-c2a551902b5f",
    "ClientReference": "json Messages",
    "Status": "pending"
	}
*/

	private float Cost;
	private String Destination;
	private String Id;
	private String Ticket;
	private String ClientReference;
	private String Status;


	public float getCost() {
		return Cost;
	}
	public void setCost(float Cost) {
		this.Cost = Cost;
	}

	public String getDestination() {
		return Destination;
	}
	public void setDestination(String Destination) {
		this.Destination = Destination;
	}

	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}

	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String Ticket) {
		this.Ticket = Ticket;
	}

	public String getClientReference() {
		return ClientReference;
	}
	public void setClientReference(String ClientReference) {
		this.ClientReference = ClientReference;
	}

	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}


}
