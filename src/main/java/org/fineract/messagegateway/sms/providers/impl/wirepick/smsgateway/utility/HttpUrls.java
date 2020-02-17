
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


package org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.Utility;

import java.io.StringReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;



import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.MsgStatus;

public class HttpUrls {

	
	private static final String GET = "GET";
	private PostMethod postMethod;

	public static MsgStatus sendByPostMethod(String sUrl, NameValuePair[] data,Map<String, String> headers) throws Exception {

		HttpClient httpClient = new HttpClient() ;
		httpClient.getParams().setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
		httpClient.getParams().setParameter(HttpClientParams.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("Accept-Charset", "UTF-8");

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				postMethod.addRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		postMethod.addParameters(data);
		println("Sending this ${postMethod.toString()}");
		// I need to print the postMethod data
		//String str = EntityUtils.toString(postMethod.getRequestEntity());
		//System.out.println(str);


		try {
			int statusCode = httpClient.executeMethod(postMethod);

			/*
			 String httpResponse = postMethod.getResponseBodyAsString();
			 System.out.println("getting Response Body as String := " + httpResponse);
			 InputStream inputStream = postMethod.getResponseBodyAsStream();
			 byte[] buffer = new byte[1024 * 1024 * 5];
			 String path = null;
			 int length;
			 while ((length = inputStream.read(buffer)) > 0) {
			 				path = new String(buffer);
			 }
			 System.out.println("getting ResponseBody As Stream " + path);
			 System.out.println("==================");
			 */

			String statusCodeString = Integer.toString(statusCode);
			System.out.println("Status code is " + statusCodeString);

			if (statusCode == HttpStatus.SC_OK) {

				String httpResponse = postMethod.getResponseBodyAsString();
				System.out.println(" Response is " + httpResponse );

				return Settings.parseWirepickResultXML(new StringReader(httpResponse)) ; 
				
			}
		}  catch (Exception e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
			
		}
		return null;
	}

	public static MsgStatus sendByUrlHttpConnection(String url) throws Exception {

        HttpURLConnection con = null;
        try {
        	
            URL obj = new java.net.URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(GET);
			con.connect();

			System.out.println("Response code:" + con.getResponseCode());
			System.out.println("Response message:" + con.getResponseMessage());

			InputStream test = con.getErrorStream();
			String result = new BufferedReader(new InputStreamReader(test)).lines().collect(Collectors.joining("\n"));

            int responseCode = con.getResponseCode();

            if (responseCode == HttpStatus.SC_OK) {
				System.out.println(" SMS is sent to \n " + url);
              return  Settings.parseWirepickResultXML(con.getInputStream()) ; 
               
            } else {
				System.out.println(" SMS is not sent");
            	return null ; 
            }

        } catch (Exception ex) {
        	System.out.println(ex.getStackTrace());
           throw new Exception(ex) ; 
            
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
