package com.schubergphilis.cloudstackdb;

import com.schubergphilis.utils.ClassUtils;

public abstract class ChangedSourceCodeFile implements ChangedRelativePathFile {

    protected final SourceCodeFile original;
    protected final SourceCodeFile changed;

    public ChangedSourceCodeFile(SourceCodeFile original, SourceCodeFile changed) {
        this.original = original;
        this.changed = changed;
    }

    protected ChangedSourceCodeFile(String original, String changed) {
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
    public String getRelativePath() {
        return original.getRelativePath();
    }

    @Override
    public String getNewRelativePath() {
        return changed.getRelativePath();
    }

}
