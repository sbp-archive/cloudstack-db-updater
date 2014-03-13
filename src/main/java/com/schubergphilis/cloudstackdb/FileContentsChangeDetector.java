package com.schubergphilis.cloudstackdb;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.schubergphilis.utils.FileUtils;

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

        List<SourceCodeFile> filesThatChangedInNewVersion = getFilesThatChangedInNewVersion(currentVersion, nextVersion);
        if (!filesThatChangedInNewVersion.isEmpty()) {
            conflicts.add(new FileContentHasChangedConflict(filesThatChangedInNewVersion));
        }

        return conflicts;
    }

    protected static List<SourceCodeFile> getFilesThatChangedInNewVersion(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        List<SourceCodeFile> files = new LinkedList<>();

        for (String filename : currentVersion.getPathsRelativeToSourceRoot()) {
            if (nextVersion.containsFile(filename)) {
                addFileIfItChanged(currentVersion.getFile(filename), nextVersion.getFile(filename), files);
            }
        }

        return files;
    }

    private static void addFileIfItChanged(SourceCodeFile fileInCurrentVersion, SourceCodeFile FileInNextVersion, List<SourceCodeFile> filenames) {
        try {
            if (!FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(fileInCurrentVersion.getFile(), FileInNextVersion.getFile())) {
                filenames.add(fileInCurrentVersion);
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't compare the contents of file '" + fileInCurrentVersion.getPathRelativeToSourceRoot() + "' in previous and current version.", e);
        }
    }

}
