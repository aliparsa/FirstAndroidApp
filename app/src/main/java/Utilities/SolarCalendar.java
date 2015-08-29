package Utilities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ashkan on 8/18/2014.
 */
public class SolarCalendar {

    public String strWeekDay = "";
    public String strMonth = "";


    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    int day;
    int month;
    int year;

    int hour;
    int minute;
    int second;

    public SolarCalendar()
    {
        Date MiladiDate = new Date();
        calcSolarCalendar(MiladiDate);
    }

    public SolarCalendar(Date MiladiDate)
    {
        calcSolarCalendar(MiladiDate);
    }

    public SolarCalendar(int year, int month, int day, int hour, int minute, int second){

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;

        setMonthName(month);
    }

    private void calcSolarCalendar(Date MiladiDate) {

        int ld;

        int miladiYear = MiladiDate.getYear() + 1900;
        int miladiMonth = MiladiDate.getMonth() + 1;
        int miladiDate = MiladiDate.getDate();
        int WeekDay = MiladiDate.getDay();

        Calendar c = Calendar.getInstance();

        this.hour = c.get(Calendar.HOUR);
        this.minute = c.get(Calendar.MINUTE);
        this.second = c.get(Calendar.SECOND);

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((miladiYear % 4) != 0) {
            day = buf1[miladiMonth - 1] + miladiDate;

            if (day > 79) {
                day = day - 79;
                if (day <= 186) {
                    switch (day % 31) {
                        case 0:
                            month = day / 31;
                            day = 31;
                            break;
                        default:
                            month = (day / 31) + 1;
                            day = (day % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    day = day - 186;

                    switch (day % 30) {
                        case 0:
                            month = (day / 30) + 6;
                            day = 30;
                            break;
                        default:
                            month = (day / 30) + 7;
                            day = (day % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                day = day + ld;

                switch (day % 30) {
                    case 0:
                        month = (day / 30) + 9;
                        day = 30;
                        break;
                    default:
                        month = (day / 30) + 10;
                        day = (day % 30);
                        break;
                }
                year = miladiYear - 622;
            }
        } else {
            day = buf2[miladiMonth - 1] + miladiDate;

            if (miladiYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (day > ld) {
                day = day - ld;

                if (day <= 186) {
                    switch (day % 31) {
                        case 0:
                            month = (day / 31);
                            day = 31;
                            break;
                        default:
                            month = (day / 31) + 1;
                            day = (day % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    day = day - 186;

                    switch (day % 30) {
                        case 0:
                            month = (day / 30) + 6;
                            day = 30;
                            break;
                        default:
                            month = (day / 30) + 7;
                            day = (day % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            }

            else {
                day = day + 10;

                switch (day % 30) {
                    case 0:
                        month = (day / 30) + 9;
                        day = 30;
                        break;
                    default:
                        month = (day / 30) + 10;
                        day = (day % 30);
                        break;
                }
                year = miladiYear - 622;
            }

        }

        setMonthName(month);

        setWeekName(WeekDay);
    }

    private void setMonthName(int month){
        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }
    }

    private void setWeekName(int WeekDay){
        switch (WeekDay) {

            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }

    }


    public String getFullDate() {
        return getYear() + "/" + getMonth() + "/" + getDay();
    }
}