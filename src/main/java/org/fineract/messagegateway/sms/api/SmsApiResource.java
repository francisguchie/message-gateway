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
package org.fineract.messagegateway.sms.api;

import java.util.Collection;
import java.util.List;

import org.fineract.messagegateway.constants.MessageGatewayConstants;
import org.fineract.messagegateway.sms.data.DeliveryStatusData;
import org.fineract.messagegateway.sms.domain.SMSMessage;
import org.fineract.messagegateway.sms.service.SMSMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.fineract.messagegateway.sms.providers.impl.wirepick.Constants.*;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.*;
import org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model.*;

import org.fineract.messagegateway.sms.providers.impl.trueafrican.Constants.*;
import org.fineract.messagegateway.sms.providers.impl.trueafrican.smsgateway.*;
import org.fineract.messagegateway.sms.providers.impl.trueafrican.smsgateway.model.*;



@RestController
@RequestMapping("/sms")
public class SmsApiResource {

	//This class sends TRANSACTIONAL & PROMOTIONAL SMS
	private SMSMessageService smsMessageService ;
	
	@Autowired
    public SmsApiResource(final SMSMessageService smsMessageService) {
		this.smsMessageService = smsMessageService ;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Void> sendShortMessages(@RequestHeader(MessageGatewayConstants.TENANT_IDENTIFIER_HEADER) final String tenantId,
    		@RequestHeader(MessageGatewayConstants.TENANT_APPKEY_HEADER) final String appKey, 
    		@RequestBody final List<SMSMessage> payload) {
        
  /*  	WirepickSMS sms = new WirepickSMS() ;
         for(SMSMessage sMessage : payload) {
            WpkClientConfig config = new WpkClientConfig(ConstantValues.SMS_CLIENT_ID, ConstantValues.SMS_CLIENT_PWD, 
                    ConstantValues.SMS_CLIENT_AFFILIATE, sMessage.getMobileNumber() ,sMessage.getMessage(), ConstantValues.SMS_CLIENT_SENDER_ID, ConstantValues.SMS_TAG) ; 
            try {
                MsgStatus msgStatus =  sms.SendPOSTSMS(config) ;
                System.out.println(msgStatus.getMessageId());

            } catch (Exception e) {
              System.out.println("Message not sent please make sure your ClientID,TAG and Password are correct. Alse make sure the Phone number includes the international code without the plus sign");  

            }
        } */

        trueAfricanSMS sms = new trueAfricanSMS() ;
         for(SMSMessage sMessage : payload) {
            taClientConfig config = new taClientConfig(sMessage.getMobileNumber() ,sMessage.getMessage(), 
                     taConstantValues.SMS_CLIENT_USER_NAME, taConstantValues.SMS_CLIENT_PWD) ; 
            try {
                taMsgStatus msgStatus =  sms.SendPOSTSMS(config) ;
                System.out.println(msgStatus.getMsisdn());

            } catch (Exception e) {
                
                taMsgStatus msgStatus =  sms.SendPOSTSMS(config) ;
                System.out.println(msgStatus.getMsisdn());

            } catch (Exception e2) {
                
              System.out.println("Check UserName, Password, Msisdn are correct and the Phone number includes the international code without the plus sign");  

            }
        } 
       return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(value = "/report", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Collection<DeliveryStatusData>> getDeliveryStatus(@RequestHeader(MessageGatewayConstants.TENANT_IDENTIFIER_HEADER) final String tenantId,
    		@RequestHeader(MessageGatewayConstants.TENANT_APPKEY_HEADER) final String appKey, 
    		@RequestBody final Collection<Long> internalIds) {
    	Collection<DeliveryStatusData> deliveryStatus = this.smsMessageService.getDeliveryStatus(tenantId, appKey, internalIds) ;
    	return new ResponseEntity<>(deliveryStatus, HttpStatus.OK);
    }
}
