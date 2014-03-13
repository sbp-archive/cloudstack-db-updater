package com.schubergphilis.cloudstackdb;

import java.util.Map;
import java.util.TreeMap;

public class MovedFilesConflict implements Conflict {

    private final Map<String, String> movedFiles;

    public MovedFilesConflict(Map<String, String> movedFiles) {
        this.movedFiles = new TreeMap<>(movedFiles);
    }

    @Override
    public String print() {
        StringBuffer sb = new StringBuffer("Moved files:\n");
        for (String file : movedFiles.keySet()) {
            sb.append("\t- " + file + "\n");
            sb.append("\t\tto ").append(movedFiles.get(file)).append("\n");
        }
        return sb.toString();
    }

}
