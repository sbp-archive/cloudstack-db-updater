package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractFileListConflict implements Conflict {

    protected List<String> files;

    public AbstractFileListConflict(List<String> files) {
        this.files = new ArrayList<String>(files);
        Collections.sort(this.files);
    }

    protected List<String> getFiles() {
        return files;
    }

    public String print(String header) {
        StringBuffer sb = new StringBuffer(header);
        for (String file : files) {
            sb.append("\t- " + file + "\n");
        }
        return sb.toString();
    }

}
