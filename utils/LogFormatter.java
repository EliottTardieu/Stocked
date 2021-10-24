package com.stocked.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter  {

    // ANSI escape code
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_BLACK = "\033[30m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PURPLE = "\033[35m";
    public static final String ANSI_CYAN = "\033[36m";
    public static final String ANSI_WHITE = "\033[37m";
    public static final String ANSI_PINK = "\033[1;35m";

    // Here you can configure the format of the output and
    // its color by using the ANSI escape codes defined above.

    // format is called for every console log message
    @Override
    public String format(LogRecord record)  {
        // This example will print date/time, class, and log level in yellow,
        // followed by the log message and it's parameters in white .
        StringBuilder builder = new StringBuilder();
        /** To get Colors (Not working)
         * builder.append("$");
         * builder.append("{");
         * builder.append(getLogLevelColor(record.getLevel()));
         * builder.append("}");
         */


        builder.append("[");
        builder.append(new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(record.getMillis())));
        builder.append("]");

        builder.append(" [");
        builder.append(record.getLevel().getName());
        builder.append("]");

        builder.append(" - ");
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null)  {
            builder.append(" - ");
            for (int i = 0; i < params.length; i++)  {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        if (record.getThrown() != null) {
            builder.append(" - ");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            builder.append(sw.toString());
        }

        /** To get Colors (Not working)
         * builder.append("$");
         * builder.append("{");
         * builder.append(ANSI_RESET);
         * builder.append("}");
         */
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Retourne le code ANSI de la couleur associé au niveau de log fourni en paramètre
     *
     * @param level Niveau de log à évaluer
     * @return Couleur associée au niveau de log
     */
    public String getLogLevelColor(Level level) {
        switch (level.getName()) {
            case "SEVERE":
                return ANSI_RED;
            case "FINE":
                return ANSI_GREEN;
            default:
                return ANSI_RESET;
        }
    }

}