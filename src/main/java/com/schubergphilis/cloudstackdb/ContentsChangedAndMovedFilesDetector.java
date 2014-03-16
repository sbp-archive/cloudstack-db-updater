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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class ContentsChangedAndMovedFilesDetector extends FilePathBasedConflictDetector {

    private static final Logger log = Logger.getLogger(ContentsChangedFilesDetector.class);

    public ContentsChangedAndMovedFilesDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion, FileLists fileLists) {
        super(currentVersion, nextVersion, fileLists);
    }

    @Override
    public List<Conflict> detect() {
        log.info("Detecting moved files with changes to content");

        List<ChangedSourceCodeFile> contentsChangedAndMovedSourceCodeFiles = getFilesThatMovedAndContentsChanged(fileLists.getMovedFiles());
        List<Conflict> conflicts = new LinkedList<>();
        if (!contentsChangedAndMovedSourceCodeFiles.isEmpty()) {
            conflicts.add(new ContentsChangedAndMovedFilesConflict(contentsChangedAndMovedSourceCodeFiles));
        }

        return conflicts;
    }

    protected static List<ChangedSourceCodeFile> getFilesThatMovedAndContentsChanged(List<MovedSourceCodeFile> movedFiles) {
        List<ChangedSourceCodeFile> contentsChangedAndMovedSourceCodeFiles = new ArrayList<>(movedFiles.size());
        for (MovedSourceCodeFile movedFile : movedFiles) {
            addFileIfContentsChanged(new ContentsChangedAndMovedSourceCodeFile(movedFile.getOriginal(), movedFile.getChanged()), contentsChangedAndMovedSourceCodeFiles);
        }
        return contentsChangedAndMovedSourceCodeFiles;
    }

}
