package skazsi.adventofcode2023.dec2;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

class Game {

	private int id;
	private List<RevealedCubes> roundsOfRevealedCubes = new ArrayList<>();
	private int powerOfMinimalCubes = 1;

	Game(String gameText) {
		id = Integer.parseInt(gameText.substring(5, gameText.indexOf(":")));

		Stream.of(gameText.substring(gameText.indexOf(":") + 1).split(";")).map(String::trim)
				.forEach(revealedCubesText -> roundsOfRevealedCubes.add(new RevealedCubes(revealedCubesText)));

		roundsOfRevealedCubes.stream().mapToInt(RevealedCubes::getRed).filter(n -> n > 0).max().ifPresent(max -> powerOfMinimalCubes *= max);
		roundsOfRevealedCubes.stream().mapToInt(RevealedCubes::getGreen).filter(n -> n > 0).max().ifPresent(max -> powerOfMinimalCubes *= max);
		roundsOfRevealedCubes.stream().mapToInt(RevealedCubes::getBlue).filter(n -> n > 0).max().ifPresent(max -> powerOfMinimalCubes *= max);
	}

	int getId() {
		return id;
	}

	boolean isUnderLimits() {
		return !roundsOfRevealedCubes.stream().filter(RevealedCubes::isBeyondLimits).findAny().isPresent();
	}

	int getPowerOfMinimalCubes() {
		return powerOfMinimalCubes;
	}

	@Override
	public String toString() {
		StringJoiner stringJoiner = new StringJoiner(",", Game.class.getSimpleName() + "[", "]");
		stringJoiner.add("Id:" + id);
		roundsOfRevealedCubes.forEach(revealedCubes -> stringJoiner.add("" + revealedCubes));
		return stringJoiner.toString();
	}

	static class RevealedCubes {
		private int red;
		private int green;
		private int blue;

		private RevealedCubes(String revealedCubesText) {
			Stream.of(revealedCubesText.split(",")).map(String::trim).forEach(colorText -> {
				int quantity = Integer.parseInt(colorText.substring(0, colorText.indexOf(" ")));
				String color = colorText.substring(colorText.indexOf(" ") + 1);

				switch (color) {
				case "red":
					this.red = quantity;
					break;
				case "green":
					this.green = quantity;
					break;
				case "blue":
					this.blue = quantity;
					break;
				default:
					throw new IllegalArgumentException("Invalid text of revealed cubes: " + revealedCubesText);
				}
			});
			;
		}

		int getRed() {
			return red;
		}

		int getGreen() {
			return green;
		}

		int getBlue() {
			return blue;
		}

		private boolean isBeyondLimits() {
			return red > 12 || green > 13 || blue > 14;
		}

		@Override
		public String toString() {
			StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
			stringJoiner.add("red:" + red);
			stringJoiner.add("green:" + green);
			stringJoiner.add("blue:" + blue);
			return stringJoiner.toString();
		}
	}
}
