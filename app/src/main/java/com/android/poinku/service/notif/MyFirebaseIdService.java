package com.android.poinku.service.notif;

import com.android.poinku.preference.AppPreference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String userKey = AppPreference.getUser(getApplicationContext()).nrp;
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        if(userKey != null){
            updateToken(refreshToken);
        }
    }

    private void updateToken(String refreshToken) {
        String userKey = AppPreference.getUser(getApplicationContext()).nrp;
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Poinku").child("Token").child(userKey).setValue(token);
    }
}