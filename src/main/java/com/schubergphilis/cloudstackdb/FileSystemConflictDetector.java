package com.schubergphilis.cloudstackdb;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileSystemConflictDetector extends AbstractConflictDetector {

    private static final Logger log = Logger.getLogger(FileSystemConflictDetector.class);

    private final List<ConflictDetector> detectors = new LinkedList<>();

    public FileSystemConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);

        detectors.add(new MissingFilesDetector(currentVersion, nextVersion));
        detectors.add(new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion));
        detectors.add(new ContentsChangedFilesDetector(currentVersion, nextVersion));
    }

    @Override
    public List<Conflict> detect() {
        log.info("Running file system conflict detection");

        List<Conflict> conflicts = new LinkedList<>();
        for (ConflictDetector detector : detectors) {
            conflicts.addAll(detector.detect());
        }

        return conflicts;
    }

}
