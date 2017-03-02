package me.isming.xitek.bbs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sam on 17/3/2.
 */
public class TimeUtils {

    private final static SimpleDateFormat createDateTimeFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format;
    }

    private final static ThreadLocal<SimpleDateFormat> dateTimeFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return createDateTimeFormat();
        }
    };

    private final static SimpleDateFormat createTimeFormat() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format;
    }

    private final static ThreadLocal<SimpleDateFormat> timeFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return createTimeFormat();
        }
    };

    public static String timeFormat(long time) {
        final long date = time * 1000;
        final long twoDay = 2 * 24 * 60 * 60 * 1000;
        final long oneMinute = 60 * 1000;
        final long current = System.currentTimeMillis();
        final long differ = current - date;
        final Date commentdata = new Date(date);
        final Date currentData = new Date(current);
        final int differYear = currentData.getYear() - commentdata.getYear();

        String returnStr = "";
        if (differ <= oneMinute) {
            returnStr = "刚刚";
        } else {
            if (differYear != 0) {
                SimpleDateFormat simpleDateFormat = dateTimeFormater.get();
                returnStr = simpleDateFormat.format(commentdata);
            } else {
                Calendar ca = Calendar.getInstance();
                ca.setTime(currentData);
                int today = ca.get(Calendar.DAY_OF_YEAR);
                ca.setTime(commentdata);
                int commentDay = ca.get(Calendar.DAY_OF_YEAR);
                int differDay = today - commentDay;
                SimpleDateFormat sdf = timeFormater.get();
                if (differDay == 0) {
                    returnStr = "今天" + sdf.format(commentdata);
                } else if (differDay == 1) {
                    returnStr = "昨天" + sdf.format(commentdata);
                } else if (differDay == 2) {
                    returnStr = "前天" + sdf.format(commentdata);
                } else {
                    SimpleDateFormat simpleDateFormat = dateTimeFormater.get();
                    returnStr = simpleDateFormat.format(commentdata);
                }
            }
        }
        return returnStr;
    }

}
