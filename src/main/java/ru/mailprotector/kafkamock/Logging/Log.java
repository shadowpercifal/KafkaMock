package ru.mailprotector.kafkamock.Logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mailprotector.kafkamock.Controller.MainController;

public class Log {
    public static LogLevel CurrentLevel = LogLevel.WARNING;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static void Error(String raw){
        log(LogLevel.ERROR, raw, null);
    }

    public static void Error(String raw, Object object){
        log(LogLevel.ERROR, raw, object);
    }
    public static void Warning(String raw){
        log(LogLevel.WARNING, raw, null);
    }

    public static void Warning(String raw, Object object){
        log(LogLevel.WARNING, raw, object);
    }
    public static void Info(String raw){
        log(LogLevel.INFO, raw, null);
    }

    public static void Info(String raw, Object object){
        log(LogLevel.INFO, raw, object);
    }
    public static void Debug(String raw){
        log(LogLevel.DEBUG, raw, null);
    }

    public static void Debug(String raw, Object object){
        log(LogLevel.DEBUG, raw, object);
    }
    private static void log(LogLevel level, String raw, Object object){
        if (CurrentLevel.ordinal() <= level.ordinal()){
            try {
                System.out.println(raw + (object == null?"":"\nObject provided:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)));
            } catch (JsonProcessingException e) {
                System.out.println(raw + " \nObject could not be provided: Error parsing object");
            }
        }
    }
}
