package com.example.SurveyApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class OtpReceiver extends BroadcastReceiver {

    private static EditText editText;
    private String sms;

    public void setEditText(EditText eText) {
        OtpReceiver.editText = eText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage message:messages){
            sms=message.getMessageBody();
        }
        editText.setText(sms);
    }
}
