package com.quantcast.cookieanalyzer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The CommandLineParser class provides methods to validate and parse command
 * line arguments for a cookie analysis program. It checks the arguments for
 * specific flags and extracts necessary values such as file names and target
 * dates.
 * <p>
 * This class contains methods to validate the command line input, retrieve the
 * filename, and parse the target date in a defined format.
 * </p>
 *
 * <p>
 * Expected format:</p>
 * <pre>
 *     -f filename -d YYYY-MM-DD
 * </pre>
 *
 * <p>
 * For example:</p>
 * <pre>
 *     -f cookies.csv -d 2022-01-01
 * </pre>
 *
 * @see CookieLogParser
 * @see CookieAnalyzer
 * @see Cookie
 *
 * @author mahek
 */
public class CommandLineParser {

    private static final Logger logger = Logger.getLogger(CommandLineParser.class.getName());

    /**
     * Validates the command line arguments to ensure they meet the expected
     * format. Expected arguments: -f <filename> -d <date>
     *
     * @param args an array of command line arguments
     * @return true if the arguments are valid; otherwise, false
     */
    public static boolean validateArguments(String[] args) {
        boolean isValid = args.length == 4 && args[0].equals("-f") && args[2].equals("-d");
        if (!isValid) {
            logger.log(Level.SEVERE, "Invalid arguments. Expected format: -f <filename> -d <date>");
        }
        return isValid;
    }

    /**
     * Retrieves the filename argument from the command line arguments.
     *
     * @param args an array of command line arguments
     * @return the filename specified in the arguments
     * @throws ArrayIndexOutOfBoundsException if the arguments do not contain a
     * filename
     */
    public static String getFileName(String[] args) {
        try {
            return args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Filename not specified. Please provide a filename after -f.", e);
            throw e; // Rethrow exception to signal a fatal error in argument parsing
        }
    }

    /**
     * Parses and retrieves the target date from the command line arguments.
     *
     * @param args an array of command line arguments
     * @return the target date as a LocalDate object
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static LocalDate getTargetDate(String[] args) {
        try {
            return LocalDate.parse(args[3], DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Date not specified. Please provide a date after -d in YYYY-MM-DD format.", e);
            throw new IllegalArgumentException("Missing date argument. Expected format: -d YYYY-MM-DD.", e);
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Invalid date format provided. Expected format: YYYY-MM-DD. Argument was: " + args[3], e);
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.", e);
        }
    }
}
