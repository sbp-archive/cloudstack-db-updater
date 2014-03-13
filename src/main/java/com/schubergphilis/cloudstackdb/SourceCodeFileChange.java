package com.schubergphilis.cloudstackdb;

import com.schubergphilis.utils.ClassUtils;

public class SourceCodeFileChange implements Comparable<SourceCodeFileChange> {

    private final SourceCodeFile original;
    private final SourceCodeFile changed;

    public SourceCodeFileChange(SourceCodeFile original, SourceCodeFile changed) {
        this.original = original;
        this.changed = changed;
    }

    protected SourceCodeFileChange(String original, String changed) {
        this.original = new SourceCodeFile(original);
        this.changed = new SourceCodeFile(changed);
    }

    public SourceCodeFile getOriginal() {
        return original;
    }

    public SourceCodeFile getChanged() {
        return changed;
    }

    @Override
    public boolean equals(Object obj) {
        return ClassUtils.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return ClassUtils.hashCode(this);
    }

    @Override
    public String toString() {
        return ClassUtils.toString(this);
    }

    @Override
    public int compareTo(SourceCodeFileChange o) {
        return original.compareTo(o.original);
    }

}
