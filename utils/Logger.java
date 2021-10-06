package com.stocked.utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static FileHandler fileHandler = fileHandler();

    private static FileHandler fileHandler(){
        SimpleDateFormat fileTime = new SimpleDateFormat("yyyy/MM/dd");
        try {
            // On initialise le chemin vers le fichier de log
            String path = "logs/" + fileTime.format(Calendar.getInstance().getTime()) + ".log";
            File file = new File(path);
            // Si le fichier / chemin n'existe pas, on créé le fichier au bon endroit
            if (!file.exists()) file.getParentFile().mkdirs();
            // Puis on paramètre le FileHandler
            fileHandler = new FileHandler(path, 8096, 1, true);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            fileHandler.setFormatter(new LogFormatter());
        } catch (IOException e) {
            JOptionPane.showInternalMessageDialog(null, "Impossible de créer un fichier log " + e.getMessage());
        }
        return fileHandler;
    }

    public static void severe(String msg){
        logger.severe(msg);
    }

    public static void warning(String msg){
        logger.warning(msg);
    }

    public static void fine(String msg){
        logger.fine(msg);
    }

    public static void exit(){
        for(Handler h: logger.getHandlers()) {
            h.close();   //must call h.close or a .LCK file will remain.
        }
    }

}

