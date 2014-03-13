package com.schubergphilis.cloudstackdb;

import java.util.List;

public class FileContentHasChangedConflict extends AbstractFileListConflict<ContentsChangedSourceCodeFile> {

    public FileContentHasChangedConflict(List<ContentsChangedSourceCodeFile> files) {
        super(files);
    }

    @Override
    public String print() {
        return print("Files in which content changed:\n");
    }

}
