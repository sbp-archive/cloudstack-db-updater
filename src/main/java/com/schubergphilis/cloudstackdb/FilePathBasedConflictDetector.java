package com.schubergphilis.cloudstackdb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FilePathBasedConflictDetector extends AbstractConflictDetector {

    protected final List<SourceCodeFile> missingFiles;
    protected final List<SourceCodeFileChange> movedFiles;

    public FilePathBasedConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);

        missingFiles = getMissingFiles(currentVersion.getFiles(), nextVersion.getFiles());
        movedFiles = getMovedFiles(missingFiles, nextVersion.getFiles());
    }

    protected static List<SourceCodeFile> getMissingFiles(Collection<SourceCodeFile> filesInCurrentVersion, Collection<SourceCodeFile> filesInNextVersion) {
        List<SourceCodeFile> missingFiles = new ArrayList<>(filesInCurrentVersion);
        missingFiles.removeAll(filesInNextVersion);
        return missingFiles;
    }

    protected static List<SourceCodeFileChange> getMovedFiles(Collection<SourceCodeFile> missingFiles, Collection<SourceCodeFile> filesInNextVersion) {
        List<SourceCodeFileChange> movedFiles = new ArrayList<>(missingFiles.size());

        for (SourceCodeFile missingFile : missingFiles) {
            File file = missingFile.getFile();
            String fileName = file.getName();
            for (SourceCodeFile fileInNextVersion : filesInNextVersion) {
                if (fileInNextVersion.getFile().getName().equals(fileName)) {
                    movedFiles.add(new SourceCodeFileChange(missingFile, fileInNextVersion));
                }
            }
        }

        return movedFiles;
    }
}
