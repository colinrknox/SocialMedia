package com.revature.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

class ProfanityFilterTest {

	ProfanityFilter filter;
	String testString = "BARNACLES pineapple-pizza barnacles pineapple-pizza";
	String testStringTwo = "bonjour me llamo Colin";

	@Test
	void testProfanityFilter() {
		filter = new ProfanityFilter(testString);
		String sanitized = filter.getFiltered();
		Set<?> wordSet = new HashSet<>(Arrays.asList(sanitized.split(" ")));

		assertFalse(wordSet.contains("barnacles"));
		assertFalse(wordSet.contains("pineapple-pizza"));
		assertNotEquals(filter.getFiltered(), filter.getUnfiltered());
	}

	@Test
	void testProfanityFilterNone() {
		filter = new ProfanityFilter(testStringTwo);
		String sanitized = filter.getFiltered();
		Set<?> wordSet = new HashSet<>(Arrays.asList(sanitized.split(" ")));

		assertFalse(wordSet.contains("barnacles"));
		assertFalse(wordSet.contains("pineapple-pizza"));
		assertEquals(testStringTwo, sanitized);
	}

}
