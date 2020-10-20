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

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;

public class AppHiveSingleSMS {

    private String From;
    private String To;
    private String Content ;
    private String Reference;
    private String CallbackUrl;
    

    public AppHiveSingleSMS() {
    }

    public AppHiveSingleSMS(String From,String To,String Content, String Reference,String CallbackUrl) {

        this.From = From;
        this.To = To;
        this.Content = Content;
        this.Reference = Reference;
        this.CallbackUrl = CallbackUrl;

    }

    public String getFrom() {
        return From;
    }
    public void setFrom(String From) {
        this.From = From;
    }

    public String getTo() {
        return To;
    }
    public void setTo(String To) {
        this.To = To;
    }

    public String getContent() {
        return Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getReference() {
        return Reference;
    }
    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public String getCallbackUrl() {
        return CallbackUrl;
    }
    public void setCallbackUrl(String CallbackUrl) {
        this.CallbackUrl = CallbackUrl;
    }

}