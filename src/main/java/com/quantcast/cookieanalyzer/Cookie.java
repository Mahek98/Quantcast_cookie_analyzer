package com.quantcast.cookieanalyzer;

import java.time.LocalDate;

/**
 * The {@code Cookie} class represents a cookie entry from the log file.
 * Each entry contains the cookie identifier and the date it was logged.
 * 
 * This class encapsulates the cookie information that will be used to
 * determine the most active cookies on a given date.
 * 
 * <p>It holds two pieces of data:
 * <ul>
 *     <li>{@code cookie}: The unique identifier for the cookie.</li>
 *     <li>{@code date}: The date when the cookie was logged.</li>
 * </ul>
 * </p>
 * 
 * @author mahek
 */
public class Cookie {

    private final String cookie;  // The cookie identifier
    private final LocalDate date; // The date when the cookie was logged

    /**
     * Constructs a new {@code Cookie} object with the given cookie identifier and date.
     * 
     * @param cookie The identifier for the cookie.
     * @param date The date when the cookie was logged.
     */
    public Cookie(String cookie, LocalDate date) {
        this.cookie = cookie;
        this.date = date;
    }

    /**
     * Gets the cookie identifier.
     * 
     * @return The identifier of the cookie.
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * Gets the date when the cookie was logged.
     * 
     * @return The date of the log entry for the cookie.
     */
    public LocalDate getDate() {
        return date;
    }
}
