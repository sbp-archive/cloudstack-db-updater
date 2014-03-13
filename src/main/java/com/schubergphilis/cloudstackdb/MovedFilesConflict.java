package com.schubergphilis.cloudstackdb;

import java.util.List;

public class MovedFilesConflict extends AbstractFileListConflict<MovedSourceCodeFile> {

    public MovedFilesConflict(List<MovedSourceCodeFile> movedFiles) {
        super(movedFiles);
    }

    @Override
    public String print() {
        return print("Moved files:\n");
    }
}
