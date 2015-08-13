package com.example.anoop.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Anoop on 15-08-12.
 */
public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemprature;
    private double mHumiditiy;
    private double mPercipChance;
    private String mSummary;
    private String mTimezone;

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }


    public int getIconId(){

        int iconId = R.drawable.clear_day;
        if(mIcon.equals("clear-day")){
            iconId = R.drawable.clear_day;

        }
        else if(mIcon.equals("clear-night")){
            iconId = R.drawable.clear_night;
        }
        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public String getmTimezone() {
        return mTimezone;
    }

    public void setmTimezone(String mTimezone) {
        this.mTimezone = mTimezone;
    }

    public long getmTime() {
        return mTime;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getmTimezone()));
        Date dateTime = new Date(getmTime() * 1000);
        String timeString = formatter.format(dateTime);
        return timeString;


    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmTemprature() {
        return (int)Math.round(mTemprature);
    }

    public void setmTemprature(double mTemprature) {
        this.mTemprature = mTemprature;
    }

    public double getmHumiditiy() {
        return mHumiditiy;
    }

    public void setmHumiditiy(double mHumiditiy) {
        this.mHumiditiy = mHumiditiy;
    }

    public int getmPercipChance() {
        return (int)mPercipChance * 100;
    }

    public void setmPercipChance(double mPercipChance) {
        this.mPercipChance = mPercipChance;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }
}
