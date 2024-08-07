package net.mythoclast.tooltime.model;

import java.time.LocalDate;

/**
 * Describes data required to perform a checkout.
 *
 * @param tool The Tool being checked out for rental
 * @param rentalDays How many days to rent the tool for, must be at least 1.
 * @param discount Discount percentage applied to the overall rental cost (integer values 0-100)
 * @param checkoutDate Date when this checkout occurred
 */
public record Checkout(
        Tool tool,
        int rentalDays,
        int discount,
        LocalDate checkoutDate
) {
    public Checkout {
        if (null == tool) {
            throw new IllegalArgumentException("A checkout may not include a null Tool.");
        }
        if (null == checkoutDate) {
            throw new IllegalArgumentException("A checkout may not include a null checkout date.");
        }
        if (rentalDays < 1) {
            throw new IllegalArgumentException("A tool must be rented for at least one day.");
        }
        if (discount < 0) {
            throw new IllegalArgumentException("A negative rental discount is not allowed.");
        }
        if (discount > 100) {
            throw new IllegalArgumentException("A discount greater than 100% is not allowed.");
        }
    }
}
