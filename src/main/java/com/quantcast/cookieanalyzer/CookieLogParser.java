package com.quantcast.cookieanalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.nio.file.InvalidPathException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code CookieLogParser} class is responsible for reading and parsing a
 * cookie log file. It processes each log entry, extracting the cookie
 * identifier and timestamp, and converts the timestamp to a {@code LocalDate}
 * for easier processing.
 *
 * The log file is expected to have two columns: 1. The cookie identifier
 * (string) 2. The timestamp of the log entry (ISO-8601 format with timezone).
 *
 * This class returns a list of {@code Cookie} objects representing the log
 * entries.
 *
 * <p>
 * Each entry in the log file is expected to be in the following format:</p>
 * <pre>
 *     cookie,timestamp
 * </pre>
 *
 * <p>
 * For example:</p>
 * <pre>
 *     AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
 * </pre>
 *
 * @author mahek
 */
public class CookieLogParser {

    private static final Logger logger = Logger.getLogger(CookieLogParser.class.getName());
    private final Path filePath;

    /**
     * Constructs a new {@code CookieLogParser} with the specified file name.
     *
     * @param filename The path to the log file to be parsed.
     * @throws InvalidPathException if the file path is invalid.
     */
    public CookieLogParser(String filename) {
        try {
            this.filePath = Path.of(filename);
        } catch (InvalidPathException e) {
            logger.log(Level.SEVERE, "Invalid file path: {0}", filename);
            throw e;
        }
    }

    /**
     * Parses the log file and returns a list of {@code Cookie} objects. Each
     * entry in the log file is expected to contain a cookie identifier and a
     * timestamp, which will be converted into a {@code LocalDate}.
     *
     * The method skips the header line and processes each subsequent line to
     * extract the cookie and timestamp. If a line is incorrectly formatted or
     * has an invalid timestamp, it is skipped, and a warning is logged.
     *
     * @return A list of {@code Cookie} objects, each representing a log entry.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public List<Cookie> parseLog() throws IOException {
        List<Cookie> entries = new ArrayList<>();

        try (var reader = Files.lines(filePath).skip(1)) { // Skip header line
            reader.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Ensure there are exactly two fields
                    String cookie = parts[0].trim(); // Extract cookie and trim spaces
                    try {
                        // Parse the timestamp and convert to LocalDate
                        ZonedDateTime timestamp = ZonedDateTime.parse(parts[1].trim(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                        entries.add(new Cookie(cookie, timestamp.toLocalDate())); // Add new Cookie entry
                    } catch (DateTimeParseException e) {
                        // Log warning if timestamp is invalid
                        logger.log(Level.WARNING, "Skipping invalid timestamp in line: {0}", line);
                    }
                } else {
                    // Log warning if the line has an unexpected format
                    logger.log(Level.WARNING, "Skipping malformed line: {0}", line);
                }
            });
        } catch (NoSuchFileException e) {
            // Log severe error if the file does not exist
            logger.log(Level.SEVERE, "File not found: {0}", filePath);
            throw e;
        } catch (IOException e) {
            // Log severe error for other I/O issues
            logger.log(Level.SEVERE, "I/O error while reading file: {0}", filePath);
            throw e;
        } catch (SecurityException e) {
            // Log severe error if there's a security issue accessing the file
            logger.log(Level.SEVERE, "Permission denied: Unable to read file at {0}", filePath);
            throw e;
        }

        return entries; // Return the list of Cookie objects
    }
}
