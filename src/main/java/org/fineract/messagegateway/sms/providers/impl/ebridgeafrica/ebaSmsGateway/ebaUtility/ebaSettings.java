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

package org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.UUID;

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

import org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaModel.ebaMsgStatus;
import org.fineract.messagegateway.sms.providers.impl.ebridgeafrica.ebaSmsGateway.ebaModel.ebaClientConfig;

public class Settings {
	
	
	private static final String API = "API";
	private static final String SMS = "sms";
	private static final String STATUS2 = "status";
	private static final String PHONE = "phone";
	private static final String MSGID = "msgid";
	private static final String UTF_8 = "UTF-8";
	public static final String HOST = "https://ebridgeafrica.com/api" ;
	private static final String DEFAULT_AFFILIATE = "999" ; 
	
	public static ebaMsgStatus parseEbridgeAfricaResultXML(InputStream stream) throws Exception, IOException  {
        DocumentBuilder objDocumentBuilder = DocBuilder();
        
        Document doc = objDocumentBuilder.parse(stream);
        
        return DocumentProcessor(doc);
    }

	private static DocumentBuilder DocBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        
        objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
		return objDocumentBuilder;
	}
	
	public static ebaMsgStatus parseEbridgeAfricaResultXML(StringReader stream) throws Exception, IOException  {
        DocumentBuilder objDocumentBuilder = DocBuilder();
        
        Document doc = objDocumentBuilder.parse(new InputSource(stream));
        return DocumentProcessor(doc);
    }

	private static ebaMsgStatus DocumentProcessor(Document doc) {
		NodeList nList = doc.getElementsByTagName(SMS);

        ebaMsgStatus status = new ebaMsgStatus();
        
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                if (eElement.getElementsByTagName(MSGID) != null) {
                    status.setMsgid(eElement.getElementsByTagName(MSGID).item(0).getTextContent());
                    status.setPhone( eElement.getElementsByTagName(PHONE).item(0).getTextContent());
                    status.setStatus(eElement.getElementsByTagName(STATUS2).item(0).getTextContent());
                } else {
                    
                	return null ;
                }
                
                break ; 
            }
        }
		return status;
	}
	
	public static String HTTPparameters(ebaClientConfig config) throws UnsupportedEncodingException
	{
		StringBuffer buffer = new  StringBuffer(HOST) ; 
		
		ValidateParams(config);
		// ?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB "
		buffer.append("?userid=").append(URLEncoder.encode(config.getUserId(), UTF_8)) ;
		buffer.append("&password=").append(URLEncoder.encode(config.getPassword(), UTF_8));
		buffer.append("&message=").append(URLEncoder.encode(config.getMessage(), UTF_8));
		buffer.append("&phone=").append(URLEncoder.encode(config.getPhone(), UTF_8));
		buffer.append("&sender=").append(URLEncoder.encode(config.getSender(), UTF_8));

		return buffer.toString() ;
		// Guchie added this
		System.out.println(buffer.toString());
		// Guchie added this
	}

	private static void ValidateParams(ebaClientConfig config) {
		// ?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB "
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
			throw new NullPointerException("Message content is required") ;
		}
		else if(config.getPhone() == null || config.getPhone().isEmpty())
		{
			throw new NullPointerException("Phone Number is required") ;
		}
		else if(config.getSender() == null || config.getSender().isEmpty())
		{
			throw new NullPointerException("Sender Name is required") ;
		}
	}
	
	
	public static  NameValuePair[] GetParameters(ebaClientConfig config) throws UnsupportedEncodingException
	{
		ValidateParams(config);
		NameValuePair[] nameValuePairs = new NameValuePair[7] ;

		// ?userid=XXXXX&password=XXXXXXXX&message=test&phone=XXXXXXXXXXX&sender=ACTB "
		nameValuePairs[0] = new NameValuePair("userid", config.getUserId()) ;
		nameValuePairs[2] = new NameValuePair("password", config.getPassword()) ;
		nameValuePairs[4] = new NameValuePair("message",config.getMessage()) ;
		nameValuePairs[3] = new NameValuePair("phone", config.getPhone()) ; 
		nameValuePairs[5] = new NameValuePair("sender", config.getSender()) ;

		return nameValuePairs;
	}
}
