package com.kk.trackit.sms;

import java.util.*;
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkedari on 7/10/15.
 */
public class MessageScanner {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public String getMessages() {
        String response= "";
        try{
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        Map<String,String> params = new HashMap<String, String>();
        params.put("From", "9377086926");
       // params.put("DateSent", "2015-07-11");
            MessageList messages = client.getAccount().getMessages(params);
            for (Message message : messages) {
                response += message.getBody() + "\n";
            }
        }catch (Exception te) {
            te.printStackTrace();
        }
        return response;
    }
}
