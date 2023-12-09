package skazsi.adventofcode2023.dec9;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class OASISReportAnalyzerTest {

	@Test
	void part1_example() {
		// Given
		OASISReportAnalyzer underTest = new OASISReportAnalyzer(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.forwardAnalyze();

		// Then
		then(result).isEqualTo(114);
	}

	@Test
	void part1_input() {
		// Given
		OASISReportAnalyzer underTest = new OASISReportAnalyzer(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.forwardAnalyze();

		// Then
		then(result).isEqualTo(1887980197);
	}

	@Test
	void part2_example() {
		// Given
		OASISReportAnalyzer underTest = new OASISReportAnalyzer(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.backwardAnalyze();

		// Then
		then(result).isEqualTo(2);
	}

	@Test
	void part2_input() {
		// Given
		OASISReportAnalyzer underTest = new OASISReportAnalyzer(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.backwardAnalyze();

		// Then
		then(result).isEqualTo(990);
	}

	private static class OASISReportAnalyzer {

		private List<List<Integer>> values = new ArrayList<>();

		OASISReportAnalyzer(InputStream inputStream) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line = null;
				while ((line = reader.readLine()) != null) {

					try (Scanner scanner = new Scanner(line)) {
						values.add(scanner.tokens().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int forwardAnalyze() {
			return values.stream().mapToInt(this::forward).sum();
		}

		int forward(List<Integer> values) {
			List<Integer> differences = new ArrayList<>();
			for (int i = 0; i < values.size() - 1; i++) {
				differences.add(values.get(i + 1) - values.get(i));
			}
			if (differences.stream().filter(v -> v != 0).count() > 0) {
				int forecast = forward(differences);
				return values.get(values.size() - 1) + forecast;
			}
			return values.get(values.size() - 1);
		}

		int backwardAnalyze() {
			return values.stream().mapToInt(this::backward).sum();
		}

		int backward(List<Integer> values) {
			List<Integer> differences = new ArrayList<>();
			for (int i = 0; i < values.size() - 1; i++) {
				differences.add(values.get(i + 1) - values.get(i));
			}
			if (differences.stream().filter(v -> v != 0).count() > 0) {
				int forecast = backward(differences);
				return values.get(0) - forecast;
			}
			return values.get(0);
		}
	}
}
