package skazsi.adventofcode2023.dec1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.ToIntFunction;

class CalibrationDocumentRecoverer {

	private final ToIntFunction<String> parser;

	public CalibrationDocumentRecoverer(ToIntFunction<String> parser) {
		this.parser = parser;
	}
	
	int recover(InputStream inputStream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			return reader.lines().mapToInt(parser).sum();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
