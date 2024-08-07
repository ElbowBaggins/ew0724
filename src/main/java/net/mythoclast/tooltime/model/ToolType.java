package net.mythoclast.tooltime.model;

/**
 * Describes a type of tool
 */
public enum ToolType {
    /**
     * Specifies a ladder.
     * Costs $1.99.
     * No charge for holidays.
     */
    LADDER(
            "Ladder",
            149,
            true,
            true,
            false
    ),
    /**
     * Specifies a Chainsaw
     * Costs $1.49
     * No charge for weekends
     */
    CHAINSAW(
            "Chainsaw",
            149,
            true,
            false,
            true
    ),
    /**
     * Specifies a Jackhammer
     * Costs $2.99
     * No charge for weekends.
     * No charge for holidays.
     */
    JACKHAMMER(
            "Jackhammer",
            299,
            true,
            false,
            false
    );

    private final String description;
    private final int cents;
    private final boolean chargeForWeekdays;
    private final boolean chargeForWeekends;
    private final boolean chargeForHolidays;

    ToolType(
            final String description,
            final int cents,
            final boolean chargeForWeekdays,
            final boolean chargeForWeekends,
            final boolean chargeForHolidays
    ) {
        this.description = description;
        this.cents = cents;
        this.chargeForWeekdays = chargeForWeekdays;
        this.chargeForWeekends = chargeForWeekends;
        this.chargeForHolidays = chargeForHolidays;
    }

    /**
     * Returns human-readable description of this tool type.
     * @return Human-readable description of this tool type.
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Returns the rental price of this tool type (in cents)
     * @return The rental price of this tool type. (in cents)
     */
    public final int getCents() {
        return cents;
    }

    /**
     * Indicates if there is a charge for renting this tool on a weekday.
     * @return True if there is a charge for renting this tool on a weekday, otherwise false
     */
    public final boolean doesChargeForWeekdays() {
        return chargeForWeekdays;
    }

    /**
     * Indicates if there is a charge for renting this tool on a weekend.
     * @return True if there is a charge for renting this tool on a weekend, otherwise false
     */
    public final boolean doesChargeForWeekends() {
        return chargeForWeekends;
    }

    /**
     * Indicates if there is a charge for renting this tool on a holiday.
     * @return True if there is a charge for renting this tool on a holiday, otherwise false
     */
    public final boolean doesChargeForHolidays() {
        return chargeForHolidays;
    }
}
