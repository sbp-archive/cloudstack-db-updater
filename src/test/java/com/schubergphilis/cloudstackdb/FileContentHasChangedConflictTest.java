package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FileContentHasChangedConflictTest {

    @Test
    public void testPrint() throws Exception {
        String file1 = "file1";
        String file2 = "file2";
        List<ContentsChangedSourceCodeFile> filenames = Arrays.asList(new ContentsChangedSourceCodeFile[] {new ContentsChangedSourceCodeFile(file1, ""),
                new ContentsChangedSourceCodeFile(file2, "")});

        FileContentHasChangedConflict fileContentHasChangedConflict = new FileContentHasChangedConflict(filenames);

        assertThat(fileContentHasChangedConflict.print(), allOf(containsString(file1), containsString(file2)));
    }

}
