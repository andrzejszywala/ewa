package pl.ewa.business.exchange.control;

import org.junit.Assert;
import org.junit.Test;

public class NbpDirTest {

	@Test
	public void test() {
		Assert.assertTrue(new NbpDir(2010).iterator().hasNext());
	}
}
