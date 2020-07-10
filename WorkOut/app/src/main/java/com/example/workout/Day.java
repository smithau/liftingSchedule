package com.example.workout;

import java.time.MonthDay;

public enum Day
{
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    public String toString()
    {
        return "" + this;
    }

    public static Day toDay(int day) {
        switch (day) {
            case 0:
                return Day.SUNDAY;
            case 1:
                return Day.MONDAY;
            case 2:
                return Day.TUESDAY;
            case 3:
                return Day.WEDNESDAY;
            case 4:
                return Day.THURSDAY;
            case 5:
                return Day.FRIDAY;
            case 6:
                return Day.SATURDAY;
            default:
                return null;
        }
    }
}



