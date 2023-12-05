package skazsi.adventofcode2023.dec5;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ScratchCardsTest {

	@Test
	void part1_example() {
		// Given
		SeedMapper underTest = new SeedMapper(getClass().getResourceAsStream("example"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(35);
	}

	@Test
	void part2_example() {
		// Given
		SeedMapper underTest = new SeedMapper(getClass().getResourceAsStream("example"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(46);
	}

	@Test
	void part1_input() {
		// Given
		SeedMapper underTest = new SeedMapper(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(806029445);
	}

	@Test
	void part2_input() {
		// Given
		SeedMapper underTest = new SeedMapper(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(59370572);
	}

	private static class SeedMapper {

		private List<Long> seeds;
		private List<Range> seedRanges;
		private List<Mapper> mappers;

		SeedMapper(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n\r\n");

				seeds = parseSeeds(scanner.next());
				seedRanges = parseSeedRanges();
				mappers = scanner.tokens().map(Mapper::new).collect(Collectors.toList());
			}
		}

		private List<Long> parseSeeds(String text) {
			try (Scanner scanner = new Scanner(text)) {
				scanner.useDelimiter("seeds:\\s|\\s+");
				return scanner.tokens().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
			}
		}

		private List<Range> parseSeedRanges() {
			List<Range> result = new ArrayList<>();
			for (int i = 0; i < seeds.size(); i = i + 2) {
				result.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
			}
			return result;
		}

		long part1() {
			long closestSeed = Long.MAX_VALUE;
			for (Long seed : seeds) {
				for (Mapper mapper : mappers) {
					seed = mapper.map(seed);
				}
				closestSeed = Math.min(seed, closestSeed);
			}

			return closestSeed;
		}

		long part2() {
			long closestSeed = Long.MAX_VALUE;
			for (Range seedRange : seedRanges) {
				System.out.println("Processing: " + seedRange);
				for (long seed = seedRange.start; seed < seedRange.end; seed++) {
					long value = seed;
					for (Mapper mapper : mappers) {
						value = mapper.map(value);
					}
					closestSeed = Math.min(value, closestSeed);
				}
			}

			return closestSeed;
		}
	}

	private static class Mapper {

		private String name;
		private Map<Range, Range> rangePairs = new HashMap<>();

		Mapper(String text) {
			try (Scanner scanner = new Scanner(text)) {
				scanner.useDelimiter("\\smap:\\s+|\\s+");
				name = scanner.next();

				while (scanner.hasNext()) {
					long destinationStart = scanner.nextLong();
					long sourceStart = scanner.nextLong();
					long length = scanner.nextLong();
					rangePairs.put(new Range(sourceStart, sourceStart + length), new Range(destinationStart, destinationStart + length));
				}
			}
		}

		long map(long value) {
			Optional<Entry<Range, Range>> rangePair = rangePairs.entrySet().stream().filter(e -> e.getKey().isInRange(value)).findFirst();
			if (rangePair.isEmpty()) {
				return value;
			}

			long positionInRange = value - rangePair.get().getKey().start;
			return rangePair.get().getValue().start + positionInRange;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder(name).append("\n");
			rangePairs.entrySet().stream().forEach(e -> stringBuilder.append(e.getKey() + " -> " + e.getValue() + "\n"));
			return stringBuilder.toString();
		}
	}

	private static class Range {

		private long start;
		private long end;

		Range(long start, long end) {
			this.start = start;
			this.end = end;
		}

		boolean isInRange(long value) {
			return start <= value && value < end;
		}

		@Override
		public String toString() {
			return start + "-" + end;
		}
	}

}
