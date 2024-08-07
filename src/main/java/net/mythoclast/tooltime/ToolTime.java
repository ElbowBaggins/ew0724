package net.mythoclast.tooltime;

import net.mythoclast.tooltime.model.*;

import java.time.LocalDate;
import java.time.Month;

/**
 * This class simply creates and prints the "report form" of each scenario tested in the JUnits.
 * Pay no mind.
 */
public final class ToolTime {
    public static void main() {
        // This commented-out call always throws
        /*
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.JAKR.getTool(),
                5,
                101,
                LocalDate.of(2015, Month.SEPTEMBER, 3)
            )
        ).getReport());
         */
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.LADW.getTool(),
                    3,
                    10,
                    LocalDate.of(2020, Month.JULY, 2)
            )
        ).getReport());
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.CHNS.getTool(),
                5,
                25,
                LocalDate.of(2015, Month.JULY, 2)
            )
        ).getReport());
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.JAKD.getTool(),
                    6,
                    0,
                    LocalDate.of(2015, Month.SEPTEMBER, 3)
            )
        ).getReport());
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.JAKR.getTool(),
                    9,
                    0,
                    LocalDate.of(2015, Month.JULY, 2)
            )
        ).getReport());
        System.out.println(new RentalAgreement(
            new Checkout(
                Tools.JAKR.getTool(),
                    4,
                    50,
                    LocalDate.of(2020, Month.JULY, 2)
            )
        ).getReport());
    }
}