package com.hugh.bmobtest;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

/**
 * Created by hugh on 2016/3/9.
 */
public class PushReceiver extends BroadcastReceiver {

    private String alert;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String s = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

            JSONTokener jsonTokener = new JSONTokener(s);
            try {
                JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                alert = jsonObject.getString("alert");
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                                                 context).setSmallIcon(R.drawable.ic_launcher)
                                         .setContentTitle("BmobTest")
                                         .setContentText(alert);
                manager.notify(0, mBuilder.build());


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
