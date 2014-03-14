package com.schubergphilis.cloudstackdb;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class NewFilesDetector extends FilePathBasedConflictDetector {

    private static final Logger log = Logger.getLogger(NewFilesDetector.class);

    public NewFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion, FileLists fileLists) {
        super(currentVersion, nextVersion, fileLists);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting new files");

        List<SourceCodeFile> newFiles = getMissingFiles(nextVersion.getFiles(), currentVersion.getFiles());
        fileLists.setNewFiles(newFiles);

        List<Conflict> conflicts = new LinkedList<>();
        if (!newFiles.isEmpty()) {
            conflicts.add(new NewFilesConflict(newFiles));
        }

        return conflicts;
    }

}
