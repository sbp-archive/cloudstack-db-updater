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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.util.Set;

import org.apache.log4j.Logger;

import com.schubergphilis.utils.FileUtils;

public class SourceCodeFileGatherer {

    private static final Logger log = Logger.getLogger(SourceCodeFileGatherer.class);

    private SourceCodeFileGatherer() {
    }

    public static Set<File> gatherDbRelatedFiles(File sourceCodeBaseDir) {
        log.info("Gathering DB related files from " + sourceCodeBaseDir.getPath());
        return FileUtils.gatherFilesThatMatchCriteria(sourceCodeBaseDir,
                allOf(anyOf(containsString("/db/"), endsWith("VO.java")), not(containsString("/test/")), not(containsString("/target/"))));
    }
}
