package skazsi.adventofcode2023.dec3;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

public class EngineSchematicTest {

	@Test
	void part1_example() {
		// Given
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("467..114..\n");
		stringBuilder.append("...*......\n");
		stringBuilder.append("..35..633.\n");
		stringBuilder.append("......#...\n");
		stringBuilder.append("617*......\n");
		stringBuilder.append(".....+.58.\n");
		stringBuilder.append("..592.....\n");
		stringBuilder.append("......755.\n");
		stringBuilder.append("...$.*....\n");
		stringBuilder.append(".664.598..");

		EngineSchematic underTest = new EngineSchematic(stringBuilder.toString());

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(4361);
	}

	@Test
	void part1() throws Exception {
		// Given
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(EngineSchematicTest.class.getResourceAsStream("Dec3Input")))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		}

		EngineSchematic underTest = new EngineSchematic(stringBuilder.toString());

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(529618);
	}

	@Test
	void part2_example() {
		// Given
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("467..114..\n");
		stringBuilder.append("...*......\n");
		stringBuilder.append("..35..633.\n");
		stringBuilder.append("......#...\n");
		stringBuilder.append("617*......\n");
		stringBuilder.append(".....+.58.\n");
		stringBuilder.append("..592.....\n");
		stringBuilder.append("......755.\n");
		stringBuilder.append("...$.*....\n");
		stringBuilder.append(".664.598..");

		EngineSchematic underTest = new EngineSchematic(stringBuilder.toString());

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(467835);
	}

	@Test
	void part2() throws Exception {
		// Given
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(EngineSchematicTest.class.getResourceAsStream("Dec3Input")))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		}

		EngineSchematic underTest = new EngineSchematic(stringBuilder.toString());

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(77509019);
	}
}
