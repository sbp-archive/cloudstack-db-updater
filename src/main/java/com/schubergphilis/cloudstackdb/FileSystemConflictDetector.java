package com.schubergphilis.cloudstackdb;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.schubergphilis.utils.FileUtils;

public class FileSystemConflictDetector implements ConflictDetector {

    private final List<File> previousVersion = new LinkedList<File>();
    private final List<File> currentVersion = new LinkedList<File>();

    @Override
    public List<Conflict> detect() {
        return null;
    }

    public void addToPreviousVersion(Collection<? extends String> filenames) {
        addFilesToList(previousVersion, filenames);
    }

    public void addToCurrentVersion(Collection<? extends String> filenames) {
        addFilesToList(currentVersion, filenames);
    }

    private static void addFilesToList(List<File> list, Collection<? extends String> filenames) {
        for (String filename : filenames) {
            list.add(new File(filename));
        }
    }

    public static boolean contentHasChanged(File fileV1, File fileV2) throws IOException {
        boolean removeTraillingWhiteSpace = true;
        List<String> linesFileV1 = FileUtils.readLines(fileV1, removeTraillingWhiteSpace);
        List<String> linesFileV2 = FileUtils.readLines(fileV2, removeTraillingWhiteSpace);

        if (linesFileV1.size() != linesFileV2.size()) {
            return removeTraillingWhiteSpace;
        } else {
            return !linesFileV1.equals(linesFileV2);
        }
    }

}
