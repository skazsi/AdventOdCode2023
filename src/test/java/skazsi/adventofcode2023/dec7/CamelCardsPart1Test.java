package skazsi.adventofcode2023.dec7;

import static org.assertj.core.api.BDDAssertions.then;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_2;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_3;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_9;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_A;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_J;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_K;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_Q;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Card.CARD_T;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.FIVE_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.FOUR_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.HIGH_CARD;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.ONE_PAIR;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.THREE_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart1Test.Type.TWO_PAIRS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class CamelCardsPart1Test {

	@Test
	void card_is_comparable() {
		// When & Then
		then(CARD_2.compareTo(CARD_2)).isZero();
		then(CARD_2.compareTo(CARD_3)).isNegative();
		then(CARD_9.compareTo(CARD_T)).isNegative();
		then(CARD_T.compareTo(CARD_J)).isNegative();
		then(CARD_J.compareTo(CARD_Q)).isNegative();
		then(CARD_Q.compareTo(CARD_K)).isNegative();
		then(CARD_K.compareTo(CARD_A)).isNegative();
	}

	@Test
	void type() {
		// When & Then
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_2, CARD_2))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_2, CARD_A))).isEqualTo(FOUR_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_A, CARD_J))).isEqualTo(THREE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_A, CARD_A, CARD_J))).isEqualTo(TWO_PAIRS);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_A, CARD_Q, CARD_J))).isEqualTo(ONE_PAIR);
		then(Type.getType(List.of(CARD_2, CARD_A, CARD_Q, CARD_J, CARD_T))).isEqualTo(HIGH_CARD);
	}

	@Test
	void hand_comparable() {
		// Given
		Hand hand22222 = new Hand("22222 0");
		Hand hand55Q77 = new Hand("55Q77 0");
		Hand handJJJJJ = new Hand("JJJJJ 0");
		Hand hand23456 = new Hand("23456 0");
		Hand handAAQ77 = new Hand("AAQ77 0");

		List<Hand> hands = new ArrayList<>();
		hands.add(hand22222);
		hands.add(hand55Q77);
		hands.add(handJJJJJ);
		hands.add(hand23456);
		hands.add(handAAQ77);

		// When
		Collections.sort(hands);

		// When
		then(hands).containsExactly(hand23456, hand55Q77, handAAQ77, hand22222, handJJJJJ);
	}

	@Test
	void part1_example() {
		// Given
		CamelCards underTest = new CamelCards(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(6440);
	}

	@Test
	void part1_input() {
		// Given
		CamelCards underTest = new CamelCards(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(249748283);
	}

	private static class CamelCards {

		private List<Hand> hands;

		CamelCards(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n|\r|\n");
				hands = scanner.tokens().map(Hand::new).collect(Collectors.toList());
			}
		}

		int part1() {
			Collections.sort(hands);
			int sum = 0;
			for (int i = 0; i < hands.size(); i++) {
				System.out.println(hands.get(i) + "\t" + hands.get(i).bid);
				sum = sum + (i + 1) * hands.get(i).bid;
			}
			return sum;
		}
	}

	private static class Hand implements Comparable<Hand> {

		private List<Card> cards;
		private Type type;
		private int bid;

		Hand(String text) {
			cards = text.substring(0, 5).chars().mapToObj(Character::toChars).map(chars -> "CARD_" + chars[0]).map(Card::valueOf).collect(Collectors.toList());
			type = Type.getType(cards);
			bid = Integer.parseInt(text.substring(6));
		}

		@Override
		public int compareTo(Hand o) {
			if (type != o.type) {
				return type.compareTo(o.type);
			}
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i) != o.cards.get(i)) {
					return cards.get(i).compareTo(o.cards.get(i));
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			return cards.stream().map(Card::name).map(s -> s.substring(5)).reduce("", String::concat);
		}
	}

	enum Card {
		CARD_2, CARD_3, CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_T, CARD_J, CARD_Q, CARD_K, CARD_A;
	}

	enum Type {
		HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

		static Type getType(List<Card> cards) {
			if (cards.stream().collect(Collectors.groupingBy(Card::name)).keySet().size() == 1) {
				return FIVE_OF_A_KIND;
			} else if (cards.stream().collect(Collectors.groupingBy(Card::name)).entrySet().stream().filter(e -> e.getValue().size() == 4).findAny().isPresent()) {
				return FOUR_OF_A_KIND;
			} else if (cards.stream().collect(Collectors.groupingBy(Card::name)).keySet().size() == 2) {
				return FULL_HOUSE;
			} else if (cards.stream().collect(Collectors.groupingBy(Card::name)).entrySet().stream().filter(e -> e.getValue().size() == 3).findAny().isPresent()) {
				return THREE_OF_A_KIND;
			} else if (cards.stream().collect(Collectors.groupingBy(Card::name)).entrySet().stream().filter(e -> e.getValue().size() == 2).count() == 2) {
				return TWO_PAIRS;
			} else if (cards.stream().collect(Collectors.groupingBy(Card::name)).entrySet().stream().filter(e -> e.getValue().size() == 2).count() == 1) {
				return ONE_PAIR;
			} else
				return HIGH_CARD;
		}
	}
}
