/**
 * blackduck-detect
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.detect.jenkins;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import com.blackducksoftware.integration.log.IntLogger;
import com.blackducksoftware.integration.log.LogLevel;

import hudson.model.TaskListener;

public class JenkinsDetectLogger extends IntLogger implements Serializable {
    private static final long serialVersionUID = -685871863395350470L;

    private final TaskListener jenkinsLogger;

    private LogLevel level = LogLevel.INFO;

    public JenkinsDetectLogger(final TaskListener jenkinsLogger) {
        this.jenkinsLogger = jenkinsLogger;
    }

    public TaskListener getJenkinsListener() {
        return jenkinsLogger;
    }

    @Override
    public void setLogLevel(final LogLevel level) {
        this.level = level;
    }

    @Override
    public LogLevel getLogLevel() {
        return level;
    }

    /**
     * Prints the message regardless of the log level
     */
    @Override
    public void alwaysLog(final String txt) {
        printLog(txt, null);
    }

    @Override
    public void debug(final String txt) {
        if (level.isLoggable(LogLevel.DEBUG)) {
            printLog(txt, null);
        }
    }

    @Override
    public void debug(final String txt, final Throwable e) {
        if (level.isLoggable(LogLevel.DEBUG)) {
            printLog(txt, e);
        }
    }

    @Override
    public void error(final Throwable e) {
        if (level.isLoggable(LogLevel.ERROR)) {
            printLog(null, e);
        }
    }

    @Override
    public void error(final String txt) {
        if (level.isLoggable(LogLevel.ERROR)) {
            printLog(txt, null);
        }
    }

    @Override
    public void error(final String txt, final Throwable e) {
        if (level.isLoggable(LogLevel.ERROR)) {
            printLog(txt, e);
        }
    }

    @Override
    public void info(final String txt) {
        if (level.isLoggable(LogLevel.INFO)) {
            printLog(txt, null);
        }
    }

    @Override
    public void trace(final String txt) {
        if (level.isLoggable(LogLevel.TRACE)) {
            printLog(txt, null);
        }
    }

    @Override
    public void trace(final String txt, final Throwable e) {
        if (level.isLoggable(LogLevel.TRACE)) {
            printLog(txt, e);
        }
    }

    @Override
    public void warn(final String txt) {
        if (level.isLoggable(LogLevel.WARN)) {
            printLog(txt, null);
        }
    }

    private void printLog(final String txt, final Throwable e) {
        if (txt != null) {
            if (jenkinsLogger != null) {
                jenkinsLogger.getLogger().println(txt);
            } else {
                System.out.println(txt);
            }
        }
        if (e != null) {
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            if (jenkinsLogger != null) {
                jenkinsLogger.getLogger().println(sw.toString());
            } else {
                System.out.println(sw.toString());
            }
        }
    }

}
