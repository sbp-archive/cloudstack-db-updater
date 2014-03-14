package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractFileListConflict<T extends RelativePathFile & Comparable<T>> implements Conflict {

    protected List<T> files;

    public AbstractFileListConflict(List<T> files) {
        this.files = new ArrayList<T>(files);
        Collections.sort(this.files);
    }

    protected List<T> getFiles() {
        return files;
    }

    protected String print(String header) {
        StringBuffer sb = new StringBuffer(header);
        for (T file : files) {
            sb.append("\t- " + file.print() + "\n");
        }
        return sb.toString();
    }

}
