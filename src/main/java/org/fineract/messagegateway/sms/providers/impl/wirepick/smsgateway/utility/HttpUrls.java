
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


import java.io.InputStream;
import java.io.StringReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;



import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.util.EntityUtils;

import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;



import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.MsgStatus;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.WpkClientConfig;

public class HttpUrls {

	
	private static final String GET = "GET";
	private PostMethod postMethod;

	public static void getJsonPrinter(WpkClientConfig config)
	{
		// this runs well too data is well formatted
		// printJsonDataMitData(config);
		Settings.printJsonDataMitData(config);

	}
	public static MsgStatus sendByPostMethod(String sUrl, NameValuePair[] data,Map<String, String> headers) throws Exception {

		HttpClient httpClient = new HttpClient() ;
		httpClient.getParams().setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
		httpClient.getParams().setParameter(HttpClientParams.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("Accept-Charset", "UTF-8");

		getJsonPrinter();

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				postMethod.addRequestHeader(entry.getKey(), entry.getValue());
				System.out.println(" print the name value pairs ......");
			}

		} else {
			System.out.println(" the headers are empty ");
		}
		postMethod.addParameters(data);

		try {
			int statusCode = httpClient.executeMethod(postMethod);

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

	public static MsgStatus jsonPostRqstWithHttp(String[] Args) {

		URL url = new URL ("https://ebridgeafrica.com/api/v1/sendsms");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		String jsonInputString = "{\"userid\":\"ACTB\",\"password\":\"VW3L8ttd\",\"message\":\"17feb-at-1515 - json api\",\"phone\":\"23280277388\",\"sender\":\"ACTB\"}";

		//String jsonInputString = printJsonDataMitData(config);

		try(OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}
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
