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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

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

	//{"phone":"23279194407","status":"OK",
	// "description":"Message has been sent successfully","time":"2020-02-13T16:43:58.9460874+00:00"}


	private static final String PHONE = "phone";
	private static final String STATUS2 = "status";
	private static final String DESCRIPTION = "description";
	private static final String TIME = "time";

	private static final String SMS = "sms";
	private static final String API = "API";
	private static final String UTF_8 = "UTF-8";

	/** This is for sendByPostMethod and has a cookie issue */
	public static final String HOST = "https://ebridgeafrica.com/api/v1/sendsms" ;

	/** this is sendByUrlHttpConnection and its working */
	// public static final String HOST = "https://ebridgeafrica.com/api" ;

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

                if (eElement.getElementsByTagName(PHONE) != null) {
                    status.setPhone(eElement.getElementsByTagName(PHONE).item(0).getTextContent());
                    status.setStatus(eElement.getElementsByTagName(STATUS2).item(0).getTextContent());
					status.setDescription(eElement.getElementsByTagName(DESCRIPTION).item(0).getTextContent());
					status.setTime(eElement.getElementsByTagName(TIME).item(0).getTextContent());
                } else {
                	return null ;
                }
                
                break ; 
            }
        }
		return status;
	}

	// https://ebridgeafrica.com/api?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB
	public static String HTTPparameters(WpkClientConfig config) throws UnsupportedEncodingException
	{
		StringBuffer buffer = new  StringBuffer(HOST) ; 
		
		ValidateParams(config);
		
		buffer.append("?userid=").append(URLEncoder.encode(config.getUserId(), UTF_8)) ;
		buffer.append("&password=").append(URLEncoder.encode(config.getPassword(), UTF_8));
		buffer.append("&message=").append(URLEncoder.encode(config.getMessage(), UTF_8));
		buffer.append("&phone=").append(URLEncoder.encode(config.getPhone(), UTF_8));
		buffer.append("&sender=").append(URLEncoder.encode(config.getSender(), UTF_8));

		System.out.println(buffer.toString().toString());

		return buffer.toString() ;

		
	}


	private static void ValidateParams(WpkClientConfig config) {

		if(config.getUserId() == null || config.getUserId().isEmpty())
		{
			throw new NullPointerException("User id is required") ;
		}
		else if(config.getPassword() == null || config.getPassword().isEmpty())
		{
			throw new NullPointerException("Password is required") ;
		}
		else if(config.getMessage() == null || config.getMessage().isEmpty())
		{
			throw new NullPointerException("Message is required") ;
		}
		else if(config.getPhone() == null || config.getPhone().isEmpty())
		{
			throw new NullPointerException("Phone is required") ;
		}
		else if(config.getSender() == null || config.getSender().isEmpty())
		{
			throw new NullPointerException("Sender is required") ;
		}
	}

	public static  NameValuePair[] GetParameters(WpkClientConfig config) throws UnsupportedEncodingException
	{

		printingJsonData();

		ValidateParams(config);
		NameValuePair[] nameValuePairs = new NameValuePair[5] ;
		
		nameValuePairs[0] = new NameValuePair("userid", config.getUserId()) ;
		nameValuePairs[1] = new NameValuePair("password", config.getPassword()) ;
		nameValuePairs[2] = new NameValuePair("message",config.getMessage()) ;
		nameValuePairs[3] = new NameValuePair("phone", config.getPhone()) ;
		nameValuePairs[4] = new NameValuePair("sender", config.getSender()) ;

		return nameValuePairs;
	}

	public static  printingJsonData(WpkClientConfig config) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, String> items = new HashMap<>();

		items.put("userid", config.getUserId());
		items.put("password", config.getPassword());
		items.put("message", config.getMessage());
		items.put("phone", config.getPhone());
		items.put("sender", config.getSender());

		gson.toJson(items, System.out);

	}
}
