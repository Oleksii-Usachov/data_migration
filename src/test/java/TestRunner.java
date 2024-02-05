import lombok.SneakyThrows;
import model.StockTimestamp;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import reader.CsvReader;
import reader.JsonReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static utils.Utils.getAllFilesFromDir;
import static utils.Utils.getCommonFileNamesFromFolders;

public class TestRunner {

    private static final Logger logger = LogManager.getLogger(TestRunner.class);

    String f1 = "C:\\TemporalData\\v1\\";
    String f2 = "C:\\TemporalData\\v2\\";
    String json = ".json";
    String csv = ".csv";

    @Test
    @SneakyThrows
    public void testCommonFiles() {
        List<String> corruptedFiles = new ArrayList<>();
        List<String> matchedFilesFiles = new ArrayList<>();//
        List<String> commonFiles = getCommonFileNamesFromFolders(Paths.get(f1), Paths.get(f2));

        commonFiles.forEach(it -> {
            List<StockTimestamp> jsonStockTimestamps = null;
            List<StockTimestamp> csvStockTimestamps = null;
            try {
                jsonStockTimestamps = JsonReader.readFromFile(f1.concat(it.concat(json)));
                csvStockTimestamps = CsvReader.readCsvFile(f2.concat(it.concat(csv)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Comparator<StockTimestamp> comparator = Comparator.comparing(StockTimestamp::getTimestamp);
            comparator = comparator.thenComparing(StockTimestamp::getObservationDate);

            jsonStockTimestamps.sort(comparator);
            try {
                assertEquals(jsonStockTimestamps, csvStockTimestamps);
                matchedFilesFiles.add(it);//
                logger.info(it + " files are Matched");
            } catch (AssertionError e) {
                corruptedFiles.add(it);
                logger.info(it + " files are Mismatched");
            }
        });
        logger.error("Amount of matched files: " + matchedFilesFiles);
        logger.error("Amount of unmatched files: " + corruptedFiles);
        assertEquals("The amount of corrupted files should be 0", 0, corruptedFiles.size());
    }

    @Test
    @SneakyThrows
    public void checkFileAccordance() {
        Path path1 = Paths.get(f1);
        Path path2 = Paths.get(f2);

        List<String> v1Files = getAllFilesFromDir(path1);
        List<String> v2Files = getAllFilesFromDir(path2);

        List<String> differences1 = new ArrayList<>((CollectionUtils.removeAll(v1Files, v2Files)));
        List<String> differences2 = new ArrayList<>((CollectionUtils.removeAll(v2Files, v1Files)));
        if (differences1.size() > 0)
            logger.error("This files are not present in v2 folder: " + differences1);
        else if (differences2.size() > 0)
            logger.error("This files are not present in v1 folder: " + differences2);

        assertThat(v1Files).isEqualTo(v2Files);
    }
}
