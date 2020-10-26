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

package org.fineract.messagegateway.sms.providers.impl.wirepick.Constants;

public class ConstantValues {

	    // For the Get method only ----------------------------------------------------------
        //https://api.sierrahive.com/v1/messages/sms
        //?clientid=879jgbnhg
        //&clientsecret=456hdbfdsfsvnm,xcdjk8723232uijkdsjksdjsd
        //&token=3f4abf4e047946db97328abf43eec91f
        //&from=ACTB
        //&to=+23278448846
        //&reference=674843
        //&content=Testing APTELL SMS Servers


	public static final String SMS_CLIENT_USER_NAME = "879jgbnhg";
	public static final String SMS_CLIENT_PASSWORD = "456hdbfdsfsvnm,xcdjk8723232uijkdsjksdjsd";
	public static final String SMS_CLIENT_TOKEN = "3f4abf4e047946db97328abf43eec91f";
	public static final String SMS_CLIENT_FROM = "ACTB";
	public static final String SMS_CLIENT_REFERENCE = "Bank Alert";
	//TO DO - we must use callbackUrl to get reports
	public static final String SMS_CLIENT_CALLBACKURL = "";

	//public static final String SMS_CLIENT_USER_NAME = "advanc";
	//public static final String SMS_CLIENT_PASSWORD = "2017Aumf#";

	
}
