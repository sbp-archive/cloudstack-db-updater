package com.schubergphilis.cloudstackdb;

import java.util.List;

public class MissingFilesConflict extends AbstractFileListConflict<SourceCodeFile> {

    public MissingFilesConflict(List<SourceCodeFile> missingFiles) {
        super(missingFiles);
    }

    @Override
    public String print() {
        return print("Missing files:\n");
    }
}
