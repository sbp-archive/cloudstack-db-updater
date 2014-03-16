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

public class FileSystemConflictDetector extends AbstractConflictDetector {

    private static final Logger log = Logger.getLogger(FileSystemConflictDetector.class);

    private final List<ConflictDetector> detectors = new LinkedList<>();

    public FileSystemConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        super(currentVersion, nextVersion);

        FileLists fileLists = new FileLists();
        detectors.add(new MissingFilesDetector(currentVersion, nextVersion, fileLists));
        detectors.add(new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion, fileLists));
        detectors.add(new ContentsChangedFilesDetector(currentVersion, nextVersion));
        detectors.add(new NewFilesDetector(currentVersion, nextVersion, fileLists));
    }

    @Override
    public List<Conflict> detect() {
        log.info("Running file system conflict detection");

        List<Conflict> conflicts = new LinkedList<>();
        for (ConflictDetector detector : detectors) {
            conflicts.addAll(detector.detect());
        }

        return conflicts;
    }

}
