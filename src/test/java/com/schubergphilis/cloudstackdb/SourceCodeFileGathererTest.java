package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

public class SourceCodeFileGathererTest {

    private final File rootDir = new File(Thread.currentThread().getContextClassLoader().getResource("versions/v1").getFile());

    @SuppressWarnings("unchecked")
    @Test
    public void testGatherDbRelatedFiles() throws Exception {
        Set<File> dbRelatedFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(rootDir);

        assertNotNull(dbRelatedFiles);
        assertThat(dbRelatedFiles, IsIterableContainingInAnyOrder.<File> containsInAnyOrder(hasProperty("name", is("file1")), hasProperty("name", is("file2VO.java"))));
    }

}
