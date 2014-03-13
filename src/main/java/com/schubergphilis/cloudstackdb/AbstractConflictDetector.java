package com.schubergphilis.cloudstackdb;

import java.io.IOException;
import java.util.List;

import com.schubergphilis.utils.FileUtils;

public abstract class AbstractConflictDetector implements ConflictDetector {

    protected final SourceCodeVersion currentVersion;
    protected final SourceCodeVersion nextVersion;

    public AbstractConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        this.currentVersion = currentVersion;
        this.nextVersion = nextVersion;
    }

    protected static void addFileIfContentsChanged(ChangedSourceCodeFile changedFile, List<ChangedSourceCodeFile> filenames) {
        try {
            if (!FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(changedFile.getOriginal().getFile(), changedFile.getChanged().getFile())) {
                filenames.add(changedFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't compare the contents of file '" + changedFile.getOriginal().getRelativePath() + "' in previous to +"
                    + changedFile.getChanged().getRelativePath() + "' in current version.", e);
        }
    }

}
