
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
import java.io.OutputStreamWriter;



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
	private static final String CHARSET = "UTF-8";
	private static final String ACCEPT_TEXT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	private static final String KEEP_ALIVE = "keep-alive";
	private static final String LANGUAGES = "en-US,en;q=0.5";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0";
	private PostMethod postMethod;

	/** use string instead of MsgStatus lets see */
	public static String sendByPostMethod(String sUrl, NameValuePair[] data,Map<String, String> headers) throws Exception {

		HttpClient httpClient = new HttpClient() ;
		httpClient.getParams().setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
		httpClient.getParams().setParameter(HttpClientParams.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod postMethod = new PostMethod(sUrl);
		postMethod.setRequestHeader("Accept-Charset", "UTF-8");

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				postMethod.addRequestHeader(entry.getKey(), entry.getValue());
				System.out.println(" LINE 1 print the name value pairs ......");
				System.out.println(entry);
			}
			System.out.println(" LINE 2 print the name value pairs ......");
			System.out.println();

		} else {
			System.out.println(headers.toString());
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
				// return Settings.parseWirepickResultXML(new StringReader(httpResponse)) ;
			}
		}  catch (Exception e) {
			// throw e;
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	public static String sendByUrlHttpConnection(String url) throws Exception {

	    HttpURLConnection con = null;
        try {
        	
            URL obj = new java.net.URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(GET);
			con.setRequestProperty("Accept-Charset", CHARSET);
			con.setRequestProperty("Accept", ACCEPT_TEXT);
			con.setRequestProperty("Connection", KEEP_ALIVE);
			con.setRequestProperty("Accept-Language", LANGUAGES);
			con.setRequestProperty("User-Agent", USER_AGENT);


			con.connect();

            int responseCode = con.getResponseCode();
			System.out.println( "The response code = " + Integer.toString(responseCode));

            if (responseCode == HttpStatus.SC_OK) {
				// System.out.println(" SMS is sent to \n " + url);

			/**  THIS BELOW IS THE KILLER BUG that messed the whole code */
              //return  Settings.parseWirepickResultXML(con.getInputStream()) ;
               
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
        return null ;
    }

	public static String printJsonDataMitData2(WpkClientConfig config) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, String> items = new HashMap<>();
		String[] msisdnArray = {config.getMsisdn()};

		items.put("msisdn", gson.toJson(msisdnArray));
		items.put("message", config.getMessage());
		items.put("username", config.getUsername());
		items.put("password", config.getPassword());

		gson.toJson(items, System.out);
		return null ;
	}

	public static String sendByPostMethod2(String sUrl, Settings printJsonDataMitData2) throws Exception {

		URL url = new URL (sUrl);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		String jsonInputString = printJsonDataMitData2(config);

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

		return null;
	}
}
