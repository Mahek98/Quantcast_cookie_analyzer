package com.quantcast.cookieanalyzer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code CookieLogReader} class is responsible for reading cookie log files
 * and providing a list of parsed {@code Cookie} objects.
 * <p>
 * This class uses a specified filename to locate the log file and utilizes a
 * {@code CookieLogParser} to parse the content of the file into {@code Cookie}
 * objects.
 * </p>
 *
 * <p>
 * Example usage:</p>
 * <pre>
 *     CookieLogReader reader = new CookieLogReader("cookie_log.csv");
 *     List&lt;Cookie&gt; cookies = reader.readLogFile();
 * </pre>
 *
 * @see CookieLogParser
 * @see CookieAnalyzer
 * @see Cookie
 *
 * author mahek
 */
public class CookieLogReader {

    private static final Logger logger = Logger.getLogger(CookieLogReader.class.getName());
    private final String filename;

    /**
     * Constructs a new {@code CookieLogReader} with the specified filename.
     *
     * @param filename the name of the log file to be read
     * @throws IllegalArgumentException if the filename is null or empty
     */
    public CookieLogReader(String filename) {
        if (filename == null || filename.isBlank()) {
            logger.log(Level.SEVERE, "Invalid filename provided. Filename cannot be null or empty.");
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }
        this.filename = filename;
    }

    /**
     * Reads the log file and parses its content to return a list of
     * {@code Cookie} objects.
     *
     * @return a List of {@code Cookie} objects parsed from the log file
     * @throws IOException if an error occurs while reading the log file
     */
    public List<Cookie> readLogFile() throws IOException {
        try {
            logger.log(Level.INFO, "Attempting to read and parse log file: {0}", filename);
            CookieLogParser parser = new CookieLogParser(filename);
            return parser.parseLog();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read or parse the log file: " + filename, e);
            throw e; // Rethrow IOException to signal failure to the caller
        }
    }
}
