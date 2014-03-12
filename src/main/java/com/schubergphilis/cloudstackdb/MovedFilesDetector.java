package com.schubergphilis.cloudstackdb;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class MovedFilesDetector extends FilePathConflictDetector {

    private static final Logger log = Logger.getLogger(FileContentsChangeDetector.class);

    public MovedFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting moved files");

        List<Conflict> conflicts = new LinkedList<>();
        if (!movedFiles.isEmpty()) {
            conflicts.add(new MovedFilesConflict(movedFiles));
        }

        return conflicts;
    }

}
