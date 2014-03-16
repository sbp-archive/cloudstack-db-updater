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

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class AbstractFileSystemConflictDetectorTest {

    @Rule
    public TemporaryFolder rootFolderCurrentVersion = new TemporaryFolder();
    @Rule
    public TemporaryFolder rootFolderNewVersion = new TemporaryFolder();

    protected SourceCodeVersion currentVersion;
    protected SourceCodeVersion nextVersion;

    protected final String filename = "file";
    protected final String nameOfFileThatWillBeMissing = "fileThatWillBeMissing";
    protected final String nameOfFileThatWillBeMoved = "fileThatWillBeMoved";
    protected File fileCurrentVersion;
    protected File fileNextVersion;
    protected String newDir = "newDir";

    @Before
    public void setup() throws Exception {
        fileCurrentVersion = rootFolderCurrentVersion.newFile(filename);
        fileNextVersion = rootFolderNewVersion.newFile(filename);
        File fileThatWillBeMissing = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMissing);
        File fileThatWillBeMoved = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMoved);

        rootFolderNewVersion.newFolder(newDir);
        File fileThatHasBeenMoved = rootFolderNewVersion.newFile(newDir + "/" + nameOfFileThatWillBeMoved);

        currentVersion = new SourceCodeVersion(rootFolderCurrentVersion.getRoot());
        nextVersion = new SourceCodeVersion(rootFolderNewVersion.getRoot());

        currentVersion.addFiles(Arrays.asList(new File[] {fileCurrentVersion, fileThatWillBeMissing, fileThatWillBeMoved}));
        nextVersion.addFiles(Arrays.asList(new File[] {fileNextVersion, fileThatHasBeenMoved}));
    }

}
