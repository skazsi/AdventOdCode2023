package skazsi.adventofcode2023.dec1;

import java.util.Map;
import java.util.Map.Entry;

class NumberParser {

	private static final Map<String, String> WRITTEN_NUMBERS = Map.of("one", "1", "two", "2", "three", "3", "four", "4",
			"five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9");

	int parseAsPart1(String text) {
		String firstDigit = findFirstDigit(text);
		String lastDigit = findLastDigit(text);
		return Integer.parseInt(firstDigit + lastDigit);
	}

	int parseAsPart2(String text) {
		String forwardReplacedText = text;
		for (int i = 0; i < forwardReplacedText.length(); i++) {
			for (Entry<String, String> writtenNumber : WRITTEN_NUMBERS.entrySet()) {
				if (forwardReplacedText.indexOf(writtenNumber.getKey()) == i) {
					forwardReplacedText = forwardReplacedText.substring(0, i) + writtenNumber.getValue()
							+ forwardReplacedText.substring(i + writtenNumber.getKey().length());
				}
			}
		}
		
		String reverseReplacedText = text;
		for (int i = reverseReplacedText.length(); i > -1; i--) {
			for (Entry<String, String> writtenNumber : WRITTEN_NUMBERS.entrySet()) {
				if (reverseReplacedText.lastIndexOf(writtenNumber.getKey()) == i) {
					reverseReplacedText = reverseReplacedText.substring(0, i) + writtenNumber.getValue()
							+ reverseReplacedText.substring(i + writtenNumber.getKey().length());
				}
			}
		}

		String firstDigit = findFirstDigit(forwardReplacedText);
		String lastDigit = findLastDigit(reverseReplacedText);
		return Integer.parseInt(firstDigit + lastDigit);
	}

	private String findFirstDigit(String text) {
		return Character.toString(text.chars().filter(Character::isDigit).findFirst().orElseThrow());
	}

	private String findLastDigit(String text) {
		return Character.toString(reverse(text).chars().filter(Character::isDigit).findFirst().orElseThrow());
	}

	private String reverse(String text) {
		return new StringBuilder(text).reverse().toString();
	}
}
