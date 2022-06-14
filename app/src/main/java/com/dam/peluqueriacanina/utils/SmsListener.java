package com.dam.peluqueriacanina.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public String msg = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("smsa", "Recibido");
        if (intent.getAction() == SMS_RECEIVED) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                //  Get Messages
                Object[] sms = (Object[]) bundle.get("pdus");

                final SmsMessage[] message = new SmsMessage[sms.length];

                for (int i = 0; i < sms.length; ++i) {
                    // Parse Each Message
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) sms[i], format);

                    } else {
                        message[i] = SmsMessage.createFromPdu((byte[]) sms[i]);
                    }
                    msg = message[i].getMessageBody();
                }
            }
        }
    }
}
