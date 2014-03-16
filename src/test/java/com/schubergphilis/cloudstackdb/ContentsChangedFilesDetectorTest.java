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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.schubergphilis.utils.FileUtils;

public class ContentsChangedFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testCheckForChangesInFileContentsWhenNoFileHasChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("foo", fileNewVersion);

        List<Conflict> conflicts = ContentsChangedFilesDetector.checkForChangesInFileContents(currentVersion, nextVersion);

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void testCheckForChangesInFileContentsWhenOneFileHasChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);
        String otherFilename = "otherFile";
        File otherFileCurrentVersion = rootFolderCurrentVersion.newFile(otherFilename);
        File otherFileNewVersion = rootFolderNewVersion.newFile(otherFilename);
        FileUtils.writeToFile("foobar", otherFileCurrentVersion);
        FileUtils.writeToFile("foobar", otherFileNewVersion);
        currentVersion.addFiles(Arrays.asList(new File[] {otherFileCurrentVersion}));
        nextVersion.addFiles(Arrays.asList(new File[] {otherFileNewVersion}));

        List<Conflict> conflicts = ContentsChangedFilesDetector.checkForChangesInFileContents(currentVersion, nextVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(filename));
    }

    @Test
    public void testDetectAppliesCheckForMissingFiles() throws Exception {
        ContentsChangedFilesDetector detector = new ContentsChangedFilesDetector(currentVersion, nextVersion);

        List<Conflict> expected = ContentsChangedFilesDetector.checkForChangesInFileContents(currentVersion, nextVersion);
        List<Conflict> actual = detector.detect();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFilesThatChangedInNewVersionWhenOneFileChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);

        List<ChangedSourceCodeFile> expected = Arrays.asList(new ChangedSourceCodeFile[] {new ContentsChangedSourceCodeFile("/" + filename, "/" + filename)});
        List<ChangedSourceCodeFile> actual = ContentsChangedFilesDetector.getFilesThatChangedInNewVersion(currentVersion, nextVersion);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFilesThatChangedInNewVersionWhenNoFileChanged() throws Exception {
        List<ChangedSourceCodeFile> actual = ContentsChangedFilesDetector.getFilesThatChangedInNewVersion(currentVersion, nextVersion);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

}
