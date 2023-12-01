package skazsi.adventofcode2023.dec1;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NumberParserTest {

	private NumberParser underTest = new NumberParser();

	@ParameterizedTest
	@CsvSource(value = {
			"fouronevhnrz44,44",
			"eightg1,11",
			"4ninejfpd1jmmnnzjdtk5sjfttvgtdqspvmnhfbm,45",
			"78seven8,78",
			"6pcrrqgbzcspbd,66",
			"7sevenseven,77",
			"1threeeight66,16",
			"one1sevensskhdreight,11",
			"rninethree6,66",
			"eight45fourfgfive1,41",
			"xdlnbfzxgfmhd4t,44",
			"7tf,77",
	})
	void parseAsPart1(String input, int expected) {
		// When
		int result = underTest.parseAsPart1(input);
		
		// Then
		then(result).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource(value = {
			"fouronevhnrz44,44",
			"eightg1,81",
			"4ninejfpd1jmmnnzjdtk5sjfttvgtdqspvmnhfbm,45",
			"78seven8,78",
			"6pcrrqgbzcspbd,66",
			"7sevenseven,77",
			"1threeeight66,16",
			"one1sevensskhdreight,18",
			"rninethree6,96",
			"eight45fourfgfive1,81",
			"xdlnbfzxgfmhd4t,44",
			"7tf,77",
			"8oneldkrfcssbfeight,88",
			"five5ninebvvfv,59",
			"sixrhxkzcgfhltrchq3three91,61",
			"lnxms8,88",
			"threekv33eightninethree,33",
			"fourxrsxhclj99twosevennine7htxdr,47",
			"5hdhtdxgktztjdjrhkmlblsevenseven1four8,58",
			"25xmvshkbmtkmvqpfhgq8fivefqctjm6two,22",
			"nine533two,92",
			"sixmbkjzpcxvfive2,62",
			"seven3fivevhkpjvfqsfivemfdvlkhhmmvtztjf,75",
			"3eight5threefour,34",
			"fplrjjznseventwocrv9,79",
			"mxqvdb5onesix84fpkzf,54",
			"17five6mvxgkkmz2two2mf,12",
			"lrqnqfncvvvrrpkfour92xsxfztwonehsb,41",
			"dphngmgfhhhcjxmbmqdk3nine54,34",
			"34xdbhnbhbmljxc55oneeight,38",
			"cpgdcctwothreevlqmk1qpdthree,23",
			"977ckpkmx5,95",
			"7cx81,71",
			"two1nine,29",
			"eightwothree,83",
			"abcone2threexyz,13",
			"xtwone3four,24",
			"4nineeightseven2,42",
			"zoneight234,14",
			"7pqrstsixteen,76"
	})
	void parseAsPart2(String input, int expected) {
		// When
		int result = underTest.parseAsPart2(input);
		
		// Then
		then(result).isEqualTo(expected);
	}
}
