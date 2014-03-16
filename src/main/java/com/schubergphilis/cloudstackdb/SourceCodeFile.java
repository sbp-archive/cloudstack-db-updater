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

import com.schubergphilis.utils.ClassUtils;

public class SourceCodeFile implements RelativePathFile, Comparable<SourceCodeFile> {

    protected final File file;
    protected final String relativePath;

    public SourceCodeFile(File file, String relativePath) {
        this.file = file;
        this.relativePath = relativePath;
    }

    protected SourceCodeFile(String relativePath) {
        this.file = new File(relativePath);
        this.relativePath = relativePath;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    @Override
    public boolean equals(Object obj) {
        return ClassUtils.doEquals(this, obj, "file");
    }

    @Override
    public int hashCode() {
        return ClassUtils.doHashCode(this, "file");
    }

    @Override
    public String toString() {
        return ClassUtils.doToString(this);
    }

    @Override
    public int compareTo(SourceCodeFile o) {
        return relativePath.compareTo(o.relativePath);
    }

    @Override
    public String print() {
        return relativePath;
    }
}
