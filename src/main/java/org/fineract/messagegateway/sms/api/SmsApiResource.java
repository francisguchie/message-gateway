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

import org.apache.http.impl.cookie.IgnoreSpecProvider;

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
    public ResponseEntity<Void> sendShortMessages(@RequestHeader(MessageGatewayConstants.TENANT_IDENTIFIER_HEADER)
                                                      final String tenantId,
    		@RequestHeader(MessageGatewayConstants.TENANT_APPKEY_HEADER) final String appKey, 
    		@RequestBody final List<SMSMessage> payload) {

    	WirepickSMS sms = new WirepickSMS() ;
         for(SMSMessage sMessage : payload) {

             String strMsisdn = sMessage.getMobileNumber();
             String[] msisdnArray = new String[] {strMsisdn};
             System.out.println(msisdnArray[0]); //prints "name"

             TrueAfricanConfig config = new TrueAfricanConfig(msisdnArray,sMessage.getMessage(),
                    ConstantValues.SMS_CLIENT_USER_NAME, ConstantValues.SMS_CLIENT_PASSWORD) ;
            try {

                //MsgStatus msgStatus =  sms.SendPOSTSMS(config) ;

                sms.SendPOSTSMS2(config);

                //System.out.println(" Printing the code from server ");
                //System.out.println(msgStatus.getCode());

                //this refers to MsgStatus with defined response sends a null pointer error during run time
                // MsgStatus msgStatus =  sms.SendGETSMS(config) ;

                /** this method works fine but we need to be sending POST requests with json Body */
                //sms.SendGETSMS2(config); // simple but works like a cham

            } catch (NullPointerException e) {
                //System.out.println(e);
                System.out.println(" This is a null pointer exception \n " +
                        "This might mean that no responce SMS provider \n "
                        + e.toString());
                //e.printStackTrace();

            }catch (Exception e) {
                System.out.println(e.toString());
              System.out.println("See that User Id, Password, Message and sender are provided. Also make sure the Phone number includes the international code without the plus sign");
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
