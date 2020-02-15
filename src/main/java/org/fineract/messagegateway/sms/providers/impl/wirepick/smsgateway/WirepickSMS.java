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
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;


import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.Utility.*;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.*;

import com.google.gson.Gson;

public class WirepickSMS {
	
	public MsgStatus SendGETSMS(WpkClientConfig clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		String httpUrl = Settings.HTTPparameters(clientConfig) ; 
		if(httpUrl != null && httpUrl.startsWith(Settings.HOST))
		{
			return HttpUrls.sendByUrlHttpConnection(httpUrl) ;
		}
		throw new Exception("Could not do stuff  :( " );
		//return null;
	}

	public MsgStatus SendPOSTSMS(WpkClientConfig clientConfig) throws Exception
	{
		if(clientConfig == null)
			throw new NullPointerException() ;
		try{
			String httpUrl = Settings.HOST ;

			NameValuePair[] valuePairs = Settings.GetParameters(clientConfig) ;

			// Guchie added
			System.out.println("SendPOSTSMS of WirepickSMS.java \n the array in use is " + Arrays.toString(valuePairs));

			Gson gson = new Gson();
			String json = gson.toJson(valuePairs);
			System.out.println(json);

			// (JSONObject)JSONSerializer.toJSON(valuePairs pair);
			// Guchie added

			return HttpUrls.sendByPostMethod(httpUrl, valuePairs, null);

		} catch (Exception e){
			System.out.println("Could not do stuff  :( in SendPOSTSMS");
		}

		// throw new Exception( );
	}
}
