package skazsi.adventofcode2023.dec3;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class EngineSchematic {

	private final int verticalSize;
	private final int horizontalSize;

	private static class Symbol {
		private char character;

		private Symbol(char character) {
			this.character = character;
		}
	}

	private static class Number {
		private int number;
	}

	private Object[][] charMatrix;

	public EngineSchematic(String input) {
		this.verticalSize = input.lines().findFirst().get().length();
		this.horizontalSize = (int) input.lines().count();

		this.charMatrix = new Object[horizontalSize][verticalSize];
		initCharMatrix(input);
	}

	private void initCharMatrix(String input) {
		int verticalPosition = 0;
		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				for (int horizontalPosition = 0; horizontalPosition < line.length(); horizontalPosition++) {
					char character = line.charAt(horizontalPosition);
					if (character == '.') {
						continue;
					}
					if (!Character.isDigit(character)) {
						charMatrix[verticalPosition][horizontalPosition] = new Symbol(character);
						continue;
					}

					Number number = new Number();
					charMatrix[verticalPosition][horizontalPosition] = number;
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(character);

					while (horizontalPosition < line.length() - 1 && Character.isDigit(line.charAt(horizontalPosition + 1))) {
						++horizontalPosition;
						charMatrix[verticalPosition][horizontalPosition] = number;
						stringBuilder.append(line.charAt(horizontalPosition));
					}
					number.number = Integer.parseInt(stringBuilder.toString());
				}

				++verticalPosition;
			}
		}
	}

	int part1() {
		int result = 0;
		for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
			for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
				if (charMatrix[verticalPosition][horizontalPosition] instanceof Symbol) {
					Set<Number> numbers = setOfNumbersForSinglePosition(verticalPosition, horizontalPosition);
					result += numbers.stream().mapToInt(number -> number.number).sum();
				}
			}
		}
		return result;
	}

	int part2() {
		int result = 0;
		for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
			for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
				if (charMatrix[verticalPosition][horizontalPosition] instanceof Symbol) {
					Symbol symbol = (Symbol) charMatrix[verticalPosition][horizontalPosition];
					if (symbol.character == '*') {
						Set<Number> numbers = setOfNumbersForSinglePosition(verticalPosition, horizontalPosition);
						if (numbers.size() != 2) {
							continue;
						}
						result += numbers.stream().mapToInt(number -> number.number).reduce(1, (temp, number) -> temp * number);
					}
				}
			}
		}
		return result;
	}

	private Set<Number> setOfNumbersForSinglePosition(int verticalPosition, int horizontalPosition) {
		Set<Number> numbers = new HashSet<Number>();

		addNumberSafely(numbers, max(verticalPosition - 1, 0), max(horizontalPosition - 1, 0));
		addNumberSafely(numbers, max(verticalPosition - 1, 0), horizontalPosition);
		addNumberSafely(numbers, max(verticalPosition - 1, 0), min(horizontalPosition + 1, verticalSize));

		addNumberSafely(numbers, verticalPosition, max(horizontalPosition - 1, 0));
		addNumberSafely(numbers, verticalPosition, horizontalPosition);
		addNumberSafely(numbers, verticalPosition, min(horizontalPosition + 1, verticalSize));

		addNumberSafely(numbers, min(verticalPosition + 1, horizontalSize), max(horizontalPosition - 1, 0));
		addNumberSafely(numbers, min(verticalPosition + 1, horizontalSize), horizontalPosition);
		addNumberSafely(numbers, min(verticalPosition + 1, horizontalSize), min(horizontalPosition + 1, verticalSize));

		return numbers;
	}

	private void addNumberSafely(Set<Number> numbers, int verticalPosition, int horizontalPosition) {
		Object object = charMatrix[verticalPosition][horizontalPosition];
		if (object != null && object instanceof Number) {
			Number number = (Number) object;
			numbers.add(number);
		}
	}
}
