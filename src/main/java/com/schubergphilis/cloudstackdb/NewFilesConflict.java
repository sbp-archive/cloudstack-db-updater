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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewFilesConflict extends AbstractFileListConflict<SourceCodeFile> {

    private static final String KIND = "New files";

    public NewFilesConflict(List<SourceCodeFile> newFiles) {
        super(newFiles);
    }

    @Override
    public String print() {
        return print(KIND);
    }

    @Override
    public Map<RelativePathFile, List<String>> getPatches() throws IOException {
        Map<RelativePathFile, List<String>> patches = new HashMap<RelativePathFile, List<String>>();
        for (SourceCodeFile file : files) {
            patches.put(file, getPatch(getEmtpyFile(), file.getFile()));
        }

        return patches;
    }

    @Override
    public String getKind() {
        return KIND;
    }

}
