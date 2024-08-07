package net.mythoclast.tooltime;

import net.mythoclast.tooltime.model.Checkout;
import net.mythoclast.tooltime.model.RentalAgreement;
import net.mythoclast.tooltime.model.ToolType;
import net.mythoclast.tooltime.model.Tools;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ToolRentalTest {

    /**
     * Tests specifically for an invalid discount, validates that such attempts trigger an exception.
     * <br>
     * Tool code: JAKR
     * Tool type: Jackhammer
     * Tool brand: Rigid
     * Rental days: 5
     * Check out date: 09/03/15
     * Due date: 09/07/15
     * Daily rental charge: $2.99
     * Charge days: 2
     * Pre-discount charge: $5.98
     * Discount percent: 101%
     * Discount amount: *THROWS*
     * Final charge: *THROWS*
     */
    @Test
    public void testInvalidDiscount() {
        final IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () ->  new Checkout(
                Tools.JAKR.getTool(),
                5,
                101,
                LocalDate.of(2015, Month.SEPTEMBER, 3)
            )
        );
        assertEquals("A discount greater than 100% is not allowed.", e.getMessage());
    }

    /**
     * Tool code: LADW
     * Tool type: Ladder
     * Tool brand: Werner
     * Rental days: 3
     * Check out date: 07/02/20
     * Due date: 07/04/20
     * Daily rental charge: $1.49
     * Charge days: 2 (Independence Day observed, would otherwise be 3)
     * Pre-discount charge: $2.98
     * Discount percent: 10%
     * Discount amount: $0.29
     * Final charge: $2.69
     */
    @Test
    public void testLADWFor3DaysWith10PercentDiscount() {
        final RentalAgreement agreement = new RentalAgreement(
            new Checkout(
                Tools.LADW.getTool(),
                3,
                10,
                LocalDate.of(2020, Month.JULY, 2)
            )
        );
        assertEquals("LADW", agreement.getToolCode());
        assertEquals(ToolType.LADDER, agreement.getToolType());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(149, agreement.getDailyRentalCents());
        assertEquals(LocalDate.of(2020, Month.JULY, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, Month.JULY, 4), agreement.getDueDate());
        assertEquals(2, agreement.getChargeableDays());
        assertEquals(298, agreement.getPreDiscountCharge());
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(29, agreement.getDiscountAmount());
        assertEquals(269, agreement.getFinalCharge());
    }

    /**
     * Tool code: CHNS
     * Tool type: Chainsaw
     * Tool brand: Stihl
     * Rental days: 5
     * Check out date: 07/02/15
     * Due date: 07/06/15
     * Daily rental charge: $1.49
     * Charge days: 3
     * (No Holiday exception for Chainsaws, so the weekend accounts for the two un-billed days)
     * Pre-discount charge: $4.47
     * Discount percent: 25%
     * Discount amount: $1.11
     * Final charge: $3.36
     */
    @Test
    public void testCHNSFor5DaysWith25PercentDiscount() {
        final RentalAgreement agreement = new RentalAgreement(
            new Checkout(
                Tools.CHNS.getTool(),
                5,
                25,
                LocalDate.of(2015, Month.JULY, 2)
            )
        );
        assertEquals("CHNS", agreement.getToolCode());
        assertEquals(ToolType.CHAINSAW, agreement.getToolType());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(149, agreement.getDailyRentalCents());
        assertEquals(LocalDate.of(2015, Month.JULY, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.JULY, 6), agreement.getDueDate());
        assertEquals(3, agreement.getChargeableDays());
        assertEquals(447, agreement.getPreDiscountCharge());
        assertEquals(25, agreement.getDiscountPercent());
        assertEquals(111, agreement.getDiscountAmount());
        assertEquals(336, agreement.getFinalCharge());
    }

    /**
     * Tool code: JAKD
     * Tool type: Jackhammer
     * Tool brand: DeWalt
     * Rental days: 6
     * Check out date: 09/03/15
     * Due date: 09/08/15
     * Daily rental charge: $2.99
     * Charge days: 3 (Labor Day observed, would otherwise be 4. No weekend charge, to account for the other two days.)
     * Pre-discount charge: $8.97
     * Discount percent: 0%
     * Discount amount: $0.00
     * Final charge: $8.97
     */
    @Test
    public void testJAKDOverLaborDayWeekendWithNoDiscount() {
        final RentalAgreement agreement = new RentalAgreement(
            new Checkout(
                Tools.JAKD.getTool(),
                6,
                0,
                LocalDate.of(2015, Month.SEPTEMBER, 3)
            )
        );
        assertEquals("JAKD", agreement.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement.getToolType());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(299, agreement.getDailyRentalCents());
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 3), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 8), agreement.getDueDate());
        assertEquals(3, agreement.getChargeableDays());
        assertEquals(897, agreement.getPreDiscountCharge());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0, agreement.getDiscountAmount());
        assertEquals(897, agreement.getFinalCharge());
    }

    /**
     * Tool code: JAKR
     * Tool type: Jackhammer
     * Tool brand: Rigid
     * Rental days: 9
     * Check out date: 07/02/15
     * Due date: 07/10/15
     * Daily rental charge: $2.99
     * Charge days: 6
     * (3 days are un-billed because jackhammers are not billed on weekends,
     * AND Independence Day is *observed* on a weekday. As such, only the 6 'normal' weekdays are billed.)
     * Pre-discount charge: $17.94
     * Discount percent: 0%
     * Discount amount: $0.00
     * Final charge: $17.94
     */
    @Test
    public void testJAKRFor9DaysWithNoDiscount() {
        final RentalAgreement agreement = new RentalAgreement(
            new Checkout(
                Tools.JAKR.getTool(),
                9,
                0,
                LocalDate.of(2015, Month.JULY, 2)
            )
        );
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement.getToolType());
        assertEquals("Rigid", agreement.getToolBrand());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(299, agreement.getDailyRentalCents());
        assertEquals(LocalDate.of(2015, Month.JULY, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, Month.JULY, 10), agreement.getDueDate());
        assertEquals(6, agreement.getChargeableDays());
        assertEquals(1794, agreement.getPreDiscountCharge());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0, agreement.getDiscountAmount());
        assertEquals(1794, agreement.getFinalCharge());
    }

    /**
     * Tool code: JAKR
     * Tool type: Jackhammer
     * Tool brand: Rigid
     * Rental days: 4
     * Check out date: 07/02/20
     * Due date: 07/05/20
     * Daily rental charge: $2.99
     * Charge days: 1
     * Pre-discount charge: $2.99
     * Discount percent: 50%
     * Discount amount: $1.49
     * Final charge: $1.50
     */
    @Test
    public void testJAKRFor5DaysWith50PercentDiscount() {
        final RentalAgreement agreement = new RentalAgreement(
            new Checkout(
                Tools.JAKR.getTool(),
                4,
                50,
                LocalDate.of(2020, Month.JULY, 2)
            )
        );
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement.getToolType());
        assertEquals("Rigid", agreement.getToolBrand());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(299, agreement.getDailyRentalCents());
        assertEquals(LocalDate.of(2020, Month.JULY, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, Month.JULY, 5), agreement.getDueDate());
        assertEquals(1, agreement.getChargeableDays());
        assertEquals(299, agreement.getPreDiscountCharge());
        assertEquals(50, agreement.getDiscountPercent());
        assertEquals(149, agreement.getDiscountAmount());
        assertEquals(150, agreement.getFinalCharge());
    }

}
