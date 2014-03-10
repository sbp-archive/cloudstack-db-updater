package com.schubergphilis.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private FileUtils() {
    }

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, false);
    }

    public static List<String> readLines(File file, boolean removeTraillingWhiteSpace) throws IOException {
        List<String> lines = org.apache.commons.io.FileUtils.readLines(file);
        if (!removeTraillingWhiteSpace) {
            return lines;
        } else {
            ArrayList<String> trimmedLines = new ArrayList<String>(lines.size());
            for (String line : lines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    trimmedLines.add(trimmedLine);
                }
            }
            return trimmedLines;
        }
    }

    public static File createTemporaryFile(String filenamePrefix) throws IOException {
        return File.createTempFile(filenamePrefix, Long.toString(System.currentTimeMillis()));
    }

    public static File createTemporaryFile() throws IOException {
        return createTemporaryFile("tempFile");
    }

    public static boolean filesHaveSameContentsNotConsideringTraillingWhiteSpace(File fileV1, File fileV2) throws IOException {
        boolean removeTraillingWhiteSpace = true;
        List<String> linesFileV1 = FileUtils.readLines(fileV1, removeTraillingWhiteSpace);
        List<String> linesFileV2 = FileUtils.readLines(fileV2, removeTraillingWhiteSpace);

        if (linesFileV1.size() != linesFileV2.size()) {
            return false;
        } else {
            return linesFileV1.equals(linesFileV2);
        }
    }

    public static void writeToFile(String contents, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(contents);
        fileWriter.close();
    }

}