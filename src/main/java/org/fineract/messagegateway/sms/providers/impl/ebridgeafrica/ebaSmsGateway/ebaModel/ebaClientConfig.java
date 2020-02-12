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

package org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaModel;

public class ebaClientConfig {

	// ?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB "
	private String userid ;
	private String password ;
	private String message;
	private String phone ;
	private String sender;
	// private String affiliate;
	// private String tag;
	
	public ebaClientConfig()
	{
		
	}
	
	public ebaClientConfig(String userid, String password, String message, String phone, String sender) {

		this.userid = userid;
		this.password = password;
		this.message = message;
		this.phone = phone;
		this.sender = sender;
		//this.affiliate = affiliate;
		//this.tag = tag;
	}
	public String getUserId() {
		return userid;
	}
	public void setUserId(String userid) {

		this.userid = userid;
	}
	public String getPassword() {

		return password;
	}
	public void setPassword(String password) {

		this.password = password;
	}
	// ?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSender() {
		return sender;
	}
	public void setSenderid(String sender) {
		this.sender = sender;
	}

}
