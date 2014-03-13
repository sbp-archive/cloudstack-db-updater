package com.schubergphilis.cloudstackdb;

import java.util.List;

public class FileContentHasChangedConflict extends AbstractFileListConflict<ChangedSourceCodeFile> {

    public FileContentHasChangedConflict(List<ChangedSourceCodeFile> files) {
        super(files);
    }

    @Override
    public String print() {
        return print("Files in which content changed:\n");
    }

}
