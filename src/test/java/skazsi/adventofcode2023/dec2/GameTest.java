package skazsi.adventofcode2023.dec2;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GameTest {

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = { 
			"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green|true",
			"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue|true",
			"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red|false",
			"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red|false",
			"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green|true" })
	void isUnderLimit(String gameText, boolean expected) {
		// Given
		Game underTest = new Game(gameText);
		
		// When
		boolean result = underTest.isUnderLimits();

		// Then
		then(result).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = { 
			"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green|48",
			"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue|12",
			"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red|1560",
			"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red|630",
			"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green|36" })
	void getPowerOfMinimalCubes(String gameText, int expected) {
		// Given
		Game underTest = new Game(gameText);
		
		// When
		int result = underTest.getPowerOfMinimalCubes();

		// Then
		then(result).isEqualTo(expected);
	}
}
