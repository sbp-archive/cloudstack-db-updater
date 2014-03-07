package com.schubergphilis.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

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

}