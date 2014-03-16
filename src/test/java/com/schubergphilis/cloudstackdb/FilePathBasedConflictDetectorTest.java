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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

public class FilePathBasedConflictDetectorTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testGetMissingFilesWhenNoFileIsMissing() throws Exception {
        Set<SourceCodeFile> filesInPreviousVersion = new HashSet<>(Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")}));
        Set<SourceCodeFile> filesInCurrentVersion = new HashSet<>(Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")}));

        List<SourceCodeFile> missingFiles = FilePathBasedConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(0, missingFiles.size());
    }

    @Test
    public void testGetMissingFilesWhenSomeFileIsMissing() throws Exception {
        Set<SourceCodeFile> filesInPreviousVersion = new HashSet<>(Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")}));
        Set<SourceCodeFile> filesInCurrentVersion = new HashSet<>(Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("c")}));

        List<SourceCodeFile> missingFiles = FilePathBasedConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(2, missingFiles.size());
        assertThat(missingFiles, IsIterableContainingInAnyOrder.<SourceCodeFile> containsInAnyOrder(new SourceCodeFile("a"), new SourceCodeFile("b")));
    }

    @Test
    public void testGetMissingFilesWhenAllFilesAreMissing() throws Exception {
        Set<SourceCodeFile> filesInPreviousVersion = new HashSet<>(Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")}));
        Set<SourceCodeFile> filesInCurrentVersion = new HashSet<>();

        List<SourceCodeFile> missingFiles = FilePathBasedConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(3, missingFiles.size());
        assertThat(missingFiles, IsIterableContainingInAnyOrder.<SourceCodeFile> containsInAnyOrder(new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")));
    }

    @Test
    public void testGetMovedFilesWhenNoFilesAreMissing() throws Exception {
        Collection<SourceCodeFile> missingFiles = new ArrayList<>();
        Collection<SourceCodeFile> filesInNextVersion = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("z/c")});

        List<MovedSourceCodeFile> expected = new ArrayList<>();
        List<MovedSourceCodeFile> actual = FilePathBasedConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMovedFilesWhenNextVersionHasNoFiles() throws Exception {
        Collection<SourceCodeFile> missingFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a/b/c"), new SourceCodeFile("d/e/f")});
        Collection<SourceCodeFile> filesInNextVersion = new ArrayList<>();

        List<MovedSourceCodeFile> expected = new ArrayList<>();
        List<MovedSourceCodeFile> actual = FilePathBasedConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMovedFilesWhenOneFileMoved() throws Exception {
        SourceCodeFile sourceCodeFile1 = new SourceCodeFile("a/b/c");
        SourceCodeFile sourceCodeFile2 = new SourceCodeFile("z/c");
        Collection<SourceCodeFile> missingFiles = Arrays.asList(new SourceCodeFile[] {sourceCodeFile1, new SourceCodeFile("d/e/f")});
        Collection<SourceCodeFile> filesInNextVersion = Arrays.asList(new SourceCodeFile[] {sourceCodeFile2});

        List<MovedSourceCodeFile> expected = Arrays.asList(new MovedSourceCodeFile[] {new MovedSourceCodeFile(sourceCodeFile1, sourceCodeFile2)});
        List<MovedSourceCodeFile> actual = FilePathBasedConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, actual);
    }
}
