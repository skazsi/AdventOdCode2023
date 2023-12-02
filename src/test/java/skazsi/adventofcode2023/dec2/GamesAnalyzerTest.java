package skazsi.adventofcode2023.dec2;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class GamesAnalyzerTest {

	@Test
	void analyzePart1() {
		// Given
		GamesAnalyzer underTest = new GamesAnalyzer();
		InputStream inputStream = GamesAnalyzerTest.class.getResourceAsStream("Dec2Input");

		// When
		int result = underTest.analyzePart1(inputStream);

		// Then
		then(result).isEqualTo(2061);
	}

	@Test
	void analyzePart2() {
		// Given
		GamesAnalyzer underTest = new GamesAnalyzer();
		InputStream inputStream = GamesAnalyzerTest.class.getResourceAsStream("Dec2Input");

		// When
		int result = underTest.analyzePart2(inputStream);

		// Then
		then(result).isEqualTo(72596);
	}
}
