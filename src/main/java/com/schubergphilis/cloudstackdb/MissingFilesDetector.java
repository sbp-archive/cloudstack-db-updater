package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class MissingFilesDetector extends FilePathBasedConflictDetector {

    private static final Logger log = Logger.getLogger(MissingFilesDetector.class);

    public MissingFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting missing files");
        List<Conflict> conflicts = new LinkedList<>();

        List<SourceCodeFile> filteredMissingFiles = getMissingFilesFilteringOutMovedFiles(missingFiles, movedFiles);
        if (!filteredMissingFiles.isEmpty()) {
            conflicts.add(new MissingFilesConflict(filteredMissingFiles));
        }

        return conflicts;
    }

    protected static List<SourceCodeFile> getMissingFilesFilteringOutMovedFiles(List<SourceCodeFile> missingFiles, List<SourceCodeFileChange> movedFiles) {
        ArrayList<SourceCodeFile> filteredMissingFiles = new ArrayList<>(missingFiles);

        for (SourceCodeFile missingFile : missingFiles) {
            for (SourceCodeFileChange movedFile : movedFiles) {
                if (missingFile.equals(movedFile.getOriginal())) {
                    filteredMissingFiles.remove(missingFile);
                    break;
                }
            }
        }

        return filteredMissingFiles;
    }
}
