package com.schubergphilis.cloudstackdb;

import java.io.File;

public class SourceCodeFile {

    private final File file;
    private final String pathRelativeToSourceRoot;

    public SourceCodeFile(File file, String pathRelativeToSourceRoot) {
        this.file = file;
        this.pathRelativeToSourceRoot = pathRelativeToSourceRoot;
    }

    public SourceCodeFile(String pathRelativeToSourceRoot) {
        this.file = new File(pathRelativeToSourceRoot);
        this.pathRelativeToSourceRoot = pathRelativeToSourceRoot;
    }

    public File getFile() {
        return file;
    }

    public String getPathRelativeToSourceRoot() {
        return pathRelativeToSourceRoot;
    }

}
