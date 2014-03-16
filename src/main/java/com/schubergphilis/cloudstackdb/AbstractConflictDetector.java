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

import java.io.IOException;
import java.util.List;

import com.schubergphilis.utils.FileUtils;

public abstract class AbstractConflictDetector implements ConflictDetector {

    protected final SourceCodeVersion currentVersion;
    protected final SourceCodeVersion nextVersion;

    public AbstractConflictDetector(SourceCodeVersion currentVersion, SourceCodeVersion nextVersion) {
        this.currentVersion = currentVersion;
        this.nextVersion = nextVersion;
    }

    protected static void addFileIfContentsChanged(ChangedSourceCodeFile changedFile, List<ChangedSourceCodeFile> filenames) {
        try {
            if (!FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(changedFile.getOriginal().getFile(), changedFile.getChanged().getFile())) {
                filenames.add(changedFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't compare the contents of file '" + changedFile.getOriginal().getRelativePath() + "' in previous to +"
                    + changedFile.getChanged().getRelativePath() + "' in current version.", e);
        }
    }

}
