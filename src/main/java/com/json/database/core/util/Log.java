package com.json.database.core.util;

import java.io.Serializable;
import java.lang.System.Logger.Level;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Alvin
 **/

public class Log implements Serializable {

    private static final Set<String> sessionLogs = new HashSet<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String BOLD = "\033[0;1m";
    private static final String RESET_BOLD = "\033[0;0m";

    public static void log(String message, Object... obj) {
        List<Object> list = Arrays.asList(obj);
        if (list.isEmpty()) {
            sessionLogs.add(message);
            System.out.println(message);
            return;
        }
        sessionLogs.add(message);
        System.out.println();
        System.out.format(message.replaceAll("\\{}", "%s"), obj);
    }

    public static void log(Level level, String message, Object... obj) {
        log(message(level, message), obj);
    }

    public static void info(String message, Object... obj) {
        log(Level.INFO, message, obj);
    }

    public static void warn(String message, Object... obj) {
        log(Level.WARNING, message, obj);
    }

    public static void error(String message, Object... obj) {
        log(Level.ERROR, message, obj);
    }

    public static void error(Throwable throwable) {
        log(Level.ERROR, throwable.getMessage());
    }

    public static void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable.getMessage());
    }

    private static String message(Level level, String m) {
        StringBuilder builder = new StringBuilder();

        final Function<Level, String> spacer = (l) -> {
            switch (l) {
                case ALL:
                case OFF:
                    return "        ";
                case INFO:
                    return "       ";
                case TRACE:
                case DEBUG:
                case ERROR:
                    return "      ";
                case WARNING:
                    return "    ";
            }
            return "";
        };

        final Function<Level, String> color = (l) -> {
            switch (l) {
                case ALL:
                    return ANSI_WHITE.concat(l.name().concat(ANSI_RESET));
                case TRACE:
                    return ANSI_BLACK.concat(l.name()).concat(ANSI_RESET);
                case DEBUG:
                    return ANSI_PURPLE.concat(l.name()).concat(ANSI_RESET);
                case INFO:
                    return ANSI_GREEN.concat(l.name()).concat(ANSI_RESET);
                case WARNING:
                    return ANSI_YELLOW.concat(l.name()).concat(ANSI_RESET);
                case ERROR:
                    return ANSI_RED.concat(l.name()).concat(ANSI_RESET);
                case OFF:
                    return ANSI_CYAN.concat(l.name()).concat(ANSI_RESET);
            }
            return "";
        };

        builder
                .append(ANSI_WHITE)
                .append(LocalDate.now())
                .append("  |  ")
                .append(LocalTime.now().withNano(0))
                .append("\t")
                .append(ANSI_RESET)
                .append(BOLD)
                .append("[")
                .append(RESET_BOLD)
                .append(" ")
                .append(color.apply(level))
                .append(spacer.apply(level))
                .append(BOLD)
                .append("]")
                .append(RESET_BOLD)
                .append(ANSI_WHITE)
                .append(BOLD)
                .append(":\t --- \t")
                .append(RESET_BOLD)
                .append(ANSI_RESET)
                .append(m);
        return builder.toString();
    }

    public static Set<String> session() {
        return sessionLogs;
    }
}
