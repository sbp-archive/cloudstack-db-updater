package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileContentHasChangedConflict implements Conflict {

    private final List<String> filenames;

    public FileContentHasChangedConflict(List<String> filenames) {
        this.filenames = new ArrayList<>(filenames);
        Collections.sort(this.filenames);
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
