package net.mythoclast.tooltime.model;

import java.util.HashSet;
import java.util.Set;

import static java.lang.StringTemplate.STR;

/**
 * Defines a Tool that can be rented.
 *
 * @param code A unique and (presumably) alphanumeric value which identifies this tool. May not be null.
 * @param type Which type of tool this is. May not be null.
 * @param brand A String containing the brand of the tool. May be arbitrary. May not be null.
 */
public record Tool(String code, ToolType type, String brand) {

    /**
     * Keeps track of extant Tool codes. Trying to create a new Tool with an existing code is forbidden.
     */
    private static final Set<String> codes = new HashSet<>();

    public Tool  {
        // Check if supplied code has already been used, throw IllegalArgumentException if so.
        if (Tool.codes.contains(code)) {
            throw new IllegalArgumentException(STR."Tool code `\{code}` is already in use.");
        }
        if (null == code) {
            throw new IllegalArgumentException("Tool code may not be null.");
        }
        if (null == type) {
            throw new IllegalArgumentException("Tool type may not be null.");
        }
        if (null == brand) {
            throw new IllegalArgumentException("Tool brand may not be null.");
        }

        // Record the use of the supplied Tool code.
        Tool.codes.add(code);
    }
}
