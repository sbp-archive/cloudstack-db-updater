package com.schubergphilis.cloudstackdb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public final class ApplicationRunner implements Runnable {

    private static final String MANDATORY_OPTIONS = "-cv DIR -nv DIR";
    private static final String APPLICATION_NAME = "cs-db-updater.sh";

    private static Logger log = Logger.getLogger(ApplicationRunner.class);

    @Option(name = "-cv", required = true, usage = "set the directory with the current version of ACS", metaVar = "DIR")
    private File currentVersionSourceCodeDir;

    @Option(name = "-nv", required = true, usage = "set the directory with the next version of ACS", metaVar = "DIR")
    private File nextVersionSourceCodeDir;

    private String[] args;

    public static void main(String[] args) {
        new ApplicationRunner(args).run();
    }

    @Override
    public void run() {
        if (canParseArguments()) {
            doRun();
        }
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    protected boolean canParseArguments() {
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

    protected ApplicationRunner(String[] args) {
        this.args = args;
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
        Set<File> currentVersionFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(currentVersionSourceCodeDir);
        Set<File> nextVersionFiles = SourceCodeFileGatherer.gatherDbRelatedFiles(nextVersionSourceCodeDir);

        SourceCodeVersion currentVersion = new SourceCodeVersion(currentVersionSourceCodeDir);
        currentVersion.addFiles(currentVersionFiles);
        SourceCodeVersion nextVersion = new SourceCodeVersion(nextVersionSourceCodeDir);
        nextVersion.addFiles(nextVersionFiles);

        ConflictDetector detector = new FileSystemConflictDetector(currentVersion, nextVersion);
        List<Conflict> conflicts = detector.detect();

        StringBuilder sb = new StringBuilder();
        if (conflicts.isEmpty()) {
            sb.append("Found no potential conflicts between the two versions of ACS.\n");
        } else {
            int conflictCount = conflicts.size();
            sb.append("Found ").append(conflictCount).append("conflict").append(conflictCount > 1 ? "s" : "").append("\n");
            sb.append("Conflict list:\n");
            for (Conflict conflict : conflicts) {
                sb.append(conflict.print()).append("\n");
            }
        }
    }

}
