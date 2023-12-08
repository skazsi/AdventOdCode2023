package skazsi.adventofcode2023.dec8;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class NavigationTest {

	public static void main(String[] args) {
		long a = 20777;
		long b = 19199;
		long c = 18673;
		long d = 16043;
		long e = 12361;
		long f = 15517;

		long counter = 12361;
		while (counter < Long.MAX_VALUE) {
			if ((counter % a == 0) && (counter % b == 0) && (counter % c == 0) && (counter % d == 0) && (counter % e == 0) && (counter % f == 0)) {
				System.out.println(counter);
				System.exit(0);
			}
			counter = counter + 12361;
		}

	}

	@Test
	void part1_example1() {
		// Given
		Navigation underTest = new Navigation(getClass().getResourceAsStream("example1"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(2);
	}

	@Test
	void part1_example2() {
		// Given
		Navigation underTest = new Navigation(getClass().getResourceAsStream("example2"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(6);
	}

	@Test
	void part1_input() {
		// Given
		Navigation underTest = new Navigation(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(12361);
	}

	@Test
	void part2_input() {
		// Given
		Navigation underTest = new Navigation(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(12361);
	}

	private static class Navigation {

		private Map<String, LeftRight> map = new HashMap<>();
		private String instructions;

		Navigation(InputStream inputStream) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				instructions = reader.readLine();
				reader.readLine();
				String line = null;
				while ((line = reader.readLine()) != null) {

					try (Scanner scanner = new Scanner(line)) {
						scanner.useDelimiter("\\s=\\s\\(|,\\s|\\)");
						map.put(scanner.next(), new LeftRight(scanner.next(), scanner.next()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int part1() {
			int stepCounter = 0;
			int instructionPosition = 0;

			String location = "AAA";
			while (true) {
				char instruction = instructions.charAt(instructionPosition++);
				if (instructionPosition == instructions.length()) {
					instructionPosition = 0;
				}

				location = map.get(location).turn(instruction);
				stepCounter++;
				if (location.equals("ZZZ")) {
					return stepCounter;
				}
			}
		}

		long part2() {
			long stepCounter = 0;
			int instructionPosition = 0;

			// 20777
			// 19199
			// 18673
			// 16043
			// 12361
			// 15517

			String[] locations = new String[] { "DPA", "QLA", "VJA", "GTA", "AAA", "XQA" };
			while (true) {
				char instruction = instructions.charAt(instructionPosition++);
				if (instructionPosition == instructions.length()) {
					instructionPosition = 0;
				}

				int endsOnZ = 0;
				for (int i = 0; i < locations.length; i++) {
					locations[i] = map.get(locations[i]).turn(instruction);
					if (locations[i].endsWith("Z")) {
						endsOnZ++;
					}
				}
				stepCounter++;
				for (int i = 0; i < locations.length; i++) {
					if (locations[i].endsWith("Z")) {
						System.out.println(i + "\t" + stepCounter + "\t" + locations[i]);
					}
				}

				if (endsOnZ == locations.length) {
					return stepCounter;
				}
			}
		}
	}

	private static class LeftRight {
		private String left;
		private String right;

		private LeftRight(String left, String right) {
			this.left = left;
			this.right = right;
		}

		String turn(char instruction) {
			switch (instruction) {
			case 'L':
				return left;
			case 'R':
				return right;
			default:
				throw new RuntimeException("Unknown insturction: " + instruction);
			}
		}
	}
}
