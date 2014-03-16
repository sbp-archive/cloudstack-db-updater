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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class ContentsChangedFilesDetector extends AbstractConflictDetector {

    private static final Logger log = Logger.getLogger(ContentsChangedFilesDetector.class);

    public ContentsChangedFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting changes in file contents");
        return checkForChangesInFileContents(currentVersion, nextVersion);
    }

    protected static List<Conflict> checkForChangesInFileContents(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        List<Conflict> conflicts = new LinkedList<>();

        List<ChangedSourceCodeFile> filesThatChangedInNewVersion = getFilesThatChangedInNewVersion(currentVersion, nextVersion);
        if (!filesThatChangedInNewVersion.isEmpty()) {
            conflicts.add(new ContentsChangedFilesConflict(filesThatChangedInNewVersion));
        }

        return conflicts;
    }

    protected static List<ChangedSourceCodeFile> getFilesThatChangedInNewVersion(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        List<ChangedSourceCodeFile> files = new LinkedList<>();

        for (String filename : currentVersion.getPathsRelativeToSourceRoot()) {
            if (nextVersion.containsFile(filename)) {
                addFileIfContentsChanged(new ContentsChangedSourceCodeFile(currentVersion.getFile(filename), nextVersion.getFile(filename)), files);
            }
        }

        return files;
    }

}
