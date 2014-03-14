package com.schubergphilis.cloudstackdb;

import java.util.List;

public class ContentsChangedFilesConflict extends AbstractFileListConflict<ChangedSourceCodeFile> {

    public ContentsChangedFilesConflict(List<ChangedSourceCodeFile> files) {
        super(files);
    }

    @Override
    public String print() {
        return print("Files in which content changed:\n");
    }

}
