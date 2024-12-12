package net.webius.myassets.util;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {
    public static Long getCurrentMonth(LocalDate beginAt, LocalDate endAt) {
        return getCurrentMonth(beginAt, endAt, LocalDate.now());
    }

    public static Long getCurrentMonth(LocalDate beginAt, LocalDate endAt, LocalDate targetAt) {
        var beginPeriod = Period.between(beginAt, targetAt);
        var endPeriod = Period.between(beginAt, endAt);

        var min = 0;
        var max = getMonthRoundedUp(endPeriod);
        var months = getMonthRoundedUp(beginPeriod);

        return Math.max(min, Math.min(max, months));
    }

    public static Long getMonthRoundedUp(LocalDate dateA, LocalDate dateB) {
        return getMonthRoundedUp(Period.between(dateA, dateB));
    }

    public static Long getMonthRoundedUp(Period period) {
        var months = period.toTotalMonths();

        if (period.getDays() != 0) {
            months += (period.getDays() > 0 ? 1 : -1);
        }

        return months;
    }
}
