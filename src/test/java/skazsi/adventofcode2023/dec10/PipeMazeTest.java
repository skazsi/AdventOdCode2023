package skazsi.adventofcode2023.dec10;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

public class PipeMazeTest {

	@Test
	void example1() throws Exception {
		// Given
		PipeMaze underTest = new PipeMaze(5, 5, getClass().getResourceAsStream("example1"));

		// When
		int stepsToFurthestPoint = underTest.goFurthest(Direction.East);

		// Then
		then(stepsToFurthestPoint).isEqualTo(4);
	}

	@Test
	void example2() throws Exception {
		// Given
		PipeMaze underTest = new PipeMaze(5, 5, getClass().getResourceAsStream("example2"));

		// When
		int stepsToFurthestPoint = underTest.goFurthest(Direction.East);

		// Then
		then(stepsToFurthestPoint).isEqualTo(8);
	}

	@Test
	void example3() throws Exception {
		// Given
		PipeMaze underTest = new PipeMaze(9, 11, getClass().getResourceAsStream("example3"));

		// When
		int stepsToFurthestPoint = underTest.goFurthest(Direction.East);
		int innerTilesCount = underTest.getInnerTilesCount();

		// Then
		then(stepsToFurthestPoint).isEqualTo(23);
		then(innerTilesCount).isEqualTo(4);
	}

	@Test
	void input() throws Exception {
		// Given
		PipeMaze underTest = new PipeMaze(140, 140, getClass().getResourceAsStream("input"));

		// When
		int stepsToFurthestPoint = underTest.goFurthest(Direction.North);
		int innerTilesCount = underTest.getInnerTilesCount();

		// Then
		then(stepsToFurthestPoint).isEqualTo(6951);
		then(innerTilesCount).isEqualTo(563);
	}

	private static class PipeMaze {

		private final int verticalSize;
		private final int horizontalSize;

		private Pipe[][] pipeMatrix;
		private Pipe[][] resultMatrix;

		private PipeMaze(int verticalSize, int horizontalSize, InputStream inputStream) throws Exception {
			this.verticalSize = verticalSize;
			this.horizontalSize = horizontalSize;

			pipeMatrix = new Pipe[verticalSize][horizontalSize];
			resultMatrix = new Pipe[verticalSize][horizontalSize];

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
					String line = reader.readLine();
					for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
						pipeMatrix[verticalPosition][horizontalPosition] = Pipe.fromSign(line.substring(horizontalPosition, horizontalPosition + 1));
						resultMatrix[verticalPosition][horizontalPosition] = Pipe.Empty;
					}
				}
			}

		}

		int goFurthest(Direction startingDirection) {
			Movement movement = getStartingMovement(startingDirection);
			int startingVerticalPosition = movement.verticalPosition;
			int startingHorizontalPosition = movement.horizontalPosition;

			System.out.println("Starting position: vertical: " + startingVerticalPosition + ", horizontal: " + startingHorizontalPosition);

			int steps = 0;
			do {
				resultMatrix[movement.verticalPosition][movement.horizontalPosition] = pipeMatrix[movement.verticalPosition][movement.horizontalPosition];

				movement = pipeMatrix[movement.verticalPosition][movement.horizontalPosition].move(movement);
				System.out.println("Moving " + movement.direction.name() + ", current position: vertical: " + movement.verticalPosition + ", horizontal: " + movement.horizontalPosition);
				steps++;

			} while (movement.verticalPosition != startingVerticalPosition || movement.horizontalPosition != startingHorizontalPosition);
			return steps / 2;
		}

		private Movement getStartingMovement(Direction startingDirection) {
			for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
				for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
					if (pipeMatrix[verticalPosition][horizontalPosition].sign.equals("S"))
						return new Movement(startingDirection, verticalPosition, horizontalPosition);
				}
			}
			throw new RuntimeException();
		}

		int getInnerTilesCount() {
			int count = 0;
			boolean inner = false;

			for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
				for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
					String sign = resultMatrix[verticalPosition][horizontalPosition].sign;
					switch (sign) {
					case "F":
						inner = !inner;
						break;
					case "7":
						inner = !inner;
						break;
					case "|":
						inner = !inner;
						break;
					case "S":
						inner = !inner;
						break;
					case ".":
						if (inner)
							count++;
						break;
					}
				}
			}
			return count;
		}

	}

	private enum Direction {
		North, South, East, West;
	}

	private static class Movement {
		private final Direction direction;
		private final int verticalPosition;
		private final int horizontalPosition;

		private Movement(Direction direction, int verticalPosition, int horizontalPosition) {
			this.direction = direction;
			this.verticalPosition = verticalPosition;
			this.horizontalPosition = horizontalPosition;
		}
	}

	private enum Pipe {

		NorthSouth("|") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case North:
					return new Movement(Direction.North, movement.verticalPosition - 1, movement.horizontalPosition);
				case South:
					return new Movement(Direction.South, movement.verticalPosition + 1, movement.horizontalPosition);
				default:
					throw new RuntimeException();
				}
			}
		},
		EastWest("-") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case East:
					return new Movement(Direction.East, movement.verticalPosition, movement.horizontalPosition + 1);
				case West:
					return new Movement(Direction.West, movement.verticalPosition, movement.horizontalPosition - 1);
				default:
					throw new RuntimeException();
				}
			}
		},
		NorthEast("L") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case South:
					return new Movement(Direction.East, movement.verticalPosition, movement.horizontalPosition + 1);
				case West:
					return new Movement(Direction.North, movement.verticalPosition - 1, movement.horizontalPosition);
				default:
					throw new RuntimeException();
				}
			}
		},
		NorthWest("J") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case South:
					return new Movement(Direction.West, movement.verticalPosition, movement.horizontalPosition - 1);
				case East:
					return new Movement(Direction.North, movement.verticalPosition - 1, movement.horizontalPosition);
				default:
					throw new RuntimeException();
				}
			}
		},
		SouthWest("7") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case North:
					return new Movement(Direction.West, movement.verticalPosition, movement.horizontalPosition - 1);
				case East:
					return new Movement(Direction.South, movement.verticalPosition + 1, movement.horizontalPosition);
				default:
					throw new RuntimeException();
				}
			}
		},
		SouthEast("F") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case North:
					return new Movement(Direction.East, movement.verticalPosition, movement.horizontalPosition + 1);
				case West:
					return new Movement(Direction.South, movement.verticalPosition + 1, movement.horizontalPosition);
				default:
					throw new RuntimeException();
				}
			}
		},
		Start("S") {
			@Override
			Movement move(Movement movement) {
				switch (movement.direction) {
				case North:
					return new Movement(Direction.North, movement.verticalPosition - 1, movement.horizontalPosition);
				case South:
					return new Movement(Direction.South, movement.verticalPosition + 1, movement.horizontalPosition);
				case East:
					return new Movement(Direction.East, movement.verticalPosition, movement.horizontalPosition + 1);
				case West:
					return new Movement(Direction.South, movement.verticalPosition, movement.horizontalPosition - 1);
				default:
					throw new RuntimeException();
				}
			}
		},
		Empty(".") {
			@Override
			Movement move(Movement movement) {
				throw new UnsupportedOperationException();
			}
		};

		private String sign;

		Pipe(String sign) {
			this.sign = sign;
		}

		static Pipe fromSign(String sign) {
			for (Pipe pipe : Pipe.values()) {
				if (pipe.sign.equals(sign)) {
					return pipe;
				}
			}
			return null;
		}

		abstract Movement move(Movement movement);
	}
}
