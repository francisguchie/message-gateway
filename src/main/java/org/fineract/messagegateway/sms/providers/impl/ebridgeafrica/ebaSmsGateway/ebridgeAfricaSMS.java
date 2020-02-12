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

package org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway;

import org.apache.commons.httpclient.NameValuePair;
import org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaUtility.*;
import org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaModel.*;


public class ebridgeAfricaSMS {
	
	public ebaMsgStatus SendGETSMS(ebridgeAfricaSMS clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		String httpUrl = ebaSettings.HTTPparameters(clientConfig) ;
		if(httpUrl != null && httpUrl.startsWith(ebaSettings.HOST))
		{
			return HttpUrls.sendByUrlHttpConnection(httpUrl) ; 
		}
		
		return null;
	}
	
	
	public ebaMsgStatus SendPOSTSMS(ebridgeAfricaSMS clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		System.out.println(" throwing a null pointer Exception")
		String httpUrl = ebaSettings.HOST ;
		NameValuePair[] valuePairs = ebaSettings.GetParameters(clientConfig) ;
		return HttpUrls.sendByPostMethod(httpUrl, valuePairs, null);
	}

}
