package com.schubergphilis.cloudstackdb;


public abstract class AbstractConflictDetector implements ConflictDetector {

    protected final SourceCodeVersion currentVersion;
    protected final SourceCodeVersion nextVersion;

    public AbstractConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        this.currentVersion = currentVersion;
        this.nextVersion = nextVersion;
    }

}
