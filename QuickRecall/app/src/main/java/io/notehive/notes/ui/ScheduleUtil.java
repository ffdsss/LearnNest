package io.notehive.notes.ui;


import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 在系统日历中添加日程
 */
public class ScheduleUtil {


    private static final String CALENDER_URL = "content://com.android.calendar/calendars";
    private static final String CALENDER_EVENT_URL = "content://com.android.calendar/events";
    private static final String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders";


    private static final String CALENDARS_NAME = "categoryInfo_app";
    private static final String CALENDARS_ACCOUNT_NAME = "categoryInfo_app";
    private static final String CALENDARS_ACCOUNT_TYPE = "categoryInfo_app";
    private static final String CALENDARS_DISPLAY_NAME = "categoryInfo_app";

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    @SuppressLint("Range")
    private static int checkCalendarAccount(Context context) {
        try (Cursor userCursor = context.getContentResolver().query(Uri.parse(CALENDER_URL), null, null, null, null)) {
            if (userCursor == null) {
                return -1;
            }
            int count = userCursor.getCount();
            if (count > 0) {
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDER_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        return result == null ? -1 : ContentUris.parseId(result);
    }

    /**
     * 添加日历事件
     */
    private static void addCalendarEvent(Context context, String title, String description, long calendarStart, long calendarEnd) {
        if (context == null) {
            return;
        }
        int calId = checkAndAddCalendarAccount(context);
        if (calId < 0) {
            return;
        }

        ContentValues event = new ContentValues();
        event.put("title", title); //日程标题
        event.put("description", description); //日程描述
        event.put("calendar_id", calId); //插入账户的id
        event.put(CalendarContract.Events.DTSTART, calendarStart);
        event.put(CalendarContract.Events.DTEND, calendarEnd);
        event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC");//这个是时区，必须有
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALENDER_EVENT_URL), event); //添加事件
        if (newEvent == null) { //添加日历事件失败直接返回
            return;
        }


        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
        values.put(CalendarContract.Reminders.MINUTES, 10);// 提前10分钟提醒
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALENDER_REMINDER_URL), values);
        if (uri == null) { //添加事件提醒失败直接返回
            return;
        }


    }


    private static Calendar getCalendarFromDateTimeString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        try {
            Date dateTime = dateFormat.parse(dateTimeString);
            if (dateTime != null) {
                calendar.setTime(dateTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    @SuppressLint("Range")
    public static void deleteSchedule(Context context, String title) {

        try (Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null)) {
            if (eventCursor == null) { //查询返回空值
                return;
            }
            if (eventCursor.getCount() > 0) {
                // 遍历所有事件，找到title跟需要查询的title一样的
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));
                        Uri updateUri = ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(updateUri, null, null);
                        if (rows == -1) { //事件更新失败
                            return;
                        }

                    }
                }
            }
        }
    }


    /**
     * 用传过来的标题判断是否已经添加日程，有就更新，没有就添加
     */
    @SuppressLint("Range")
    public static void isExistSchedule(Context context, String title, String description, String startTime, String endTime) {

        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(description)) {
            description = "";
        }

        Calendar calendarStart = getCalendarFromDateTimeString(startTime);
        long start = calendarStart.getTime().getTime();
        Calendar calendarEnd = getCalendarFromDateTimeString(endTime);
        long end = calendarEnd.getTime().getTime();


        try (Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null)) {
            if (eventCursor == null) { //查询返回空值
                return;
            }
            if (eventCursor.getCount() > 0) {
                // 遍历所有事件，找到title跟需要查询的title一样的
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {

                        //如果存在就更新日程
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));
                        Uri updateUri = ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id);

                        //int rows = context.getContentResolver().delete(updateUri, null, null);//删除日程


                        ContentValues event = new ContentValues();
                        event.put("title", title);
                        event.put("description", description);
                        event.put(CalendarContract.Events.DTSTART, start);
                        event.put(CalendarContract.Events.DTEND, end);
                        event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
                        event.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC");//这个是时区，必须有
                        int rows = context.getContentResolver().update(updateUri, event, null, null);
                        if (rows == -1) { //事件更新失败
                            return;
                        }

                    } else {
                        //没有相同标题的就添加一条日程
                        addCalendarEvent(context, title, description, start, end);
                    }
                }
            } else {
                //没有日程就添加一条日程
                addCalendarEvent(context, title, description, start, end);
            }
        }
    }


}
