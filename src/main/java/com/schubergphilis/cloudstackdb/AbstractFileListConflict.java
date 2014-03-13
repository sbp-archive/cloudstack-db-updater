package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractFileListConflict implements Conflict {

    protected List<SourceCodeFile> files;

    public AbstractFileListConflict(List<SourceCodeFile> files) {
        this.files = new ArrayList<SourceCodeFile>(files);
        Collections.sort(this.files);
    }

    protected List<SourceCodeFile> getFiles() {
        return files;
    }

    protected String print(String header) {
        StringBuffer sb = new StringBuffer(header);
        for (SourceCodeFile file : files) {
            sb.append("\t- " + file.getPathRelativeToSourceRoot() + "\n");
        }
        return sb.toString();
    }

}
