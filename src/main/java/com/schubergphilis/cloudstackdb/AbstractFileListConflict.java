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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.schubergphilis.utils.ClassUtils;
import com.schubergphilis.utils.FileUtils;

public abstract class AbstractFileListConflict<T extends RelativePathFile & Comparable<T>> implements Conflict {

    protected List<T> files;

    public AbstractFileListConflict(List<T> files) {
        this.files = new ArrayList<T>(files);
        Collections.sort(this.files);
    }

    protected List<T> getFiles() {
        return files;
    }

    protected String print(String header) {
        StringBuilder sb = new StringBuilder(header);
        sb.append(":\n");
        for (T file : files) {
            sb.append("\t- " + file.print() + "\n");
        }
        return sb.toString();
    }

    protected static List<String> getPatch(File current, File next) throws IOException {
        return FileUtils.getPatch(current, next);
    }

    protected static File getEmtpyFile() throws IOException {
        return File.createTempFile("emptyFile-", Long.toString(System.currentTimeMillis()));
    }

    protected static Map<RelativePathFile, List<String>> getPatchesForChangedSourceCodeFiles(List<ChangedSourceCodeFile> changedFiles) throws IOException {
        Map<RelativePathFile, List<String>> patches = new HashMap<RelativePathFile, List<String>>();
        for (ChangedSourceCodeFile file : changedFiles) {
            patches.put(file, getPatch(file.getOriginal().getFile(), file.getChanged().getFile()));
        }

        return patches;
    }

    @Override
    public String toString() {
        return ClassUtils.doToString(this);
    }
}
