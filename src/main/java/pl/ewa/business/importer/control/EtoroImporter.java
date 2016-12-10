package pl.ewa.business.importer.control;

import java.io.IOException;
import java.io.InputStream;

import pl.ewa.business.webtrader.entity.Account;

public interface EtoroImporter {
	Account execute(InputStream is) throws IOException;
}
