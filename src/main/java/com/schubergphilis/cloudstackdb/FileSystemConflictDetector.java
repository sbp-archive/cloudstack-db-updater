package com.schubergphilis.cloudstackdb;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FileSystemConflictDetector implements ConflictDetector {

    private final SourceCodeVersion currentVersion;
    private final SourceCodeVersion newVersion;

    public FileSystemConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion newVersion) {
        this.currentVersion = currentVersion;
        this.newVersion = newVersion;
    }

    @Override
    public List<Conflict> detect() {
        List<Conflict> conflicts = new LinkedList<>();

        conflicts.addAll(checkForMissingFiles(currentVersion.getFilenames(), newVersion.getFilenames()));

        conflicts.addAll(checkForChangesInFileContents(currentVersion, newVersion));

        return conflicts;
    }

    protected static List<Conflict> checkForChangesInFileContents(SourceCodeVersion currentVersion, SourceCodeVersion newVersion) {
        List<Conflict> conflicts = new LinkedList<>();

        List<String> filesThatChangedInNewVersion = currentVersion.getFilesThatChangedInNewVersion(newVersion);
        if (!filesThatChangedInNewVersion.isEmpty()) {
            conflicts.add(new FileContentHasChangedConflict(filesThatChangedInNewVersion));
        }

        return conflicts;
    }

    protected static List<Conflict> checkForMissingFiles(Set<String> filesInCurrentVersion, Set<String> filesInNewVersion) {
        List<Conflict> conflicts = new LinkedList<>();
        if (newVersionDoesNotHaveAllFilesFromCurrent(filesInCurrentVersion, filesInNewVersion)) {
            conflicts.add(new MissingFilesConflict(filesInCurrentVersion, filesInNewVersion));
        }
        return conflicts;
    }

    private static boolean newVersionDoesNotHaveAllFilesFromCurrent(Set<String> filesInCurrentVersion, Set<String> filesInNewVersion) {
        return !filesInNewVersion.containsAll(filesInCurrentVersion);
    }

}
