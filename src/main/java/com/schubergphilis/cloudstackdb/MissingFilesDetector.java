package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class MissingFilesDetector extends AbstractConflictDetector {

    private static final Logger log = Logger.getLogger(MissingFilesDetector.class);

    public MissingFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting missing files");
        return checkForMissingFiles(currentVersion.getFilenames(), nextVersion.getFilenames());
    }

    protected static List<Conflict> checkForMissingFiles(Set<String> filesInCurrentVersion, Set<String> filesInNextVersion) {
        List<Conflict> conflicts = new LinkedList<>();

        List<String> missingFiles = new ArrayList<>(filesInCurrentVersion);
        missingFiles.removeAll(filesInNextVersion);
        if (!missingFiles.isEmpty()) {
            conflicts.add(new MissingFilesConflict(missingFiles));
        }

        return conflicts;
    }

}
