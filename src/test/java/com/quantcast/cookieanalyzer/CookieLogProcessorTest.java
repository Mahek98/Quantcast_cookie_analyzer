package com.quantcast.cookieanalyzer;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link CookieLogProcessor} class.
 * 
 * This class contains test cases for the method {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)}.
 * It tests various scenarios such as:
 * 1. Finding the most active cookie when there is only one most frequent cookie.
 * 2. Handling cases where no cookies are active on a given date.
 * 3. Handling cases where there is a tie between multiple cookies.
 * 4. Handling an empty list of cookies.
 * 5. Handling cases where a cookie appears on multiple dates.
 * 
 * @author mahek
 */
public class CookieLogProcessorTest {

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method with a basic case where
     * one cookie is the most active.
     * 
     * This test verifies that when one cookie appears more frequently than others, it is returned as the most active.
     */
    @Test
    void testFindMostActiveCookies() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("SAZuXPGUrfbcn5UA", date),
                new Cookie("AtY0laUfhglK3lC7", date)
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // Verifies that there is exactly one most active cookie.
        assertEquals(1, result.size());
        // Verifies that the most active cookie is the one that appears most frequently.
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method when there are no cookies 
     * for the target date.
     * 
     * This test ensures that the method returns an empty list when there are no cookies for the specified date.
     */
    @Test
    void testFindMostActiveCookiesNoMatches() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 10);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", LocalDate.of(2018, 12, 9)),
                new Cookie("SAZuXPGUrfbcn5UA", LocalDate.of(2018, 12, 9))
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // No cookies on this date, should return an empty list
        assertEquals(0, result.size());
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method when there is a tie
     * between multiple cookies.
     * 
     * This test checks that the method can handle a scenario where multiple cookies have the same highest frequency
     * for a given date.
     */
    @Test
    void testFindMostActiveCookiesWithTies() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("SAZuXPGUrfbcn5UA", date),
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("SAZuXPGUrfbcn5UA", date)
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // Both cookies appear twice, so both should be returned
        assertEquals(2, result.size());
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
        assertEquals("SAZuXPGUrfbcn5UA", result.get(1));
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method with an empty list of cookies.
     * 
     * This test ensures that the method returns an empty list when no cookies are provided.
     */
    @Test
    void testFindMostActiveCookiesWithEmptyList() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of();  // Empty list

        List<String> result = processor.findMostActiveCookies(entries, date);

        // No cookies, should return an empty list
        assertEquals(0, result.size());
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method with a list of cookies 
     * that have entries on multiple dates.
     * 
     * This test ensures that the method filters cookies correctly based on the target date.
     */
    @Test
    void testFindMostActiveCookiesAcrossMultipleDates() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("SAZuXPGUrfbcn5UA", LocalDate.of(2018, 12, 8)),
                new Cookie("AtY0laUfhglK3lC7", date)
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // "AtY0laUfhglK3lC7" is the most active cookie on 2018-12-09
        assertEquals(1, result.size());
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method with cookies that 
     * are all the same but appear on multiple dates.
     * 
     * This test ensures that the method counts cookies correctly across multiple dates.
     */
    @Test
    void testFindMostActiveCookiesSameCookieMultipleDates() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", LocalDate.of(2018, 12, 8)),
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("AtY0laUfhglK3lC7", date)
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // "AtY0laUfhglK3lC7" is the most active cookie on 2018-12-09
        assertEquals(1, result.size());
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
    }

    /**
     * Test the {@link CookieLogProcessor#findMostActiveCookies(List, LocalDate)} method when all cookies 
     * are unique on a specific date.
     * 
     * This test checks the case where each cookie appears only once for the target date.
     */
    @Test
    void testFindMostActiveCookiesUniqueCookies() {
        CookieLogProcessor processor = new CookieLogProcessor();
        LocalDate date = LocalDate.of(2018, 12, 9);

        List<Cookie> entries = List.of(
                new Cookie("AtY0laUfhglK3lC7", date),
                new Cookie("SAZuXPGUrfbcn5UA", date),
                new Cookie("4sMM2LxV07bPJzwf", date)
        );

        List<String> result = processor.findMostActiveCookies(entries, date);

        // All cookies appear once, so all should be returned
        assertEquals(3, result.size());
        assertEquals("AtY0laUfhglK3lC7", result.get(0));
        assertEquals("SAZuXPGUrfbcn5UA", result.get(1));
        assertEquals("4sMM2LxV07bPJzwf", result.get(2));
    }
}
