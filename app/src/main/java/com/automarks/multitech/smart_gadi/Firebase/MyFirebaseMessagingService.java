package com.automarks.multitech.smart_gadi.Firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();


        MyNotificationManager.getmInstance(getApplicationContext()).displayNofitication(title,body);
    }

}
