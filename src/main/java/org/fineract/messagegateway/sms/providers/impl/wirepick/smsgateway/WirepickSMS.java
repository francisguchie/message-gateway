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

package org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway;

import org.apache.commons.httpclient.NameValuePair;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.Utility.*;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.*;


public class WirepickSMS {
	
	public MsgStatus SendGETSMS(WpkClientConfig clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		String httpUrl = Settings.HTTPparameters(clientConfig) ; 
		if(httpUrl != null && httpUrl.startsWith(Settings.HOST))
		{
			//Guchie added
			System.out.println("printing the httpUrl thats set line 37 of WirepickSMS.java");
			System.out.printf(httpUrl);
			//Guchie added

			return HttpUrls.sendByUrlHttpConnection(httpUrl) ;
			//throws Exception("Could not do stuff  :( ");

		}

		throw new Exception("Could not do stuff  :( " );
		//return null;
	}
	
	
	public MsgStatus SendPOSTSMS(WpkClientConfig clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		String httpUrl = Settings.HOST ;

		//Guchie added
		System.out.println("printing the httpUrl thats set line 55 of WirepickSMS.java ");
		System.out.println(httpUrl);
		//Guchie added

		NameValuePair[] valuePairs = Settings.GetParameters(clientConfig) ;

		// Guchie added
		System.out.println("printing the httpUrl thats set line 62 of WirepickSMS.java ");
		System.out.println(httpUrl);
		// Guchie added
		return HttpUrls.sendByPostMethod(httpUrl, valuePairs, null);
	}

}
