package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MissingFilesConflict implements Conflict {

    private final List<String> missingFiles;

    public MissingFilesConflict(List<String> missingFiles) {
        this.missingFiles = new ArrayList<String>(missingFiles);
        Collections.sort(this.missingFiles);
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
