package com.schubergphilis.cloudstackdb;

import java.util.List;

public class MissingFilesConflict extends AbstractFileListConflict {

    public MissingFilesConflict(List<String> missingFiles) {
        super(missingFiles);
    }

    @Override
    public String print() {
        return print("Missing files:\n");
    }
}
