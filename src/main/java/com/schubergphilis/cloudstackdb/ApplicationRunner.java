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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.schubergphilis.utils.FileUtils;

public final class ApplicationRunner implements Runnable {

    private static final String PATH_SEPARATOR = System.getProperty("path.separator");
    private static final String MANDATORY_OPTIONS = "-cv DIR -nv DIR";
    private static final String APPLICATION_NAME = "cs-db-updater.sh";

    private static Logger log = Logger.getLogger(ApplicationRunner.class);

    @Option(name = "-cv", required = true, usage = "set the directory with the current version of ACS", metaVar = "DIR")
    private File currentVersionSourceCodeDir;

    @Option(name = "-nv", required = true, usage = "set the directory with the next version of ACS", metaVar = "DIR")
    private File nextVersionSourceCodeDir;

    private List<String> args;
    protected String printedConflicts;

    protected ApplicationRunner(String[] args) {
        setArgs(args);
    }

    public static void main(String[] args) {
        new ApplicationRunner(args).doRun();
    }

    @Override
    public void run() {
        doRun();
    }

    public void setArgs(String[] args) {
        this.args = Arrays.asList(args);
    }

    private boolean canParseArguments() {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            log.error("Failed to parse application arguments", e);
            log.info(printUsageInfoMessage(parser));
            return false;
        }

        return true;
    }

    protected static String printUsage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Command: ").append(APPLICATION_NAME).append(" ").append(MANDATORY_OPTIONS);

        return sb.toString();
    }

    protected static String printUsageInfoMessage(CmdLineParser parser) {
        ByteArrayOutputStream optionsBuffer = new ByteArrayOutputStream();
        parser.printUsage(optionsBuffer);
        StringBuilder sb = new StringBuilder();
        sb.append("Here's how to use the tool\n");
        sb.append(printUsage()).append("\n");
        sb.append("Options:\n").append(optionsBuffer.toString());
        return sb.toString();
    }

    protected void doRun() {
        if (canParseArguments()) {
            runDetectionAndReportFindings();
        }
    }

    private void runDetectionAndReportFindings() {
        Set<File> currentVersionFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(currentVersionSourceCodeDir);
        Set<File> nextVersionFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(nextVersionSourceCodeDir);

        SourceCodeVersion currentVersion = new SourceCodeVersion(currentVersionSourceCodeDir);
        currentVersion.addFiles(currentVersionFiles);
        SourceCodeVersion nextVersion = new SourceCodeVersion(nextVersionSourceCodeDir);
        nextVersion.addFiles(nextVersionFiles);

        ConflictDetector detector = new FileSystemConflictDetector(currentVersion, nextVersion);
        List<Conflict> conflicts = detector.detect();

        dumpPatchesToFile(conflicts);

        printedConflicts = printConflicts(conflicts);
        log.info(printedConflicts);
    }

    private static void dumpPatchesToFile(List<Conflict> conflicts) {
        for (Conflict conflict : conflicts) {
            Map<RelativePathFile, List<String>> patches;
            try {
                patches = conflict.getPatches();
                for (RelativePathFile file : patches.keySet()) {
                    File diffFile = new File(new File("diffs"), "diff-" + file.getRelativePath().replace(PATH_SEPARATOR, "_"));
                    List<String> patch = patches.get(file);
                    FileUtils.writeToFile(patch, diffFile);
                    log.info("Wrote patch to " + diffFile.getPath());
                }
            } catch (IOException e) {
                log.error("Couldn't get patches for " + conflict.getKind() + " conflict:\n" + conflict);
            }
        }
    }

    protected static String printConflicts(List<Conflict> conflicts) {
        StringBuilder sb = new StringBuilder();
        if (conflicts.isEmpty()) {
            sb.append("Found no potential conflicts between the two versions of ACS.\n");
        } else {
            int conflictCount = conflicts.size();
            sb.append("Found ").append(conflictCount).append(" potential source").append(conflictCount > 1 ? "s" : "").append(" of conflict\n\n");
            for (Conflict conflict : conflicts) {
                sb.append(conflict.print()).append("\n");
            }
        }
        String findings = sb.toString();
        return findings;
    }

}
