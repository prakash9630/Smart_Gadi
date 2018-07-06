package com.automarks.multitech.smart_gadi.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.app.NotificationCompat;

import com.automarks.multitech.smart_gadi.MainActivity;
import com.automarks.multitech.smart_gadi.R;

import java.lang.invoke.ConstantCallSite;

public class MyNotificationManager {
    private Context mCtx;
    private static  MyNotificationManager mInstance;



    private MyNotificationManager(Context context)
    {

        mCtx=context;

    }
    public static synchronized MyNotificationManager getmInstance(Context context)
    {
        if (mInstance==null)
        {

            mInstance=new MyNotificationManager(context);

        }
        return mInstance;
    }
    public void displayNofitication(String title,String body)
    {
        NotificationCompat.Builder mBulber=new NotificationCompat.Builder(mCtx,Constancs.CHANNEL_ID)
 .setSmallIcon(R.drawable.ic_launcher_foreground)
    .setContentTitle(title)
    .setContentText(body);

        Intent intent=new Intent(mCtx, MainActivity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(mCtx,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

  mBulber.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager=(NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager!=null)
        {
            mNotificationManager.notify(1,mBulber.build());

        }

    }
}
