package com.myapp.pushtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String endDataStr="2017-12-14 23:59:59";
    private String startDataStr="";
    private Date startDate,endDate;
    private long startMilliSeconds,endMilliSeconds;
    private int day,hours,minutes,seconds,sumSeconds;
    private TextView startDate_main,endDate_mian,count_down_day,count_down_minutes,count_down_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Calendar calendar=Calendar.getInstance();
        startDataStr=sdf.format(calendar.getTime());
        startDate_main.setText(startDataStr);
        endDate_mian.setText(endDataStr);
        try {
            startDate=sdf.parse(startDataStr);
            endDate=sdf.parse(endDataStr);

            startMilliSeconds=startDate.getTime();
            endMilliSeconds=endDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        sumSeconds= (int) ((endMilliSeconds-startMilliSeconds)/1000);
        handle.sendEmptyMessage(1);
    }

    private void initView() {
        startDate_main= (TextView) findViewById(R.id.startDate);
        endDate_mian= (TextView) findViewById(R.id.endDate);
        count_down_day= (TextView) findViewById(R.id.count_down_day);
        count_down_minutes= (TextView) findViewById(R.id.count_down_minutes);
        count_down_second= (TextView) findViewById(R.id.count_down_second);
    }
    private Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sumSeconds--;
            if (sumSeconds > 0) {
                setCountDownData(sumSeconds);
                handle.sendEmptyMessageDelayed(1, 1000);
            } else {
                count_down_day.setText("00");
                count_down_minutes.setText("00");
                count_down_second.setText("00");
            }
        }
    };
    private void setCountDownData(int sumSeconds){

        //得到天数
        day=sumSeconds/3600/24;
        int remain_day=sumSeconds%(3600*24);

        //得到小时
        hours=remain_day/3600;
        int remain_hour=remain_day%3600;

        //得到分钟
        minutes=remain_hour/60;

        seconds=remain_hour%60;
        if (day < 10) {
            count_down_day.setText("0" + String.valueOf(day));
        } else {
            count_down_day.setText(String.valueOf(day));
        }
        if (minutes < 10) {
            count_down_minutes.setText("0" + String.valueOf(minutes));
        } else {
            count_down_minutes.setText(String.valueOf(minutes));
        }
        if (seconds < 10) {
            count_down_second.setText("0" + String.valueOf(seconds));
        } else {
            count_down_second.setText(String.valueOf(seconds));
        }

    }
}
