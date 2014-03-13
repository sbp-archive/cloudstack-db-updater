package com.schubergphilis.cloudstackdb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class FilePathConflictDetector extends AbstractConflictDetector {

    protected final List<String> missingFiles;
    protected final Map<String, String> movedFiles;

    public FilePathConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);

        missingFiles = getMissingFiles(currentVersion.getPathsRelativeToSourceRoot(), nextVersion.getPathsRelativeToSourceRoot());
        movedFiles = getMovedFiles(missingFiles, nextVersion.getFiles());
    }

    protected static List<String> getMissingFiles(Set<String> filesInCurrentVersion, Set<String> filesInNextVersion) {
        List<String> missingFiles = new ArrayList<>(filesInCurrentVersion);
        missingFiles.removeAll(filesInNextVersion);
        return missingFiles;
    }

    protected static Map<String, String> getMovedFiles(List<String> missingFiles, Collection<SourceCodeFile> filesInNextVersion) {
        Map<String, String> movedFiles = new HashMap<>(missingFiles.size());

        for (String missingFile : missingFiles) {
            File file = new File(missingFile);
            String fileName = file.getName();
            for (SourceCodeFile fileInNextVersion : filesInNextVersion) {
                if (fileInNextVersion.getFile().getName().equals(fileName)) {
                    movedFiles.put(missingFile, fileInNextVersion.getPathRelativeToSourceRoot());
                }
            }
        }

        return movedFiles;
    }
}
