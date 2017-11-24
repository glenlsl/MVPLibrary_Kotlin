package travelsky.fulinpolice.mvplibrary.util;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期常用工具
 * Created by Solin on 2017/3/7.
 */
public class DateTool {
    public static String oldToNewFormat(String oldDate, String oldFormat, String newFormat) {

        SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
        try {
            Date date = oldDateFormat.parse(oldDate);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
        /*LocalDateTime localDateTime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern(oldFormat));
        return localDateTime.format(DateTimeFormatter.ofPattern(newFormat));*/
    }

    public static String formatDateStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatCurDate(String format) {
        /*SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(new Date());*/
        //        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static Date getDate(String srcDate, String srcFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(srcFormat, Locale.getDefault());
        try {
            return dateFormat.parse(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeek(String srcDate, String srcFormat, String[] backStrs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(srcDate, srcFormat));
        return backStrs[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /*public static String getWeek(String srcDate, String srcFormat, String[] backStrs) {
        LocalDateTime localDateTime = LocalDateTime.parse(srcDate, DateTimeFormatter.ofPattern(srcFormat));
        return backStrs[localDateTime.getDayOfWeek().getValue()];
    }*/

    public static Date datePlus(long time, int plusTime, int field) {
        GregorianCalendar c = new GregorianCalendar(Locale.getDefault());
        c.setTimeInMillis(time);
        c.add(field, plusTime);
        return c.getTime();
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        /*Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;*/
        LocalDate localDate = LocalDate.of(year, month, 1);
        return localDate.lengthOfMonth();
    }
}
