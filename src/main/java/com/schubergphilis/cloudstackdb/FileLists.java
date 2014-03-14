package com.schubergphilis.cloudstackdb;

import java.util.List;

public class FileLists {

    private List<SourceCodeFile> newFiles;
    private List<SourceCodeFile> missingFiles;
    private List<MovedSourceCodeFile> movedFiles;

    public List<SourceCodeFile> getNewFiles() {
        return newFiles;
    }

    public void setNewFiles(List<SourceCodeFile> newFiles) {
        this.newFiles = newFiles;
    }

    public List<SourceCodeFile> getMissingFiles() {
        return missingFiles;
    }

    public void setMissingFiles(List<SourceCodeFile> missingFiles) {
        this.missingFiles = missingFiles;
    }

    public List<MovedSourceCodeFile> getMovedFiles() {
        return movedFiles;
    }

    public void setMovedFiles(List<MovedSourceCodeFile> movedFiles) {
        this.movedFiles = movedFiles;
    }

    public boolean missingFilesAreSet() {
        return missingFiles != null;
    }

}
