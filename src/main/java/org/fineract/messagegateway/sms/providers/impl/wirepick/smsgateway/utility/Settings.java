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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.MsgStatus;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.WpkClientConfig;

public class Settings {
	//{"status":"OK","code":200, "message":"Message has been sent successfully"}
	private static final String STATUS = "status";
	private static final String CODE = "code";
	private static final String MESSAGE = "message";

	private static final String SMS = "sms";
	private static final String API = "API";
	private static final String UTF_8 = "UTF-8";

	/** This is for sendByPostMethod and has a cookie issue */
	public static final String HOST = "http://mysms.trueafrican.com/v1/api/esme/send" ;

	/** this is sendByUrlHttpConnection and its working */
	//public static final String HOST = "https://ebridgeafrica.com/api" ;

	//private static final String DEFAULT_AFFILIATE = "999" ;


	public static MsgStatus parseWirepickResultXML(InputStream stream) throws Exception, IOException  {
        DocumentBuilder objDocumentBuilder = DocBuilder();
        
        Document doc = objDocumentBuilder.parse(stream);
		System.out.println("the document is " + doc.toString());
        return DocumentProcessor(doc);
    }

	private static DocumentBuilder DocBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        
        objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
		return objDocumentBuilder;
	}
	
	public static MsgStatus parseWirepickResultXML(StringReader stream) throws Exception, IOException  {
        DocumentBuilder objDocumentBuilder = DocBuilder();
        
        Document doc = objDocumentBuilder.parse(new InputSource(stream));
        return DocumentProcessor(doc);
    }

	private static MsgStatus DocumentProcessor(Document doc) {
		NodeList nList = doc.getElementsByTagName(SMS);
        MsgStatus status = new MsgStatus();
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName(STATUS) != null) {
                    status.setStatus(eElement.getElementsByTagName(STATUS).item(0).getTextContent());
					//status.setCode(eElement.getElementsByTagName(CODE).item(0).getTextContent());
					status.setMessage(eElement.getElementsByTagName(MESSAGE).item(0).getTextContent());
                } else {
                	return null ;
                }
                break ;             }
        }
		return status;
	}

	// https://ebridgeafrica.com/api?userid=XXXXX&password=XXXXXXXX&message=test&msisdn=XXXXXXXXXXX&username=ACTB
	// this is for the curl / wget but True African dont have this anywhere
	public static String HTTPparameters(WpkClientConfig config) throws UnsupportedEncodingException	{
		StringBuffer buffer = new  StringBuffer(HOST) ;
		ValidateParams(config);
		buffer.append("?msisdn=").append(URLEncoder.encode(config.getMsisdn(), UTF_8)) ;
		buffer.append("&message=").append(URLEncoder.encode(config.getMessage(), UTF_8));
		buffer.append("&username=").append(URLEncoder.encode(config.getUsername(), UTF_8));
		buffer.append("&password=").append(URLEncoder.encode(config.getPassword(), UTF_8));

		return buffer.toString() ;
	}

	private static void ValidateParams(WpkClientConfig config) {

		if(config.getMsisdn() == null || config.getMsisdn().isEmpty())
		{
			throw new NullPointerException("Msisdn is required") ;
		}
		else if(config.getMessage() == null || config.getMessage().isEmpty())
		{
			throw new NullPointerException("Message is required") ;
		}
		else if(config.getUsername() == null || config.getUsername().isEmpty())
		{
			throw new NullPointerException("Username is required") ;
		}
		else if(config.getPassword() == null || config.getPassword().isEmpty())
		{
			throw new NullPointerException("Password is required") ;
		}
	}

	public static  NameValuePair[] GetParameters(WpkClientConfig config) throws UnsupportedEncodingException
	{
		// this runs well too and the data is well formatted
		System.out.println(" the json formatted is below in 2 forms ");
		printJsonDataMitData(config);
		printJsonDataMitData2(config);

		ValidateParams(config);
		NameValuePair[] nameValuePairs = new NameValuePair[4] ;

		nameValuePairs[0] = new NameValuePair("msisdn", config.getMsisdn()) ;
		nameValuePairs[1] = new NameValuePair("message",config.getMessage()) ;
		nameValuePairs[2] = new NameValuePair("username", config.getUsername()) ;
		nameValuePairs[3] = new NameValuePair("password", config.getPassword()) ;

		return nameValuePairs;
	}

	/** this is my own json formatting method / function */
	public static String printJsonDataMitData(WpkClientConfig config) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, String> items = new HashMap<>();

		items.put("msisdn", config.getMsisdn());
		items.put("message", config.getMessage());
		items.put("username", config.getUsername());
		items.put("password", config.getPassword());

		gson.toJson(items, System.out);
		return null ;
	}
	/** this is my own json formatting method / function */
	public static String printJsonDataMitData2(WpkClientConfig config) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, String> items = new HashMap<>();
		String[] msisdnArray = config.getMsisdn()

		items.put("msisdn", gson.toJson(msisdnArray));
		items.put("message", config.getMessage());
		items.put("username", config.getUsername());
		items.put("password", config.getPassword());

		gson.toJson(items, System.out);
		return null ;
	}

}
