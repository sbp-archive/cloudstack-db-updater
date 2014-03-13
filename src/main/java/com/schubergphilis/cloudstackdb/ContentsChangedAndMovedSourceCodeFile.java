package com.schubergphilis.cloudstackdb;

public class ContentsChangedAndMovedSourceCodeFile extends MovedSourceCodeFile {

    public ContentsChangedAndMovedSourceCodeFile(SourceCodeFile original, SourceCodeFile changed) {
        super(original, changed);
    }

    protected ContentsChangedAndMovedSourceCodeFile(String original, String changed) {
        super(original, changed);
    }

}
