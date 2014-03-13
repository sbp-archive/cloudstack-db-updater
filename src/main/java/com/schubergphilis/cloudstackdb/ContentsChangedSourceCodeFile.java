package com.schubergphilis.cloudstackdb;

public class ContentsChangedSourceCodeFile extends ChangedSourceCodeFile {

    public ContentsChangedSourceCodeFile(SourceCodeFile original, SourceCodeFile changed) {
        super(original, changed);
    }

    protected ContentsChangedSourceCodeFile(String original, String changed) {
        super(original, changed);
    }

    @Override
    public String print() {
        return original.print();
    }

}
