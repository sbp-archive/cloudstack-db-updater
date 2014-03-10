package com.schubergphilis.cloudstackdb;

import java.util.List;

public class FileContentHasChangedConflict implements Conflict {

    private final List<String> filenames;

    public FileContentHasChangedConflict(List<String> filenames) {
        this.filenames = filenames;
    }

    @Override
    public String print() {
        StringBuffer sb = new StringBuffer("Contents changed:\n");
        for (String file : filenames) {
            sb.append("\t- " + file + "\n");
        }
        return sb.toString();
    }

}
