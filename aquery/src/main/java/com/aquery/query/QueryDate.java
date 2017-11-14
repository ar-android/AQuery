package com.aquery.query;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ocittwo on 11/13/17.
 */

public class QueryDate {

    private final Calendar calendar;
    private long timeMillis;

    private DateFormat defaultFormat;

    public QueryDate(long timeMillis) {
        this.timeMillis = timeMillis;
        calendar = Calendar.getInstance();
        setTimeMillis(timeMillis);
        setDefaultFormat("dd MMM yyyy hh:mm:ss zzz");
    }

    public QueryDate setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        return this;
    }

    public void setDate(Date date){
        this.timeMillis = date.getTime();
        setTimeMillis(timeMillis);
    }

    @SuppressLint("SimpleDateFormat")
    public void setDefaultFormat(String pattern) {
        this.defaultFormat = new SimpleDateFormat(pattern);
    }

    public Date getDate(){
        return calendar.getTime();
    }

    public String defaultFormat() {
        return defaultFormat.format(getDate());
    }

    public String format(String pattern) {
        setDefaultFormat(pattern);
        return defaultFormat.format(getDate());
    }

    @Override
    public String toString() {
        return defaultFormat();
    }

    public String dayName() {
        return format("EEEE");
    }

    public String monthName() {
        return format("MMMM");
    }

    public String timeZone() {
        return format("zzz");
    }

    public String time() {
        return format("hh:mm:ss");
    }

    public String hour() {
        return format("hh");
    }

    public String getAmPm() {
        return format("a");
    }

}
