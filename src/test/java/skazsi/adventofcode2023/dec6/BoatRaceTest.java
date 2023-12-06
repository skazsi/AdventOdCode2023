package skazsi.adventofcode2023.dec6;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Map;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

public class BoatRaceTest {

	@Test
	void part1_example() {
		// Given
		BoatRace underTest = new BoatRace();

		// When
		long result = underTest.calculate(Map.of(7L, 9L, 15L, 40L, 30L, 200L));

		// Then
		then(result).isEqualTo(288);
	}

	@Test
	void part1_input() {
		// Given
		BoatRace underTest = new BoatRace();

		// When
		long result = underTest.calculate(Map.of(49L, 298L, 78L, 1185L, 79L, 1066L, 80L, 1181L));

		// Then
		then(result).isEqualTo(2269432);
	}

	@Test
	void part2_example() {
		// Given
		BoatRace underTest = new BoatRace();

		// When
		long result = underTest.calculate(Map.of(71530L, 940200L));

		// Then
		then(result).isEqualTo(71503);
	}

	@Test
	void part2_input() {
		// Given
		BoatRace underTest = new BoatRace();

		// When
		long result = underTest.calculate(Map.of(49787980L, 298118510661181L));

		// Then
		then(result).isEqualTo(35865985);
	}

	private static class BoatRace {

		long calculate(Map<Long, Long> races) {
			return races.entrySet().stream().map(e -> new Race(e.getKey(), e.getValue())).mapToLong(Race::numOfWinningCombination).reduce(1, (result, value) -> result * value);
		}

	}

	private static class Race {

		private long time;
		private long distance;

		Race(long time, long distance) {
			this.time = time;
			this.distance = distance;
		}

		long numOfWinningCombination() {
			return LongStream.range(1, time - 1).filter(hold -> hold * (time - hold) > distance).count();
		}
	}
}
