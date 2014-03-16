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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FilePathBasedConflictDetector extends AbstractConflictDetector {

    protected final FileLists fileLists;

    public FilePathBasedConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion, FileLists fileLists) {
        super(currentVersion, nextVersion);

        this.fileLists = fileLists;
        if (!this.fileLists.missingFilesAreSet()) {
            this.fileLists.setMissingFiles(getMissingFiles(currentVersion.getFiles(), nextVersion.getFiles()));
            this.fileLists.setMovedFiles(getMovedFiles(this.fileLists.getMissingFiles(), nextVersion.getFiles()));
        }
    }

    protected static List<SourceCodeFile> getMissingFiles(Collection<SourceCodeFile> filesInCurrentVersion, Collection<SourceCodeFile> filesInNextVersion) {
        List<SourceCodeFile> missingFiles = new ArrayList<>(filesInCurrentVersion);
        missingFiles.removeAll(filesInNextVersion);
        return missingFiles;
    }

    protected static List<MovedSourceCodeFile> getMovedFiles(Collection<SourceCodeFile> missingFiles, Collection<SourceCodeFile> filesInNextVersion) {
        List<MovedSourceCodeFile> movedFiles = new ArrayList<>(missingFiles.size());

        for (SourceCodeFile missingFile : missingFiles) {
            File file = missingFile.getFile();
            String fileName = file.getName();
            for (SourceCodeFile fileInNextVersion : filesInNextVersion) {
                if (fileInNextVersion.getFile().getName().equals(fileName)) {
                    movedFiles.add(new MovedSourceCodeFile(missingFile, fileInNextVersion));
                }
            }
        }

        return movedFiles;
    }
}
