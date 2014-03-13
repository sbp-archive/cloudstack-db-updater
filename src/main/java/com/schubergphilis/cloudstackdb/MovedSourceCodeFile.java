package com.schubergphilis.cloudstackdb;

public class MovedSourceCodeFile extends ChangedSourceCodeFile {

    public MovedSourceCodeFile(SourceCodeFile original, SourceCodeFile changed) {
        super(original, changed);
    }

    protected MovedSourceCodeFile(String original, String changed) {
        super(original, changed);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(original.print() + " to " + changed.print());
        return sb.toString();
    }

}
