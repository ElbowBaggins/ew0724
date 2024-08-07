package net.mythoclast.tooltime.model;

/**
 * Enumerates the four tools we have for rental.
 */
public enum Tools {
    /**
     * Specifies a Stihl chainsaw, code CHNS
     */
    CHNS(new Tool("CHNS", ToolType.CHAINSAW, "Stihl")),

    /**
     * Specifies a Werner ladder, code LADW
     */
    LADW(new Tool("LADW", ToolType.LADDER, "Werner")),

    /**
     * Specifies a DeWalt jackhammer, code JAKD
     */
    JAKD(new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt")),

    /**
     * Specifies a Rigid jackhammer, code JAKR
     */
    JAKR(new Tool("JAKR", ToolType.JACKHAMMER, "Rigid"));

    private final Tool tool;

    Tools(final Tool tool) {
        this.tool = tool;
    }

    /**
     * Returns the actually-enumerated Tool instance
     * @return The actually-enumerated Tool instance
     */
    public Tool getTool() {
        return tool;
    }
}
