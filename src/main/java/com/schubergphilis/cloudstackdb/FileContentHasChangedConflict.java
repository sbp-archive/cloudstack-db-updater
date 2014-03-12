package com.schubergphilis.cloudstackdb;

import java.util.List;

public class FileContentHasChangedConflict extends AbstractFileListConflict {

    public FileContentHasChangedConflict(List<String> files) {
        super(files);
    }

    @Override
    public String print() {
        return print("Files in which content changed:\n");
    }

}
