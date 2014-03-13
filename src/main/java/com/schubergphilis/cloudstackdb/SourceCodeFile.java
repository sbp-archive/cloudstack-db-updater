package com.schubergphilis.cloudstackdb;

import java.io.File;

import com.schubergphilis.utils.ClassUtils;

public class SourceCodeFile implements Comparable<SourceCodeFile> {

    protected final File file;
    protected final String pathRelativeToSourceRoot;

    public SourceCodeFile(File file, String pathRelativeToSourceRoot) {
        this.file = file;
        this.pathRelativeToSourceRoot = pathRelativeToSourceRoot;
    }

    protected SourceCodeFile(String pathRelativeToSourceRoot) {
        this.file = new File(pathRelativeToSourceRoot);
        this.pathRelativeToSourceRoot = pathRelativeToSourceRoot;
    }

    public File getFile() {
        return file;
    }

    public String getPathRelativeToSourceRoot() {
        return pathRelativeToSourceRoot;
    }

    @Override
    public boolean equals(Object obj) {
        return ClassUtils.equals(this, obj, "file");
    }

    @Override
    public int hashCode() {
        return ClassUtils.hashCode(this, "file");
    }

    @Override
    public String toString() {
        return ClassUtils.toString(this);
    }

    @Override
    public int compareTo(SourceCodeFile o) {
        return pathRelativeToSourceRoot.compareTo(o.pathRelativeToSourceRoot);
    }
}
