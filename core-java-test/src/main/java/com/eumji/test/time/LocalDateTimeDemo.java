package com.eumji.test.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 *
 * 时间API的一些测试
 *
 * 主要针对Java8
 *
 * 另外新的DateFormator
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-19
 * @time: 下午7:24
 */
public class LocalDateTimeDemo {


    public static void compare(){
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        System.out.println(date1.equals(date1)); //true
        System.out.println(date1.isBefore(date2)); //false
        System.out.println(date1.isAfter(date2)); //false

        System.out.println(date1.compareTo(date2));

    }

    public static  void zone(){
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime berlinDateTime = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Berlin"));
        LocalDate localDate = berlinDateTime.toLocalDate();
        LocalDateTime localDateTime = berlinDateTime.toLocalDateTime();
        LocalTime localTime = berlinDateTime.toLocalTime();

    }

    /**
     * timeStamp的转换方法 只能和LocalDateTime，Instant相互转换
     */
    public static void timestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        Timestamp valueTime = Timestamp.valueOf(localDateTime);

        Instant instant = timestamp.toInstant();
        Timestamp instantTime = Timestamp.from(instant);
    }

    /**
     * 求时间差的方法测试
     * 主要是通过until方法
     * 也可以用ChronoUnit.between方法
     */
    public static void chronoUnitTest(){
        final LocalDate before = LocalDate.of(2018, 2, 10);
        final LocalDate now = LocalDate.now();
        final LocalTime localTime = LocalTime.now();
        final LocalDateTime localDateTime = LocalDateTime.now();

        final long month = localDateTime.until(localTime, ChronoUnit.MONTHS);
        final long hours = localDateTime.until(now, ChronoUnit.HOURS);

        final long between = ChronoUnit.DAYS.between(before, now);
        System.out.println(between);
        System.out.println(ChronoUnit.MONTHS.between(before,now));
        System.out.println(ChronoUnit.WEEKS.between(before,now));

    }

    /**
     * 格式化时间的两种方式和对比
     * 当时间越界的时候 SimpleDateFormat依然 可以格式 自动给你增长
     * 但是DateTimeFormatter会抛异常 Text '2013-14-12' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 14
     */
    public static void formatTime1() throws ParseException {
        String time1 = "2013-14-12";
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        final Date parse = sf.parse(time1);
        System.out.println(parse);

        final LocalDate parse1 = LocalDate.parse(time1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(parse1);
    }

    /**
     * SimpleDateFormat对于 2013-1-1 这样的格式依然可以用yyyy-MM-dd
     * DateTimeFormatter不可以 Text '2013-1-1' could not be parsed at index 5
     *
     *SimpleDateFormat和DateTimeFormatter都可以yyyy-M-d格式兼容 2013-01-01这样的时间格式
     * 说明yyyy-M-d才是真爱
     * @throws ParseException
     */
    public static void formatTime2() throws ParseException {
        String time1 = "2013-1-1";
        String time2 = "2013-01-01";
        //final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-M-d");
        final Date parse = sf.parse(time2);
        System.out.println(parse);

        //final LocalDate parse1 = LocalDate.parse(time1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalDate parse1 = LocalDate.parse(time2, DateTimeFormatter.ofPattern("yyyy-M-d"));
        System.out.println(parse1);

    }

    public static void main(String[] args) throws ParseException {
        formatTime2();
    }

}
