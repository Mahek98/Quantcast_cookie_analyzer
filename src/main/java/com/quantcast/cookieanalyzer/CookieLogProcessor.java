package com.quantcast.cookieanalyzer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The {@code CookieLogProcessor} class is responsible for processing a list of
 * {@code Cookie} objects to determine the most active cookies for a specified
 * date.
 *
 * The "most active cookies" are defined as the cookies that appear the most
 * times in the log on the given date.
 *
 * <p>
 * For example, if a particular cookie appears 5 times on the target date and
 * other cookies appear fewer times, this cookie would be considered the most
 * active.</p>
 *
 * <p>
 * If multiple cookies have the same maximum frequency, all of them are
 * returned.</p>
 *
 * @see Cookie
 * @see CookieLogParser
 * @see CommandLineParser
 * @see CookieAnalyzer
 *
 * author mahek
 */
public class CookieLogProcessor {

    private static final Logger logger = Logger.getLogger(CookieLogProcessor.class.getName());

    /**
     * Finds the most active cookies for a specified date.
     *
     * This method takes a list of {@code Cookie} objects and a target date, and
     * returns a list of cookies that appear the most on that date. The cookies
     * are identified by their string identifier.
     *
     * @param entries a list of {@code Cookie} objects to process
     * @param targetDate the date for which to find the most active cookies
     * @return a list of the most active cookies (cookie identifiers) on the
     * given date
     */
    public List<String> findMostActiveCookies(List<Cookie> entries, LocalDate targetDate) {
        if (entries == null || entries.isEmpty()) {
            logger.log(Level.WARNING, "Cookie entries list is null or empty. Returning empty result.");
            return List.of(); // Return empty list if no entries are provided
        }

        if (targetDate == null) {
            logger.log(Level.SEVERE, "Target date is null. Unable to process cookies.");
            throw new IllegalArgumentException("Target date cannot be null.");
        }

        // Map to store the frequency of each cookie on the target date
        Map<String, Integer> cookieFrequency = new HashMap<>();

        // Count the frequency of cookies on the target date
        for (Cookie entry : entries) {
            if (entry.getDate().equals(targetDate)) {
                cookieFrequency.put(entry.getCookie(),
                        cookieFrequency.getOrDefault(entry.getCookie(), 0) + 1);
            }
        }

        if (cookieFrequency.isEmpty()) {
            logger.log(Level.INFO, "No cookies found for the specified date: {0}", targetDate);
            return List.of(); // Return empty list if no cookies match the target date
        }

        // Find the maximum frequency among all cookies on the target date
        int maxFrequency = cookieFrequency.values().stream()
                .max(Integer::compare)
                .orElse(0);

        if (maxFrequency == 0) {
            logger.log(Level.INFO, "No active cookies found on the specified date: {0}", targetDate);
            return List.of();
        }

        // Return a list of cookies that have the maximum frequency
        List<String> mostActiveCookies = cookieFrequency.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency) // Filter cookies with max frequency
                .map(Map.Entry::getKey) // Extract the cookie identifier
                .collect(Collectors.toList());

        logger.log(Level.INFO, "Found {0} most active cookie(s) with frequency {1} on {2}.",
                new Object[]{mostActiveCookies.size(), maxFrequency, targetDate});

        return mostActiveCookies;
    }
}
