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

public class NewFilesDetector extends FilePathBasedConflictDetector {

    private static final Logger log = Logger.getLogger(NewFilesDetector.class);

    public NewFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion, FileLists fileLists) {
        super(currentVersion, nextVersion, fileLists);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting new files");

        List<SourceCodeFile> newFiles = getMissingFiles(nextVersion.getFiles(), currentVersion.getFiles());
        fileLists.setNewFiles(newFiles);

        List<Conflict> conflicts = new LinkedList<>();
        if (!newFiles.isEmpty()) {
            conflicts.add(new NewFilesConflict(newFiles));
        }

        return conflicts;
    }

}
