package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CapitalizerTest {
	@Test
	public void capitalizeLowerCaseWord() {
		assertEquals("Word", capitalize("word"));
	}

	@Test
	public void capitalizeAlreadyCapitalizedWord() {
		assertEquals("Word", capitalize("Word"));
	}

	@Test
	public void capitalizeUpperCaseWord() {
		assertEquals("WORD", capitalize("WORD"));
	}

	@Test
	public void capitalizeCamelCased() {
		assertEquals("WordCamelCased", capitalize("wordCamelCased"));
	}

	@Test
	public void capitalizeOneLetter() {
		assertEquals("W", capitalize("w"));
	}

	@Test
	public void capitalizeEmptyString() {
		assertEquals("", capitalize(""));
	}

	@Test
	public void capitalizeNullToEmptyString() {
		assertEquals("", capitalize(null));
	}
}
