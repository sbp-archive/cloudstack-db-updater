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
import java.util.Map;

public class ContentsChangedAndMovedFilesConflict extends AbstractFileListConflict<ChangedSourceCodeFile> {

    private static final String KIND = "Moved files in which contents have changed";

    public ContentsChangedAndMovedFilesConflict(List<ChangedSourceCodeFile> movedFiles) {
        super(movedFiles);
    }

    @Override
    public String print() {
        return print(KIND);
    }

    @Override
    public Map<RelativePathFile, List<String>> getPatches() throws IOException {
        return getPatchesForChangedSourceCodeFiles(files);
    }

    @Override
    public String getKind() {
        return KIND;
    }

}
