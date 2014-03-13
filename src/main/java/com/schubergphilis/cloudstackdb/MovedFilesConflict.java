package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovedFilesConflict implements Conflict {

    private final List<SourceCodeFileChange> movedFiles;

    public MovedFilesConflict(List<SourceCodeFileChange> movedFiles) {
        this.movedFiles = new ArrayList<>(movedFiles);
        Collections.sort(this.movedFiles);
    }

    @Override
    public String print() {
        StringBuffer sb = new StringBuffer("Moved files:\n");
        for (SourceCodeFileChange file : movedFiles) {
            sb.append("\t- " + file.getOriginal().getPathRelativeToSourceRoot() + "\n");
            sb.append("\t\tto ").append(file.getChanged().getPathRelativeToSourceRoot()).append("\n");
        }
        return sb.toString();
    }

}
