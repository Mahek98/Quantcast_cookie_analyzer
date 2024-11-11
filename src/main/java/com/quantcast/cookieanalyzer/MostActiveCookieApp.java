package com.quantcast.cookieanalyzer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code MostActiveCookieApp} class serves as the entry point for the
 * application that analyzes cookie log files to identify the most active
 * cookies on a given date.
 *
 * <p>
 * This application validates command-line arguments, reads cookie log files,
 * processes the data, and outputs the results in a formatted manner.</p>
 *
 * <p>
 * Example usage:</p>
 * <pre>
 *     java MostActiveCookieApp -f cookie_log.csv -d 2021-12-09
 * </pre>
 *
 * @see CommandLineParser
 * @see CookieLogReader
 * @see CookieLogProcessor
 * @see TerminalOutputFormatter
 *
 * author mahek
 */
public class MostActiveCookieApp {

    private static final Logger logger = Logger.getLogger(MostActiveCookieApp.class.getName());

    /**
     * The main method of the application, which executes the cookie analysis.
     *
     * @param args command-line arguments for the application. Expected format:
     * -f <filename> -d <YYYY-MM-DD>
     */
    public static void main(String[] args) {
        // Validate and parse command-line arguments
        if (!CommandLineParser.validateArguments(args)) {
            System.out.println("Usage: java MostActiveCookieApp -f <filename> -d <YYYY-MM-DD>");
            return;
        }

        String filename = CommandLineParser.getFileName(args);
        LocalDate targetDate;
        try {
            targetDate = CommandLineParser.getTargetDate(args);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Invalid date format provided.", e);
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try {
            // Read the cookie log file
            CookieLogReader logReader = new CookieLogReader(filename);
            List<Cookie> entries = logReader.readLogFile();

            if (entries.isEmpty()) {
                logger.log(Level.WARNING, "The log file is empty or contains no valid entries.");
                System.out.println("Error: The log file is empty or contains no valid entries.");
                return;
            }

            // Find the most active cookies for the target date
            CookieLogProcessor processor = new CookieLogProcessor();
            List<String> mostActiveCookies = processor.findMostActiveCookies(entries, targetDate);

            if (mostActiveCookies.isEmpty()) {
                logger.log(Level.INFO, "No active cookies found for the specified date: {0}", targetDate);
                System.out.println("No active cookies found for the specified date: " + targetDate);
                return;
            }

            // Format and print the output
            int terminalWidth = TerminalOutputFormatter.getTerminalWidth();
            String output = TerminalOutputFormatter.buildOutput(mostActiveCookies, targetDate);

            // Print the output centered in the terminal
            TerminalOutputFormatter.printCentered(output, terminalWidth);

            // Add extra bottom spacing
            System.out.println("\n\n");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to process the log file: " + filename, e);
            System.out.println("Error: Unable to process the log file. " + e.getMessage());
        }
    }
}
