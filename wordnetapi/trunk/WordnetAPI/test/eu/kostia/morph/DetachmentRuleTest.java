package eu.kostia.morph;

import static eu.kostia.entity.PartOfSpeech.*;
import static org.junit.Assert.*;

import org.junit.*;

public class DetachmentRuleTest {

	@Test
	public void testApply00() {
		DetachmentRule rule = new DetachmentRule(NOUN, "ies", "y");
		assertEquals(null, rule.apply("pretty"));
		assertEquals("multiply", rule.apply("multiplies"));
	}

	@Test
	public void testApply01() {
		DetachmentRule rule = new DetachmentRule(NOUN, "ches", "ch");
		assertEquals(null, rule.apply("many"));
		assertEquals("church", rule.apply("churches"));
	}

}
