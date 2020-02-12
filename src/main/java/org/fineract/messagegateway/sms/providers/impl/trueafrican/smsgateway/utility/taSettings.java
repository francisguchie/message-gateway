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

package org.fineract.messagegateway.sms.providers.impl.trueafrican.smsgateway.Utility;

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

import org.fineract.messagegateway.sms.providers.impl.trueafrican.smsgateway.model.MsgStatus;
import org.fineract.messagegateway.sms.providers.impl.trueafrican.smsgateway.model.taClientConfig ;

public class taSettings {
	
	
	//private static final String API = "API";
	private static final String SMS = "sms";
	private static final String STATUS2 = "status";
	private static final String MSISDN = "msisdn";
	// private static final String MSGID = "msgid";
	private static final String UTF_8 = "UTF-8";
	public static final String HOST = "https://mysms.trueafrican.com/v1/api/esme/send" ; 
	// private static final String DEFAULT_AFFILIATE = "999" ; 
	
	public static MsgStatus parseTrueAfricanResultXML(InputStream stream) throws Exception, IOException  {
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
	
	public static MsgStatus parseTrueAfricanResultXML(StringReader stream) throws Exception, IOException  {
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

                if (eElement.getElementsByTagName(MSISDN) != null) {
                    //status.setMsgid(eElement.getElementsByTagName(MSGID).item(0).getTextContent());
                   // status.setMsisdn( eElement.getElementsByTagName(MSISDN).item(0).getTextContent());
                    status.setStatus(eElement.getElementsByTagName(STATUS2).item(0).getTextContent());
                } else {
                    
                	return null ;
                }
                
                break ; 
            }
        }
		return status;
	}
	
	public static String HTTPparameters(taClientConfig config) throws UnsupportedEncodingException
	{
		StringBuffer buffer = new  StringBuffer(HOST) ; 
		
		ValidateParams(config);
		
		buffer.append("?msisdn=").append(URLEncoder.encode(config.getMsisdn(), UTF_8)) ;
		buffer.append("&message=").append(URLEncoder.encode(config.getMessage(), UTF_8));
		buffer.append("&username=").append(URLEncoder.encode(config.getUserName(), UTF_8));
		buffer.append("&password=").append(URLEncoder.encode(config.getPassword(), UTF_8));
		
		return buffer.toString() ; 
		
	}

	private static void ValidateParams(taClientConfig config) {
		if(config.getMsisdn() == null || config.getMsisdn().isEmpty())
		{
			throw new NullPointerException("Phone number is required") ; 
		}
		else if(config.getMessage() == null || config.getMessage().isEmpty())
		{
			throw new NullPointerException("Message can not be empty") ; 
		}
		else if(config.getUserName() == null || config.getUserName().isEmpty())
		{
			throw new NullPointerException("User Name is required to send ") ; 
		}
		else if(config.getPassword() == null || config.getPassword().isEmpty())
		{
			throw new NullPointerException("Password is required at all times") ; 
		}
		
	}
	
	
	public static  NameValuePair[] GetParameters(taClientConfig config) throws UnsupportedEncodingException
	{
		
		ValidateParams(config);
		NameValuePair[] nameValuePairs = new NameValuePair[4] ; 
		
		nameValuePairs[0] = new NameValuePair("msisdn", config.getMsisdn()) ; 
		nameValuePairs[1] = new NameValuePair("message", config.getMessage()) ; 
		nameValuePairs[2] = new NameValuePair("username",config.getUserName()) ;
		nameValuePairs[3] = new NameValuePair("password", config.getPassword()) ;
		
		return nameValuePairs;
	}
}
