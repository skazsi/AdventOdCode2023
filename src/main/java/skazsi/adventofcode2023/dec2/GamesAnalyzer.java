package skazsi.adventofcode2023.dec2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class GamesAnalyzer {

	int analyzePart1(InputStream inputStream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			return reader.lines().map(Game::new).filter(Game::isUnderLimits).mapToInt(Game::getId).sum();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	int analyzePart2(InputStream inputStream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			return reader.lines().map(Game::new).mapToInt(Game::getPowerOfMinimalCubes).sum();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
