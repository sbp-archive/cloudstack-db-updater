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

import com.schubergphilis.utils.ClassUtils;

public abstract class ChangedSourceCodeFile implements ChangedRelativePathFile, Comparable<ChangedSourceCodeFile> {

    protected final SourceCodeFile original;
    protected final SourceCodeFile changed;

    public ChangedSourceCodeFile(SourceCodeFile original, SourceCodeFile changed) {
        this.original = original;
        this.changed = changed;
    }

    protected ChangedSourceCodeFile(String original, String changed) {
        this.original = new SourceCodeFile(original);
        this.changed = new SourceCodeFile(changed);
    }

    public SourceCodeFile getOriginal() {
        return original;
    }

    public SourceCodeFile getChanged() {
        return changed;
    }

    @Override
    public boolean equals(Object obj) {
        return ClassUtils.doEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return ClassUtils.doHashCode(this);
    }

    @Override
    public String toString() {
        return ClassUtils.doToString(this);
    }

    @Override
    public String getRelativePath() {
        return original.getRelativePath();
    }

    @Override
    public String getNewRelativePath() {
        return changed.getRelativePath();
    }

    @Override
    public int compareTo(ChangedSourceCodeFile scf) {
        return original.compareTo(scf.original);
    }

}
