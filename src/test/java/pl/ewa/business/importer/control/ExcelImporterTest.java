package pl.ewa.business.importer.control;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import pl.ewa.business.webtrader.entity.Account;

public class ExcelImporterTest {

	@Test
	public void importFromExcelFile() throws FileNotFoundException, IOException {
		try (InputStream is = new FileInputStream(new File("./src/test/resources/eToroAccountStatement.xlsx").getAbsolutePath())) {
			Account accountStatement = new ExcelImporter().execute(is);
			
			assertEquals("Andrzej Szywala", accountStatement.getName());
			assertEquals("AndrzejSzywala", accountStatement.getUserName());
			assertEquals("USD", accountStatement.getCurrency());
			assertEquals(29, accountStatement.getClosedPositions().size());
			assertEquals(76, accountStatement.getTransactions().size());
		}
	}

}