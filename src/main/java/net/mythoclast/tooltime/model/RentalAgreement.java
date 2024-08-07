package net.mythoclast.tooltime.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Locale;

/**
 * Describes a Rental Agreement resulting from renting a Tool as described in the provided Checkout.
 * All values not directly/statically available in the Checkout or its contained records are computed on-demand.
 * @param checkout The Checkout that triggered this rental agreement
 */
public record RentalAgreement(
        Checkout checkout
) {
    /**
     * Returns the code of the Tool within the Checkout
     * @return The code of the Tool within the Checkout
     */
    public String getToolCode() {
        return checkout.tool().code();
    }

    /**
     * Returns the ToolType of the Tool within the Checkout
     * @return The ToolType of the Tool within the Checkout
     */
    public ToolType getToolType() {
        return checkout.tool().type();
    }

    /**
     * Returns the brand of the Tool within the Checkout
     * @return The brand of the Tool within the Checkout
     */
    public String getToolBrand() {
        return checkout.tool().brand();
    }

    /**
     * Returns the number of rental days denoted in the Checkout
     * @return The number of rental days denoted in the Checkout
     */
    public int getRentalDays() {
        return checkout.rentalDays();
    }

    /**
     * Returns the LocalDate of when the Checkout occurred
     * @return The LocalDate of when the Checkout occurred
     */
    public LocalDate getCheckoutDate() {
        return checkout.checkoutDate();
    }

    /**
     * Computes and returns the LocalDate of when the Tool is due back
     * @return The computed LocalDate of when the Tool is due back
     */
    public LocalDate getDueDate() {
        // The -1 is here because this implementation considers the checkout day the first rental day,
        // Just using plusDays directly results in the due date being too far ahead by one day.
        return checkout.checkoutDate().plusDays(checkout.rentalDays() - 1);
    }

    /**
     * Returns the daily rental cost, in cents, of the Tool within the Checkout, extracted from its ToolType
     * @return The daily rental cost, in cents, of the Tool within the Checkout.
     */
    public int getDailyRentalCents() {
        return checkout.tool().type().getCents();
    }

    /**
     * Returns the formatted daily rental cost of the Tool within the Checkout, in dollars and cents.
     * @return The formatted daily rental cost of the Tool within the Checkout, in dollars and cents.
     */
    public String getPrettyDailyRentalAmount() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(
            new BigDecimal(BigInteger.valueOf(getDailyRentalCents()), 2)
        );
    }

    /**
     * Computes and returns the number of days in the rental period for which a charge will be levied
     * @return The computed number of days in the rental period for which a charge will be levied
     */
    public int getChargeableDays() {
        final LocalDate checkoutDate = getCheckoutDate();
        final LocalDate dueDate = getDueDate();
        final ToolType toolType = getToolType();
        final EnumSet<DayOfWeek> weekends = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        final EnumSet<DayOfWeek> weekdays = EnumSet.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
        );
        return checkoutDate.datesUntil(
                dueDate.plusDays(1)
        ).filter(
            date ->  {
                // If we *don't* charge for Holidays,
                // immediately check for Holidays and return false if it is one.
                // We don't care what day of the week observed Holidays fall on.
                if (!toolType.doesChargeForHolidays()) {
                    if (
                            date.equals(Holiday.INDEPENDENCE_DAY.nextObserved(checkoutDate)) ||
                            date.equals(Holiday.LABOR_DAY.nextObserved(checkoutDate))
                    ) {
                        return false;
                    }
                }

                return (
                    toolType.doesChargeForWeekdays() && weekdays.contains(date.getDayOfWeek())
                ) || (
                    toolType.doesChargeForWeekends() && weekends.contains(date.getDayOfWeek())
                );
            }
        ).toList().size();
    }

    /**
     * Returns the discount percentage as denoted in the Checkout
     * @return The discount percentage as denoted in the Checkout
     */
    public int getDiscountPercent() {
        return checkout.discount();
    }

    /**
     * Returns the computed pre-discount rental charge, in cents
     * @return The computed pre-discount rental charge, in cents.
     */
    public int getPreDiscountCharge() {
        return getChargeableDays() * getDailyRentalCents();
    }

    /**
     * Returns the formatted pre-discount rental charge, in dollars and cents.
     * @return The formatted pre-discount rental charge, in dollars and cents.
     */
    public String getPrettyPreDiscountCharge() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(
                new BigDecimal(BigInteger.valueOf(getPreDiscountCharge()), 2)
        );
    }

    /**
     * Computes and returns the amount of the rental discount, in cents.
     * @return The computed amount of the rental discount, in cents.
     */
    public int getDiscountAmount() {
        return BigDecimal.valueOf(getPreDiscountCharge()).multiply(
                BigDecimal.valueOf(((double)getDiscountPercent())/100.0)
        ).round(new MathContext(34, RoundingMode.HALF_UP)).intValue();
    }

    /**
     * Returns the formatted amount of the rental discount, in dollars and cents.
     * @return The formatted amount of the rental discount, in dollars and cents.
     */
    public String getPrettyDiscountAmount() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(
                new BigDecimal(BigInteger.valueOf(getDiscountAmount()), 2)
        );
    }

    /**
     * Computes and returns the final rental charge, after discount, in cents.
     * @return The computed final rental charge, after discount, in cents.
     */
    public int getFinalCharge() {
        return getPreDiscountCharge() - getDiscountAmount();
    }

    /**
     * Returns the formatted final rental charge, in dollars and cents.
     * @return The formatted final rental charge, in dollars and cents.
     */
    public String getPrettyFinalCharge() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(
                new BigDecimal(BigInteger.valueOf(getFinalCharge()), 2)
        );
    }

    /**
     * Computes and returns a printable String report form of this RentalAgreement
     * @return The computed, printable, String report form of this RentalAgreement
     */
    public String getReport() {
        return STR.
            """
            Tool code: \{getToolCode()}
            Tool type: \{getToolType().getDescription()}
            Tool brand: \{getToolBrand()}
            Rental days: \{getRentalDays()}
            Check out date: \{getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"))}
            Due date: \{getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"))}
            Daily rental charge: \{getPrettyDailyRentalAmount()}
            Charge days: \{getChargeableDays()}
            Pre-discount charge: \{getPrettyPreDiscountCharge()}
            Discount percent: \{getDiscountPercent()}%
            Discount amount: \{getPrettyDiscountAmount()}
            Final charge: \{getPrettyFinalCharge()}
            """
            .stripIndent();
    }
}
