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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MissingFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private MissingFilesDetector detector;
    private FileLists fileLists;

    @Override
    public void setup() throws Exception {
        super.setup();

        fileLists = new FileLists();
        detector = new MissingFilesDetector(currentVersion, nextVersion, fileLists);
    }

    @Test
    public void testGetMissingFilesFilteringOutMovedFilesWhenAllFilesAreMissing() throws Exception {
        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(fileLists.getMissingFiles(), fileLists.getMovedFiles());

        assertNotNull(filteredMissingFiles);
        assertEquals(1, filteredMissingFiles.size());
        assertEquals(new SourceCodeFile("/" + nameOfFileThatWillBeMissing), filteredMissingFiles.get(0));
    }

    @Test
    public void testGetMissingFilesFilteringOutMovedFilesWhenMoreThanOneFileIsFiltered() throws Exception {
        SourceCodeFile sourceCodeFile = new SourceCodeFile("file3");
        List<SourceCodeFile> missingFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("file1"), new SourceCodeFile("file2"), sourceCodeFile});
        List<MovedSourceCodeFile> movedFiles = Arrays.asList(new MovedSourceCodeFile[] {new MovedSourceCodeFile("file1", "fileA"), new MovedSourceCodeFile("file2", "fileB")});

        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(missingFiles, movedFiles);

        assertNotNull(filteredMissingFiles);
        assertEquals(1, filteredMissingFiles.size());
        assertEquals(sourceCodeFile, filteredMissingFiles.get(0));
    }

    @Test
    public void testDetectAppliesGetMissingFilesFilteringOutMovedFiles() throws Exception {
        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(fileLists.getMissingFiles(), fileLists.getMovedFiles());
        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertTrue(conflicts.get(0) instanceof MissingFilesConflict);
        assertEquals(filteredMissingFiles, ((MissingFilesConflict)conflicts.get(0)).getFiles());
    }

}
