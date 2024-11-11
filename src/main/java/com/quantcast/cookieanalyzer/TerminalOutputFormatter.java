package com.quantcast.cookieanalyzer;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class responsible for formatting and displaying output for the
 * Quantcast Cookie Analyzer program. Provides methods to format cookie data
 * with ANSI styling for terminal output and to center-align text based on
 * terminal width.
 *
 * This class formats a list of the most active cookies on a specified date,
 * applies optional coloring and bold styling, and centers the output according
 * to the width of the terminal.
 *
 * <p>
 * Supports basic ANSI styling with text in bold and green color for improved
 * readability. If the terminal width is unavailable, a default width of 80
 * characters is used.
 * </p>
 *
 * author mahek
 */
public class TerminalOutputFormatter {

    private static final Logger logger = Logger.getLogger(TerminalOutputFormatter.class.getName());

    /**
     * ANSI escape code for green text.
     */
    private static final String GREEN = "\033[32m";

    /**
     * ANSI escape code for bold text.
     */
    private static final String BOLD = "\033[1m";

    /**
     * ANSI escape code to reset text formatting.
     */
    private static final String RESET = "\033[0m";

    /**
     * Extra spacing for top and bottom padding in the formatted output.
     */
    private static final String SPACING = "\n\n";

    /**
     * Builds a formatted output string for the list of most active cookies on
     * the specified date. If no cookies are found, it returns a message
     * indicating no active cookies for the date.
     *
     * @param mostActiveCookies A list of cookie identifiers representing the
     * most active cookies on the given date.
     * @param targetDate The target date for which the cookie data is being
     * analyzed.
     * @return A formatted string with a list of most active cookies or an
     * informational message if the list is empty.
     */
    public static String buildOutput(List<String> mostActiveCookies, LocalDate targetDate) {
        StringBuilder output = new StringBuilder();

        // Check if the list of most active cookies is empty
        if (mostActiveCookies.isEmpty()) {
            output.append(GREEN).append(BOLD).append(SPACING)
                    .append("No active cookies found for the specified date.")
                    .append(SPACING).append(RESET);
        } else {
            output.append(GREEN).append(BOLD).append(SPACING)
                    .append("Most active cookies on ").append(targetDate).append(":").append(SPACING);

            // Append each cookie identifier
            for (String cookie : mostActiveCookies) {
                output.append(cookie).append("\n");
            }
            output.append(SPACING).append(RESET); // Reset formatting at the end
        }
        return output.toString();
    }

    /**
     * Prints the specified output text centered to the specified terminal
     * width. Each line of text is padded with spaces to center-align it within
     * the given width.
     *
     * @param output The text output to be centered.
     * @param terminalWidth The width of the terminal in characters.
     */
    public static void printCentered(String output, int terminalWidth) {
        String[] lines = output.split("\n");

        for (String line : lines) {
            // Calculate leading spaces for center alignment
            int spaces = (terminalWidth - line.length()) / 2;
            spaces = Math.max(spaces, 0);  // Ensure non-negative spacing
            String centeredLine = " ".repeat(spaces) + line;
            System.out.println(centeredLine);
        }
    }

    /**
     * Attempts to retrieve the width of the terminal in characters by reading
     * the "COLUMNS" environment variable. If unavailable or invalid, it
     * defaults to a width of 80 characters.
     *
     * @return The width of the terminal in characters, or 80 if the width
     * cannot be determined.
     */
    public static int getTerminalWidth() {
        int width = 80; // Default width for unsupported systems
        try {
            String terminalWidthStr = System.getenv("COLUMNS");
            if (terminalWidthStr != null) {
                width = Integer.parseInt(terminalWidthStr);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid terminal width format. Defaulting to 80.", e);
        }
        return width;
    }
}
