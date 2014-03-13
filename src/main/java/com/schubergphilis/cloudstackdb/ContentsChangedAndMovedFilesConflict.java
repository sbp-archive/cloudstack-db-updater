package com.schubergphilis.cloudstackdb;

import java.util.List;

public class ContentsChangedAndMovedFilesConflict extends AbstractFileListConflict<ChangedSourceCodeFile> {

    public ContentsChangedAndMovedFilesConflict(List<ChangedSourceCodeFile> movedFiles) {
        super(movedFiles);
    }

    @Override
    public String print() {
        return print("Moved files in which contents have changed:\n");
    }
}
