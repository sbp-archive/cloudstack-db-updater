package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MissingFilesConflict implements Conflict {

    private final List<String> missingFiles;

    public MissingFilesConflict(Set<String> filesInPreviousVersion, Set<String> filesInCurrentVersion) {
        missingFiles = new ArrayList<String>(filesInPreviousVersion);
        missingFiles.removeAll(filesInCurrentVersion);
    }

    protected List<String> getMissingFiles() {
        return missingFiles;
    }

    @Override
    public String print() {
        StringBuffer sb = new StringBuffer("Missing files:\n");
        for (String file : missingFiles) {
            sb.append("\t- " + file + "\n");
        }
        return sb.toString();
    }

}
