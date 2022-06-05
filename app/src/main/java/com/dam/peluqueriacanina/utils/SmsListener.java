package com.dam.peluqueriacanina.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsListener extends BroadcastReceiver {

    public String msg = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            /* Get Messages */
            Object[] sms = (Object[]) bundle.get("pdus");

            SmsMessage[] message = new SmsMessage[sms.length];

            for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                message[i] = SmsMessage.createFromPdu((byte[]) sms[i]);

                msg = message[i].getMessageBody();
            }

        }
    }

}