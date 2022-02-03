package com.revature.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;

/**
 * Very simple profanity filter that replaces a list of blacklisted words from /src/main/resources/badwords
 * with those found in the replacements array.  There is easy ways around the filter like capitalization
 * and 
 * @author Colin Knox
 */
public class ProfanityFilter {

	String unfiltered;
	String filtered;
	static List<String> badWords;
	static RandomGenerator generator;
	String[] replacements = { "balderdash", "poo on a stick", "dagnabbit" };

	static {
		badWords = new LinkedList<String>();
		String path = System.getProperty("user.dir");
		File badWordsFile = new File(path + "\\src\\main\\resources\\badwords");
		try (BufferedReader reader = new BufferedReader(new FileReader(badWordsFile))) {
			String word;
			while ((word = reader.readLine()) != null) {
				badWords.add(word);
			}
		} catch (FileNotFoundException e) {
			badWords.add("hokum");
			e.printStackTrace();
		} catch (IOException io) {
			badWords.add("hokum");
			io.printStackTrace();
		}
		generator = RandomGenerator.getDefault();
	}

	public ProfanityFilter(String unfiltered) {
		this.unfiltered = unfiltered;
		filtered = profanityFilter();
	}

	public String getUnfiltered() {
		return unfiltered;
	}

	public String getFiltered() {
		return filtered;
	}

	public String profanityFilter(String content) {
		String regexList = badWords.stream().reduce("", (wordOne, wordTwo) -> {
			return wordOne.concat("|" + wordTwo);
		});
		Pattern regex = Pattern.compile("(?i)(?<= |^)(" + regexList + ")(?= |$)");
		
		return regex.matcher(content).replaceAll((matchResult) -> {
			int rndm = generator.nextInt(replacements.length);
			System.out.println(rndm);
			return replacements[rndm];
		});
	}

	private String profanityFilter() {
		return profanityFilter(unfiltered);
	}
}
