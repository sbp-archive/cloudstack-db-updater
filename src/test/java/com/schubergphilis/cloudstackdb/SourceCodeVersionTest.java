/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SourceCodeVersionTest {

    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    private final List<File> files = new ArrayList<File>(5);
    private File baseDir;

    @Before
    public void setup() throws Exception {
        baseDir = rootFolder.getRoot();

        rootFolder.newFolder("src/");
        rootFolder.newFolder("src/main/");
        rootFolder.newFolder("src/main/java/");
        File package1Dir = rootFolder.newFolder("src/main/java/package1/");
        File package2Dir = rootFolder.newFolder("src/main/java/package1/package2/");

        files.add(new File(package1Dir, "ClassA.java"));
        files.add(new File(package1Dir, "ClassB.java"));
        files.add(new File(package2Dir, "ClassC.java"));
    }

    @Test
    public void testRemovePrefixWhenPrefixIsNotThere() throws Exception {
        String absolutePath = "some/path/to/a/dir";
        String prefix = "foo";

        String expected = absolutePath;
        String actual = SourceCodeVersion.removePrefix(absolutePath, prefix);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemovePrefixWhenPrefixIsrepeated() throws Exception {
        String absolutePath = "some/path/some/path/to/a/dir";
        String prefix = "some/path/";

        String expected = "some/path/to/a/dir";
        String actual = SourceCodeVersion.removePrefix(absolutePath, prefix);

        assertEquals(expected, actual);
    }

    @Test
    public void testFileDoesNotBelongToSourceCodeWhenFileDoesBelong() throws Exception {
        String fileAbsolutePath = "some/path/to/file";
        String baseDirAbsolutePath = "some/path/";

        assertFalse(SourceCodeVersion.fileDoesNotBelongToSourceCode(fileAbsolutePath, baseDirAbsolutePath));
    }

    @Test
    public void testFileDoesNotBelongToSourceCodeWhenFileDoesNotBelong() throws Exception {
        String fileAbsolutePath = "some/path/to/file";
        String baseDirAbsolutePath = "root/some/path/";

        assertTrue(SourceCodeVersion.fileDoesNotBelongToSourceCode(fileAbsolutePath, baseDirAbsolutePath));
    }

    @Test
    public void testAddFilesWhenAllFilesBelongToSourceCode() throws Exception {
        SourceCodeVersion srcCodeVersion = new SourceCodeVersion(baseDir);

        srcCodeVersion.addFiles(files);

        Map<String, SourceCodeFile> addedFiles = srcCodeVersion.getRelativePathToFilesMap();
        assertEquals(3, addedFiles.size());
        assertThat(addedFiles.keySet(), hasItems("/src/main/java/package1/ClassA.java", "/src/main/java/package1/ClassB.java", "/src/main/java/package1/package2/ClassC.java"));
    }

    @Test(expected = RuntimeException.class)
    public void testAddFilesWhenOneFileDoesNotBelongToSourceCode() throws Exception {
        SourceCodeVersion srcCodeVersion = new SourceCodeVersion(baseDir);
        files.add(new File("some/path"));

        srcCodeVersion.addFiles(files);
    }

}
