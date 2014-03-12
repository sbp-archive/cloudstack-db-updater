package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class MissingFilesDetector extends FilePathConflictDetector {

    private static final Logger log = Logger.getLogger(MissingFilesDetector.class);

    public MissingFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting missing files");
        List<Conflict> conflicts = new LinkedList<>();

        List<String> missingFiles = getMissingFilesFilteringOutMovedFiles();
        if (!missingFiles.isEmpty()) {
            conflicts.add(new MissingFilesConflict(missingFiles));
        }

        return conflicts;
    }

    protected List<String> getMissingFilesFilteringOutMovedFiles() {
        List<String> missingFiles = new ArrayList<>(this.missingFiles);
        missingFiles.removeAll(this.movedFiles);
        return missingFiles;
    }
}
