package packJunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculsTest {

	@BeforeEach
	void setUp() throws Exception {
		// Lancement prétest, instancier ici vos objets pour les tests
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMultiplier() {
		// Test de la méthode multiplier
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAdditionner() {
		fail("Not yet implemented"); // TODO
	}


	@Test
	void testDiviser() {
		// Technique pour vérifier la levée d'une exception en Java
		Assertions.assertThrows(NumberFormatException.class, () -> {
		    Integer.parseInt("plop");
		  });
	}
	@Test
	void testSoustraire() {
		fail("Not yet implemented"); // TODO
	}

}
