package com.schubergphilis.cloudstackdb;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileContentsChangeDetector extends AbstractConflictDetector {

    private static final Logger log = Logger.getLogger(FileContentsChangeDetector.class);

    public FileContentsChangeDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting changes in file contents");
        return checkForChangesInFileContents(currentVersion, nextVersion);
    }

    protected static List<Conflict> checkForChangesInFileContents(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        List<Conflict> conflicts = new LinkedList<>();

        List<String> filesThatChangedInNewVersion = currentVersion.getFilesThatChangedInNewVersion(nextVersion);
        if (!filesThatChangedInNewVersion.isEmpty()) {
            conflicts.add(new FileContentHasChangedConflict(filesThatChangedInNewVersion));
        }

        return conflicts;
    }

}
