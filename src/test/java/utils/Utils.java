package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utils {

    private Utils() {
    }

    public static Long formatDateToUnix(String initialDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            Date date = sdf.parse(initialDate);
            return (date.getTime() / 1000L);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static String readFileContent(String filePath) throws Exception {
        try {
            return FileUtils.readFileToString(new File(filePath), Charset.defaultCharset());
        } catch (Exception e) {
            throw new Exception("File not found");
        }
    }

    public static List<String> getCommonFileNamesFromFolders(Path f1, Path f2) throws Exception {
        List<String> f1Files = getAllFilesFromDir(f1);
        List<String> f2Files = getAllFilesFromDir(f2);

        List<String> commonFileNames = new ArrayList<>(f1Files);
        commonFileNames.retainAll(f2Files);

        return commonFileNames;
    }

    public static List<String> getAllFilesFromDir(Path dir) throws Exception {
        try (Stream<Path> stream = Files.list(dir)) {
            return stream.filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(FilenameUtils::removeExtension)
                    .collect(Collectors.toList());
        }
    }
}
