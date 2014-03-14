package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.schubergphilis.utils.FileUtils;

public class SourceCodeFileGathererTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private final File rootDir = new File(Thread.currentThread().getContextClassLoader().getResource("versions/v1").getFile());

    @Before
    public void setup() throws Exception {
        FileUtils.copyDirectory(rootDir, tempFolder.getRoot());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGatherDbRelatedFiles() throws Exception {
        Set<File> dbRelatedFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(tempFolder.getRoot());

        assertNotNull(dbRelatedFiles);
        assertThat(dbRelatedFiles, IsIterableContainingInAnyOrder.<File> containsInAnyOrder(hasProperty("name", is("file1")), hasProperty("name", is("file2VO.java"))));
    }

}
