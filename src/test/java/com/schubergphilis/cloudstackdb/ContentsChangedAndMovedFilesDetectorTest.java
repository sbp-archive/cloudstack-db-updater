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

public class ContentsChangedAndMovedFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private ContentsChangedAndMovedFilesDetector detector;

    @Test
    public void testDetectWhenNoFileWasMoved() throws Exception {
        detector = new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion, new FileLists());

        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());

    }

    @Test
    public void testDetectWhenOneFileWasMoved() throws Exception {
        String nameOfFileThatWillBeMovedAndContentsWillChange = "nameOfFileThatWillBeMovedAndContentsWillChange";
        File fileThatWillBeMovedAndContentsWillChange = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMovedAndContentsWillChange);
        File fileThatHasBeenMovedAndContentsHaveChanged = rootFolderNewVersion.newFile(newDir + "/" + nameOfFileThatWillBeMovedAndContentsWillChange);
        FileUtils.writeToFile("foo", fileThatWillBeMovedAndContentsWillChange);
        FileUtils.writeToFile("bar", fileThatHasBeenMovedAndContentsHaveChanged);
        currentVersion.addFiles(Arrays.asList(new File[] {fileThatWillBeMovedAndContentsWillChange}));
        nextVersion.addFiles(Arrays.asList(new File[] {fileThatHasBeenMovedAndContentsHaveChanged}));
        detector = new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion, new FileLists());

        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(nameOfFileThatWillBeMovedAndContentsWillChange));
    }

}
