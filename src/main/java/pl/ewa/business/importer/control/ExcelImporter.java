package pl.ewa.business.importer.control;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.ewa.business.position.entity.Position;
import pl.ewa.business.webtrader.entity.Account;
import pl.ewa.business.webtrader.entity.Statement;
import pl.ewa.business.webtrader.entity.Transaction;
import pl.ewa.business.webtrader.entity.TransactionPK;

public class ExcelImporter implements EtoroImporter {

	public Account execute(InputStream is) throws IOException {
		try (XSSFWorkbook etoroWorkBook = new XSSFWorkbook(is)) {
			return buildAccount(etoroWorkBook.getSheetAt(0), etoroWorkBook.getSheetAt(1), etoroWorkBook.getSheetAt(2));
		}
	}

	private Account buildAccount(XSSFSheet accountDetailsSheet, XSSFSheet closedPositionsSheet,
			XSSFSheet transactionsReportSheet) {
		Account account = buildDetails(accountDetailsSheet);
		account.setStatements(buildStatement(accountDetailsSheet));
		account.setTransactions(buildTransactions(transactionsReportSheet, account));
		account.setClosedPositions(buildClosedPositions(closedPositionsSheet, account));
		return account;
	}

	private Collection<Statement> buildStatement(XSSFSheet accountDetailsSheet) {
		Collection<Statement> statements = new ArrayList<>();
		Statement statement = new Statement();
		Iterator<Row> rowIterator = accountDetailsSheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell nameCell = cellIterator.next();
			Cell valueCell = null;
			if (cellIterator.hasNext()) {
				valueCell = cellIterator.next();
			}

			switch (nameCell.getStringCellValue()) {
			case "Start Date":
				statement.setStartDate(asDateTime(valueCell));
				break;
			case "End Date":
				statement.setEndDate(asDateTime(valueCell));
				break;
			case "Beginning Realized Equity":
				statement.setBeginningRealizedEquity(asBigDecimal(valueCell));
				break;
			case "Deposits":
				statement.setDeposits(asBigDecimal(valueCell));
				break;
			case "Refunds":
				statement.setRefunds(asBigDecimal(valueCell));
				break;
			case "Bonuses":
				statement.setBonuses(asBigDecimal(valueCell));
				break;
			case "Adjustments":
				statement.setAdjustments(asBigDecimal(valueCell));
				break;
			case "Trade Profit Or Loss":
				statement.setTradeProfitOrLoss(asBigDecimal(valueCell));
				break;
			case "Rollover Fees":
				statement.setRolloverFees(asBigDecimal(valueCell));
				break;
			case "Withdrawals":
				statement.setWithdrawals(asBigDecimal(valueCell));
				break;
			case "Withdrawal Fees":
				statement.setWithdrawalFees(asBigDecimal(valueCell));
				break;
			case "Ending Realized Equity":
				statement.setEndingRealizedEquity(asBigDecimal(valueCell));
				break;
			default:
				break;
			}
		}
		statements.add(statement);
		return statements;
	}

	private Collection<Transaction> buildTransactions(XSSFSheet transactionsReportSheet, Account account) {
		Collection<Transaction> transactions = new ArrayList<>();
		Iterator<Row> rowIterator = transactionsReportSheet.iterator();
		// skip header
		skipRow(rowIterator);
		while (rowIterator.hasNext()) {
			Transaction transaction = buildTransaction(rowIterator, account);
			if (!transactions.stream().anyMatch(t -> new TransactionPK(t.getDate(), t.getType(), t.getPosition()).equals(
					new TransactionPK(transaction.getDate(), transaction.getType(), transaction.getPosition())))) {
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	private Transaction buildTransaction(Iterator<Row> rowIterator, Account account) {
		Transaction transaction = new Transaction();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
		transaction.setDate(asDateTime(cellIterator.next()));
		transaction.setAccountBalance(asBigDecimal(cellIterator.next()));
		transaction.setType(cellIterator.next().getStringCellValue());
		transaction.setDetails(cellIterator.next().getStringCellValue());
		transaction.setPosition(asLong(cellIterator.next()));
		transaction.setAmount(asBigDecimal(cellIterator.next()));
		transaction.setRealizedEquityChange(asBigDecimal(cellIterator.next()));
		transaction.setRealizedEquity(asBigDecimal(cellIterator.next()));
		transaction.setNwa(asBigDecimal(cellIterator.next()));
		transaction.setAccount(account);
		return transaction;
	}

	private Long asLong(Cell cell) {
		return cell.getStringCellValue() == null || cell.getStringCellValue().isEmpty() ? 0L : Long.parseLong(cell.getStringCellValue());
	}

	private BigDecimal asBigDecimal(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue() == null || cell.getStringCellValue().trim().isEmpty() ? null
					: new BigDecimal(cell.getStringCellValue().replace(",", "."));
		case Cell.CELL_TYPE_NUMERIC:
			return new BigDecimal((cell.getNumericCellValue() + "").replace(",", "."));
		default:
			return null;
		}
	}

	private Date asDate(Cell cell) {
		if (cell == null) {
			return null;
		}
		try {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(cell.getStringCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getDateCellValue();
			default:
				return null;
			}

		} catch (ParseException e) {
			return null;
		}
	}
	
	private Date asDateTime(Cell cell) {
		if (cell == null) {
			return null;
		}
		try {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cell.getStringCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getDateCellValue();
			default:
				return null;
			}

		} catch (ParseException e) {
			return null;
		}
	}
	
	private void skipRow(Iterator<Row> rowIterator) {
		if (rowIterator.hasNext()) {
			rowIterator.next();
		}
	}

	private Collection<Position> buildClosedPositions(XSSFSheet closedPositionsSheet, Account account) {
		Collection<Position> closedPositions = new ArrayList<>();
		Iterator<Row> rowIterator = closedPositionsSheet.iterator();
		skipRow(rowIterator);
		while (rowIterator.hasNext()) {
			Position closedPosition = new Position();
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			closedPosition.setId(asLong(cellIterator.next()));
			closedPosition.setAction(cellIterator.next().getStringCellValue());
			closedPosition.setCopyTraderName(cellIterator.next().getStringCellValue());
			closedPosition.setAmount(asBigDecimal(cellIterator.next()));
			closedPosition.setUnits(asBigDecimal(cellIterator.next()));
			closedPosition.setOpenRate(asBigDecimal(cellIterator.next()));
			closedPosition.setCloseRate(asBigDecimal(cellIterator.next()));
			closedPosition.setSpread(asBigDecimal(cellIterator.next()));
			closedPosition.setProfit(asBigDecimal(cellIterator.next()));
			closedPosition.setOpenDate(asDate(cellIterator.next()));
			closedPosition.setCloseDate(asDate(cellIterator.next()));
			closedPosition.setTakeProfitRate(asBigDecimal(cellIterator.next()));
			closedPosition.setStopLossRate(asBigDecimal(cellIterator.next()));
			closedPosition.setRolloverFeesAndDividends(asBigDecimal(cellIterator.next()));
			closedPosition.setAccount(account);
			closedPositions.add(closedPosition);
		}
		return closedPositions;
	}

	private Account buildDetails(XSSFSheet accountDetailsSheet) {
		Account account = new Account();
		Iterator<Row> rowIterator = accountDetailsSheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell nameCell = cellIterator.next();
			Cell valueCell = null;
			if (cellIterator.hasNext()) {
				valueCell = cellIterator.next();
			}

			switch (nameCell.getStringCellValue()) {
			case "Name":
				account.setName(valueCell.getStringCellValue());
				break;
			case "Username":
				account.setUserName(valueCell.getStringCellValue());
				break;
			case "Currency":
				account.setCurrency(valueCell.getStringCellValue());
				break;
			default:
				break;
			}
		}
		return account;
	}
}
