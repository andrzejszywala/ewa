package pl.ewa.business.exchange.control;

import org.junit.Assert;
import org.junit.Test;

public class NbpUsdExchangeTest {

	@Test
	public void test() {
		Assert.assertTrue(new NbpUsdExchange(2015, 2015).iterator().hasNext());
	}
}
