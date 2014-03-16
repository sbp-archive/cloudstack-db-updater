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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SourceCodeVersion {

    private final Map<String, SourceCodeFile> files = new HashMap<String, SourceCodeFile>();
    private final File baseDir;

    public SourceCodeVersion(File baseDir) {
        this.baseDir = baseDir;
    }

    public void addFiles(Collection<File> files) {
        for (File file : files) {
            String fileAbsolutePath = file.getAbsolutePath();
            String baseDirAbsolutePath = baseDir.getAbsolutePath();
            if (fileDoesNotBelongToSourceCode(fileAbsolutePath, baseDirAbsolutePath)) {
                throw new RuntimeException(String.format("Trying to add a file that does not belong to this source code version.%nBase directory = '%s'%nFile = '%s'.",
                        baseDirAbsolutePath, fileAbsolutePath));
            }
            String pathRelativeToSourceRoot = removePrefix(fileAbsolutePath, baseDirAbsolutePath);
            this.files.put(pathRelativeToSourceRoot, new SourceCodeFile(file, pathRelativeToSourceRoot));
        }
    }

    public boolean containsFile(String filename) {
        return files.containsKey(filename);
    }

    public SourceCodeFile getFile(String filename) {
        return files.get(filename);
    }

    public Set<String> getPathsRelativeToSourceRoot() {
        return files.keySet();
    }

    public Collection<SourceCodeFile> getFiles() {
        return files.values();
    }

    protected Map<String, SourceCodeFile> getRelativePathToFilesMap() {
        return files;
    }

    protected static boolean fileDoesNotBelongToSourceCode(String fileAbsolutePath, String baseDirAbsolutePath) {
        return !fileAbsolutePath.startsWith(baseDirAbsolutePath);
    }

    protected static String removePrefix(String absolutePath, String prefix) {
        return absolutePath.replaceFirst(prefix, "");
    }

}
