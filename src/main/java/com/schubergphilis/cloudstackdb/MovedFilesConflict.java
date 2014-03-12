package com.schubergphilis.cloudstackdb;

import java.util.List;

public class MovedFilesConflict extends AbstractFileListConflict {

    public MovedFilesConflict(List<String> files) {
        super(files);
    }

    @Override
    public String print() {
        return print("Moved files:\n");
    }

}
