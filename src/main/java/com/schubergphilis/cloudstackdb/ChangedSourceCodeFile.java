package com.schubergphilis.cloudstackdb;

import com.schubergphilis.utils.ClassUtils;

public abstract class ChangedSourceCodeFile implements ChangedRelativePathFile, Comparable<ChangedSourceCodeFile> {

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
        return ClassUtils.doEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return ClassUtils.doHashCode(this);
    }

    @Override
    public String toString() {
        return ClassUtils.doToString(this);
    }

    @Override
    public String getRelativePath() {
        return original.getRelativePath();
    }

    @Override
    public String getNewRelativePath() {
        return changed.getRelativePath();
    }

    @Override
    public int compareTo(ChangedSourceCodeFile scf) {
        return original.compareTo(scf.original);
    }

}
