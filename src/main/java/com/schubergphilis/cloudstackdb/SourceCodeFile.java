package com.schubergphilis.cloudstackdb;

import java.io.File;

import com.schubergphilis.utils.ClassUtils;

public class SourceCodeFile implements RelativePathFile, Comparable<SourceCodeFile> {

    protected final File file;
    protected final String relativePath;

    public SourceCodeFile(File file, String relativePath) {
        this.file = file;
        this.relativePath = relativePath;
    }

    protected SourceCodeFile(String relativePath) {
        this.file = new File(relativePath);
        this.relativePath = relativePath;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
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
        return relativePath.compareTo(o.relativePath);
    }

    @Override
    public String print() {
        return relativePath;
    }
}
