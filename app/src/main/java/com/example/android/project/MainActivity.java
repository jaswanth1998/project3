package com.example.android.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView display;
    private int dayCount;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref =getApplicationContext().getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE) ;
        dayCount = pref.getInt("dayCount", 0);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,6);
        cal.set(Calendar.SECOND,00);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);
        if ((hour==8 && minute==1 ))
            updateQuote();
        TextView text4 = (TextView) findViewById(R.id.text4);




        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        TextView date = (TextView) findViewById(R.id.dates);
        date.setText(s);
        final String[] wc = {"The", "Qucik", "Brown", "fox", "Jumped"};
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {


            int i = 0;

            @Override
            public void run() {
                display = (TextView) findViewById(R.id.proverb);
                display.setText(wc[i]);
                i++;
                if (i == wc.length) {
                    handler.removeCallbacks(this);
                } else {
                    //5 sec
                    handler.postDelayed(this, 1000 * 5);
                }
            }
        });

    }

    private void updateQuote() {
        if (dayCount==4)
            dayCount=0;
        dayCount++;
        pref.edit().putInt("dayCount",dayCount).apply();
    }


    public void change(View view) {
        Calendar calender = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(),Notification_reciver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}

