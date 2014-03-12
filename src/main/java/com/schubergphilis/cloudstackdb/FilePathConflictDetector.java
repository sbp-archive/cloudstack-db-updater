package com.schubergphilis.cloudstackdb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class FilePathConflictDetector extends AbstractConflictDetector {

    protected final List<String> missingFiles;
    protected final List<String> movedFiles;

    public FilePathConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);

        missingFiles = getMissingFiles(currentVersion.getAbsolutePaths(), nextVersion.getAbsolutePaths());
        movedFiles = getMovedFiles(missingFiles, nextVersion.getFiles());
    }

    protected static List<String> getMissingFiles(Set<String> filesInCurrentVersion, Set<String> filesInNextVersion) {
        List<String> missingFiles = new ArrayList<>(filesInCurrentVersion);
        missingFiles.removeAll(filesInNextVersion);
        return missingFiles;
    }

    protected static List<String> getMovedFiles(List<String> missingFiles, Collection<File> filesInNextVersion) {
        List<String> movedFiles = new ArrayList<>(missingFiles.size());

        for (String missingFile : missingFiles) {
            File file = new File(missingFile);
            String fileName = file.getName();
            for (File fileInNextVersion : filesInNextVersion) {
                if (fileInNextVersion.getName().equals(fileName)) {
                    movedFiles.add(missingFile);
                }
            }
        }

        return movedFiles;
    }

}
