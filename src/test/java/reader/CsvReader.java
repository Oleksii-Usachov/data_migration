package reader;

import model.StockTimestamp;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<StockTimestamp> readCsvFile(String fileName) throws Exception {
        List<StockTimestamp> stockTimestampList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {

            csvParser.getRecords().forEach(it -> {
                Long stockTimestamp = Long.parseLong(it.get(0));
                Long stockObservationDate = Long.parseLong(it.get(1));
                float stockPrice = Float.parseFloat(it.get(2));
                StockTimestamp st = new StockTimestamp(stockTimestamp, stockObservationDate, stockPrice);
                stockTimestampList.add(st);
            });
        } catch (Exception e) {
            throw new Exception("File can't be read");
        }

        return stockTimestampList;
    }
}
