package com.schubergphilis.cloudstackdb;

import java.util.ArrayList;

import org.junit.Test;

public class AbstractConflictDetectorTest {

    @Test(expected = RuntimeException.class)
    public void testAddFileIfContentsChangedWhenFilesDoNotExist() throws Exception {
        AbstractConflictDetector.addFileIfContentsChanged(new MovedSourceCodeFile(new SourceCodeFile("a"), new SourceCodeFile("b/a")), new ArrayList<ChangedSourceCodeFile>());
    }

}
