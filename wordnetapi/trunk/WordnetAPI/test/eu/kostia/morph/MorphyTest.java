package eu.kostia.morph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MorphyTest {

	Morphy morphy = new Morphy();

	@Test
	public void stemAxes() {
		String[] baseforms = morphy.stem("axes");
		//Expected: [axis, ax, axe]
		System.out.println(Arrays.toString(baseforms));
		assertEquals(3, baseforms.length);
		assertEquals("axis", baseforms[0]);
		assertEquals("ax", baseforms[1]);
		assertEquals("axe", baseforms[2]);
	}

	@Test
	public void stemAlumni() {

		String[] baseforms = morphy.stem("alumni");
		assertEquals(1, baseforms.length);
		assertEquals("alumnus", baseforms[0]);
	}

	@Test
	public void stemWent() {

		String[] baseforms = morphy.stem("went");
		assertEquals(1, baseforms.length);
		assertEquals("go", baseforms[0]);

	}

	@Test
	public void stemDog() {

		String[] baseforms = morphy.stem("dogs");
		assertEquals(1, baseforms.length);
		assertEquals("dog", baseforms[0]);

	}

	@Test
	public void stemChurches() {
		String[] baseforms = morphy.stem("churches");
		assertEquals(1, baseforms.length);
		assertEquals("church", baseforms[0]);

	}

	@Test
	public void stemAardwolves() {
		String[] baseforms = morphy.stem("aardwolves");
		assertEquals(1, baseforms.length);
		assertEquals("aardwolf", baseforms[0]);

	}

	@Test
	public void stemAbaci() {
		String[] baseforms = morphy.stem("abaci");
		assertEquals(1, baseforms.length);
		assertEquals("abacus", baseforms[0]);

	}

	@Test
	public void stemHardrock() {
		assertNull(morphy.stem("hardrock"));
	}

	@Test
	public void caseInsensitive() {
		String[] baseforms00 = morphy.stem("axes");
		String[] baseforms01 = morphy.stem("Axes");

		Assert.assertArrayEquals(baseforms00, baseforms01);
	}

}
