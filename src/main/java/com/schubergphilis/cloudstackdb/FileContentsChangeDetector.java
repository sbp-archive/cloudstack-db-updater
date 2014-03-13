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

        List<ChangedSourceCodeFile> filesThatChangedInNewVersion = getFilesThatChangedInNewVersion(currentVersion, nextVersion);
        if (!filesThatChangedInNewVersion.isEmpty()) {
            conflicts.add(new FileContentHasChangedConflict(filesThatChangedInNewVersion));
        }

        return conflicts;
    }

    protected static List<ChangedSourceCodeFile> getFilesThatChangedInNewVersion(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        List<ChangedSourceCodeFile> files = new LinkedList<>();

        for (String filename : currentVersion.getPathsRelativeToSourceRoot()) {
            if (nextVersion.containsFile(filename)) {
                addFileIfContentsChanged(new ContentsChangedSourceCodeFile(currentVersion.getFile(filename), nextVersion.getFile(filename)), files);
            }
        }

        return files;
    }

}
