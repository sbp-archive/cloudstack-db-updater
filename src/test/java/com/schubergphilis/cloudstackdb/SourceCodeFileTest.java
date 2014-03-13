package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class SourceCodeFileTest {

    @Test
    public void testThatEqualsAndHashCodeExcludeFileField() throws Exception {
        String pathRelativeToSourceRoot = "path";
        SourceCodeFile sourceCodeFile1 = new SourceCodeFile(new File("someName"), pathRelativeToSourceRoot);
        SourceCodeFile sourceCodeFile2 = new SourceCodeFile(new File("otherName"), pathRelativeToSourceRoot);

        assertEquals(sourceCodeFile1, sourceCodeFile2);
        assertEquals(sourceCodeFile1.hashCode(), sourceCodeFile2.hashCode());
    }

}
