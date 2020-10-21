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

package org.fineract.messagegateway.sms.providers.impl.wirepick.smsgateway.model;

public class AppHiveConfig {


// For the Get method only ----------------------------------------------------------
    //https://api.sierrahive.com/v1/messages/sms
    //?clientid=879jgbnhg
    //&clientsecret=456hdbfdsfsvnm,xcdjk8723232uijkdsjksdjsd
    //&token=3f4abf4e047946db97328abf43eec91f
    //&from=ACTB
    //&to=+23278448846
    //&reference=674843&content=Testing APTELL SMS Servers

    private String clientId ;
    private String clientsecret ;
    private String token ;
    private String From ;
    private String To ;
    private String Content;
    private String Reference;
    private String CallbackUrl ;

    public AppHiveConfig()
    {

    }

    public AppHiveConfig(String clientId, String clientsecret,String token,String from, String to,String content, String reference,String callbackUrl) {

        this.clientId = clientId;
        this.clientsecret = clientsecret;
        this.token = token;
        this.from = From;
        this.to = To;
        this.content = Content;
        this.reference = Reference;
        this.callbackUrl = CallbackUrl;

    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientsecret;
    }
    public void setClientSecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

}