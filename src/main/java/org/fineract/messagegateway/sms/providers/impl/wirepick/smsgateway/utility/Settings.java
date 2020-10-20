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
//Reply from appHive
/*
	{
    "Cost": 0.0,
    "Destination": "23279194407",
    "Id": "6ea123d1-6369-4159-9020-c2a551902b5f",
    "Ticket": "6ea123d1-6369-4159-9020-c2a551902b5f",
    "ClientReference": "json Messages",
    "Status": "pending"
	}
*/

	private static final float COST = "Cost";
	private static final String DESTINATION = "Destination";
	private static final String ID = "Id";
	private static final String TICKET = "Ticket";
	private static final String CLIENTREFERENCE = "ClientReference";
	private static final String STATUS = "Status";

	private static final String SMS = "sms";
	private static final String API = "API";
	private static final String UTF_8 = "UTF-8";

	/** This is for sendByPostMethod for appHive */
	public static final String HOST = "https://api.sierrahive.com/v1/messages/sms" ;


	/** this is sendByUrlHttpConnection and its working */
	//public static final String HOST = "https://api.sierrahive.com/v1/messages/sms" ;

	

//___________________________________________________________________________________________________________
// we are not using this part of the code 
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

// END OF we are not using this part of the code  
//___________________________________________________________________________________________________________

// For the Get method only ----------------------------------------------------------
	//https://api.sierrahive.com/v1/messages/sms
	//?clientid=879jgbnhg
	//&clientsecret=456hdbfdsfsvnm,xcdjk8723232uijkdsjksdjsd
	//&token=3f4abf4e047946db97328abf43eec91f
	//&from=ACTB
	//&to=+23278448846
	//&reference=674843
	//&content=Testing APTELL SMS Servers

	public static String HTTPparameters(AppHiveConfig config) throws UnsupportedEncodingException	{
		StringBuffer buffer = new  StringBuffer(HOST) ;
		ValidateParams(config);
		buffer.append("?clientid=").append(URLEncoder.encode(config.getClientId(), UTF_8)) ;
		buffer.append("&clientsecret=").append(URLEncoder.encode(config.getClientSecret(), UTF_8));
		buffer.append("&token=").append(URLEncoder.encode(config.getToken(), UTF_8));
		buffer.append("&from=").append(URLEncoder.encode(config.getFrom(), UTF_8));
		buffer.append("&to=").append(URLEncoder.encode(config.getTo(), UTF_8));
		buffer.append("&reference=").append(URLEncoder.encode(config.getReference(), UTF_8));
		buffer.append("&content=").append(URLEncoder.encode(config.getContent(), UTF_8));



		return buffer.toString() ;
	}

	private static void ValidateParams(AppHiveConfig config) {

		if(config.getClientId() == null || config.getClientId().isEmpty())
		{
			throw new NullPointerException("ClientId is required") ;
		}
		else if(config.getClientSecret() == null || config.getClientSecret().isEmpty())
		{
			throw new NullPointerException("Client Secret is required") ;
		}
		else if(config.getToken() == null || config.getToken().isEmpty())
		{
			throw new NullPointerException("Token is required") ;
		}
		else if(config.getFrom() == null || config.getFrom().isEmpty())
		{
			throw new NullPointerException("From which Company") ;
		}
		else if(config.getTo() == null || config.getTo().isEmpty())
		{
			throw new NullPointerException("Phone number is required") ;
		}
		else if(config.getReference() == null || config.getReference().isEmpty())
		{
			throw new NullPointerException("Reference is required") ;
		}
		else if(config.getContent() == null || config.getContent().isEmpty())
		{
			throw new NullPointerException("The Content of the message is missing") ;
		}
	}

	public static  NameValuePair[] GetParameters(AppHiveConfig config) throws UnsupportedEncodingException
	{

		ValidateParams(config);
		NameValuePair[] nameValuePairs = new NameValuePair[4] ;

		nameValuePairs[0] = new NameValuePair("clientid", config.getClientId()) ;
		nameValuePairs[1] = new NameValuePair("clientsecret",config.getClientSecret()) ;
		nameValuePairs[2] = new NameValuePair("token", config.getToken()) ;
		nameValuePairs[3] = new NameValuePair("from", config.getFrom()) ;
		nameValuePairs[4] = new NameValuePair("to", config.getTo()) ;
		nameValuePairs[5] = new NameValuePair("reference", config.getReference()) ;
		nameValuePairs[5] = new NameValuePair("content", config.getContent()) ;
		

		return nameValuePairs;
	}

	/** this is my own json formatting method / function
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
	*/

}
