package com.schubergphilis.cloudstackdb;

import java.util.List;

public class NewFilesConflict extends AbstractFileListConflict<SourceCodeFile> {

    public NewFilesConflict(List<SourceCodeFile> newFiles) {
        super(newFiles);
    }

    @Override
    public String print() {
        return print("New files:\n");
    }

}
