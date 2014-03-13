package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class MovedFilesWithChangesToContentDetector extends FilePathBasedConflictDetector {

    private static final Logger log = Logger.getLogger(FileContentsChangeDetector.class);

    public MovedFilesWithChangesToContentDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting moved files with changes to content");

        List<ChangedSourceCodeFile> contentsChangedAndMovedSourceCodeFiles = getFilesThatMovedAndContentsChanged(movedFiles);
        List<Conflict> conflicts = new LinkedList<>();
        if (!contentsChangedAndMovedSourceCodeFiles.isEmpty()) {
            conflicts.add(new ContentsChangedAndMovedFilesConflict(contentsChangedAndMovedSourceCodeFiles));
        }

        return conflicts;
    }

    protected static List<ChangedSourceCodeFile> getFilesThatMovedAndContentsChanged(List<MovedSourceCodeFile> movedFiles) {
        List<ChangedSourceCodeFile> contentsChangedAndMovedSourceCodeFiles = new ArrayList<>(movedFiles.size());
        for (MovedSourceCodeFile movedFile : movedFiles) {
            addFileIfContentsChanged(new ContentsChangedAndMovedSourceCodeFile(movedFile.getOriginal(), movedFile.getChanged()), contentsChangedAndMovedSourceCodeFiles);
        }
        return contentsChangedAndMovedSourceCodeFiles;
    }

}
