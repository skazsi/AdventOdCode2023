package skazsi.adventofcode2023.dec4;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ScratchCardsTest {

	@Test
	void part1_example() {
		// Given
		ScratchCards underTest = new ScratchCards(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(13);
	}

	@Test
	void part2_example() {
		// Given
		ScratchCards underTest = new ScratchCards(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(30);
	}

	@Test
	void part1_input() {
		// Given
		ScratchCards underTest = new ScratchCards(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(17803);
	}

	@Test
	void part2_input() {
		// Given
		ScratchCards underTest = new ScratchCards(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(5554894);
	}

	private static class ScratchCards {

		List<Card> scratchCards = new ArrayList<>();

		ScratchCards(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("Card +\\d+:|\\|");
				while (scanner.hasNext()) {
					scratchCards.add(new Card(scanner.next(), scanner.next()));
				}
			}
		}

		int part1() {
			return scratchCards.stream().mapToInt(Card::score).sum();
		}

		int part2() {
			for (int i = 0; i < scratchCards.size(); i++) {
				Card card = scratchCards.get(i);
				int matches = card.matches();
				for (int j = 1; j <= card.instances; j++) {
					for (int k = 1; k <= matches; k++) {
						if (i + k < scratchCards.size()) {
							scratchCards.get(i + k).addInstance();
						}
					}
				}
			}
			return scratchCards.stream().mapToInt(Card::getInstances).sum();
		}
	}

	private static class Card {

		private final Set<Integer> winningNumbers;
		private final Set<Integer> havingNumbers;
		private int instances = 1;

		Card(String winningNumberString, String havingNumbersString) {
			try (Scanner scanner = new Scanner(winningNumberString)) {
				winningNumbers = scanner.tokens().map(Integer::parseInt).collect(Collectors.toSet());
			}
			try (Scanner scanner = new Scanner(havingNumbersString)) {
				havingNumbers = scanner.tokens().map(Integer::parseInt).collect(Collectors.toSet());
			}
		}

		void addInstance() {
			instances += 1;
		}

		int getInstances() {
			return instances;
		}

		int score() {
			return havingNumbers.stream().filter(winningNumbers::contains).reduce(0, (r, __) -> r == 0 ? 1 : r * 2);
		}

		int matches() {
			return (int) havingNumbers.stream().filter(winningNumbers::contains).count();
		}
	}

}
