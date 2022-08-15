package lispvalidator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class LispValidatorTest {

	@Test
	void testSingleParenthesis() {
		String code = "()";
		assertTrue(LispValidator.isValidCodeStr(code));
	}

	@Test
	void testSingleNestedParenthesis() {
		String code = "( () )";
		assertTrue(LispValidator.isValidCodeStr(code));
	}

	@Test
	void testMultipleNestedParenthesis() {
		String code = "( (()) (()) (()) )";
		assertTrue(LispValidator.isValidCodeStr(code));
	}
	
	@Test
	void testCommentLine() {
		String code = "() ; single line comment";
		assertTrue(LispValidator.isValidCodeStr(code));
	}
	
	@Test
	void testString() {
		String code = """
				(
				"this is a string with parenthesis )"
				)
				""";
		assertTrue(LispValidator.isValidCodeStr(code));
	}

	@Test
	void testEscapedString() {
		String code = """
				(
				"this is a string with \\" escaped quote and parenthesis ("
				)
				""";
		assertTrue(LispValidator.isValidCodeStr(code));
	}
	
	@Test
	void testEmpty() {
		String code = "";
		assertFalse(LispValidator.isValidCodeStr(code));
	}

	@Test
	void testMismatchSimple() {
		String code = "( ( )";
		assertFalse(LispValidator.isValidCodeStr(code));
	}

	@Test
	void testMismatchComplex() {
		String code = """
				(
				((
				; comment line
				))
				)
				)
				""";
		assertFalse(LispValidator.isValidCodeStr(code));
	}

}
