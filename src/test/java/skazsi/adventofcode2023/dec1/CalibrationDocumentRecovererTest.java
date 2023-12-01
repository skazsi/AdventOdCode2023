package skazsi.adventofcode2023.dec1;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CalibrationDocumentRecovererTest {

	private NumberParser numberParser = new NumberParser();

	@Test
	void parseAsPart1() {
		// Given
		InputStream inputStream = CalibrationDocumentRecovererTest.class.getResourceAsStream("Dec1Input");
		CalibrationDocumentRecoverer underTest = new CalibrationDocumentRecoverer(text -> numberParser.parseAsPart1(text));

		// When
		int result = underTest.recover(inputStream);

		// Then
		then(result).isEqualTo(54940);
	}

	@Test
	void parseAsPart2() {
		// Given
		InputStream inputStream = CalibrationDocumentRecovererTest.class.getResourceAsStream("Dec1Input");
		CalibrationDocumentRecoverer underTest = new CalibrationDocumentRecoverer(text -> numberParser.parseAsPart2(text));

		// When
		int result = underTest.recover(inputStream);

		// Then
		then(result).isEqualTo(54208);
	}
}
