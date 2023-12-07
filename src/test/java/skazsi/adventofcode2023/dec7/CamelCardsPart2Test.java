package skazsi.adventofcode2023.dec7;

import static org.assertj.core.api.BDDAssertions.then;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_2;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_3;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_9;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_A;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_J;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_K;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_Q;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Card.CARD_T;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.FIVE_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.FOUR_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.FULL_HOUSE;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.HIGH_CARD;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.ONE_PAIR;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.THREE_OF_A_KIND;
import static skazsi.adventofcode2023.dec7.CamelCardsPart2Test.Type.TWO_PAIRS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

public class CamelCardsPart2Test {

	@Test
	void card_is_comparable() {
		// When & Then
		then(CARD_J.compareTo(CARD_2)).isNegative();
		then(CARD_2.compareTo(CARD_2)).isZero();
		then(CARD_2.compareTo(CARD_3)).isNegative();
		then(CARD_9.compareTo(CARD_T)).isNegative();
		then(CARD_Q.compareTo(CARD_K)).isNegative();
		then(CARD_K.compareTo(CARD_A)).isNegative();
	}

	@Test
	void card_isJoker_is_true() {
		// When & Then
		then(CARD_J.isJoker()).isTrue();
	}

	@ParameterizedTest
	@EnumSource(value = Card.class, mode = Mode.EXCLUDE, names = "CARD_J")
	void card_isJoker_is_false(Card card) {
		// When & Then
		then(card.isJoker()).isFalse();
	}

	@Test
	void type() {
		// When & Then
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_2, CARD_2))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_2, CARD_J))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_J, CARD_J))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_J, CARD_J, CARD_J))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_J, CARD_J, CARD_J, CARD_J))).isEqualTo(FIVE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_2, CARD_A))).isEqualTo(FOUR_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_J, CARD_A))).isEqualTo(FOUR_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_J, CARD_J, CARD_A))).isEqualTo(FOUR_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_J, CARD_J, CARD_J, CARD_A))).isEqualTo(FOUR_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_3, CARD_3))).isEqualTo(FULL_HOUSE);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_J, CARD_3, CARD_3))).isEqualTo(FULL_HOUSE);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_2, CARD_A, CARD_Q))).isEqualTo(THREE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_J, CARD_A, CARD_Q))).isEqualTo(THREE_OF_A_KIND);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_3, CARD_3, CARD_K))).isEqualTo(TWO_PAIRS);
		then(Type.getType(List.of(CARD_2, CARD_2, CARD_A, CARD_Q, CARD_K))).isEqualTo(ONE_PAIR);
		then(Type.getType(List.of(CARD_2, CARD_A, CARD_Q, CARD_J, CARD_T))).isEqualTo(ONE_PAIR);
		then(Type.getType(List.of(CARD_2, CARD_A, CARD_Q, CARD_3, CARD_T))).isEqualTo(HIGH_CARD);
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
		then(hands).containsExactly(hand23456, hand55Q77, handAAQ77, handJJJJJ, hand22222);
	}

	@Test
	void part2_example() {
		// Given
		CamelCards underTest = new CamelCards(getClass().getResourceAsStream("example"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(5905);
	}

	@Test
	void part2_input() {
		// Given
		CamelCards underTest = new CamelCards(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(248029057);
	}

	private static class CamelCards {

		private List<Hand> hands;

		CamelCards(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n|\r|\n");
				hands = scanner.tokens().map(Hand::new).collect(Collectors.toList());
			}
		}

		int part2() {
			Collections.sort(hands);
			int sum = 0;
			for (int i = 0; i < hands.size(); i++) {
				System.out.println(hands.get(i) + "\t" + hands.get(i).type + "\t" + hands.get(i).bid);
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
		CARD_J, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_T, CARD_Q, CARD_K, CARD_A;

		boolean isJoker() {
			return this == CARD_J;
		}

		boolean isNotJoker() {
			return !isJoker();
		}
	}

	enum Type {
		HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

		static Type getType(List<Card> cards) {
			int joker = (int) cards.stream().filter(Card::isJoker).count();

			Map<String, List<Card>> grouppedCards = cards.stream().filter(Card::isNotJoker).collect(Collectors.groupingBy(Card::name));

			if (grouppedCards.values().stream().mapToInt(List::size).max().orElse(0) + joker == 5) {
				return FIVE_OF_A_KIND;
			} else if (grouppedCards.values().stream().mapToInt(List::size).max().orElseThrow() + joker == 4) {
				return FOUR_OF_A_KIND;
			} else if (grouppedCards.values().stream().mapToInt(List::size).min().orElseThrow() == 2) {
				return FULL_HOUSE;
			} else if (grouppedCards.values().stream().mapToInt(List::size).max().orElseThrow() + joker == 3) {
				return THREE_OF_A_KIND;
			} else if (grouppedCards.keySet().size() == 3) {
				return TWO_PAIRS;
			} else if (grouppedCards.values().stream().mapToInt(List::size).max().orElseThrow() == 2) {
				return ONE_PAIR;
			} else if (joker == 1) {
				return ONE_PAIR;
			} else
				return HIGH_CARD;
		}
	}
}
