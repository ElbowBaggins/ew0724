package net.mythoclast.tooltime.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

/**
 * Defines Holidays and provides some helper methods
 */
public enum Holiday {
    INDEPENDENCE_DAY(LocalDate.of(2020, Month.JULY, 4)),

    // This is honestly mega jank. Since we only care about two Holidays, and Labor Day does not fall on a fixed day
    // we give it a null LocalDate. The helper methods check for this case and handle Labor Day scenarios appropriately.
    LABOR_DAY(null);

    private final LocalDate date;

    // Allows a Holiday to be initialized with a LocalDate, year disregarded
    Holiday(final LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the next observed incidence of this Holiday on/after the given LocalDate
     * Essentially, the "next observed" is either this year, if `from` falls before this year's incidence,
     * otherwise it is next year.
     * @param from The LocalDate we want to find the next observed incidence of this Holiday for.
     * @return A LocalDate containing the next observed incidence of this Holiday.
     */
    public LocalDate nextObserved(final LocalDate from) {
        return this.forYear(from.getYear() + (from.isBefore(forYear(from.getYear())) ? 0 : 1));
    }

    /**
     * Returns the LocalDate that this Holiday is observed in the given year
     * @param year The year in which we want to find when this Holiday is observed
     * @return A LocalDate containing the observed incidence of this Holiday in the given year.
     */
    public LocalDate forYear(final int year) {
        // If null == date, we're interested in Labor Day
        if (null == date) {
            // Starting from September 1 of the given year....
            return LocalDate.of(
                    year, Month.SEPTEMBER, 1
            ).with(
                    // Find when the first Monday is, and return its LocalDate.
                    TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)
            );
        }

        // Otherwise, we're interested in Independence Day
        final LocalDate actualIndependenceDay = LocalDate.of(year, Month.JULY, 4);
        // If Independence Day in the given year is a Saturday, return a LocalDate of the previous Friday
        if (actualIndependenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return actualIndependenceDay.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        // If Independence Day in the given year is a Sunday, return a LocalDate of the subsequent Monday.
        } else if (actualIndependenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return actualIndependenceDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        // Otherwise, just return a LocalDate of Independence Day
        return actualIndependenceDay;
    }
}
