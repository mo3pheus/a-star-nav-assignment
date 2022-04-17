package bootstrap;


import domain.*;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Driver {
    static Logger logger = LoggerFactory.getLogger(Driver.class);

    /**
     * @param args --log.file.path executionLogs --log.level INFO --difficulty easy --start 3 --dest 58 --size 10
     * @throws Exception 0 - log.file.path
     *                   1 - log.level
     *                   2 - difficulty(easy, medium, hard, selfgenerate)
     *                   3 - start (1-100)
     *                   4 - dest (1-100)
     *                   5 - size 10
     *                   6

     */
    public static void main(String[] args) throws Exception {
        configureLogging(Driver.getArgument(args, "log.file.path"),
                Driver.getArgument(args, "log.level"));
        GenerateMaze gmaze = new GenerateMaze(args);
        int[][] maze =  gmaze.getMaze();
        List<Integer> walls = gmaze.getWalls();
        logger.info("Maze and walls generated");
        Astar run = new Astar(maze, args,  walls);
        Cell result = run.findPath();
        run.printPath(result);

    }

    public static String configureLogging(String logFile, String logLevel) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFilename = logFile + "/AstarMaze.log";
        switch (logLevel) {
            case "DEBUG": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default: {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }

        System.out.println("Log files written out at " + logFilename);
        dailyRollingFileAppender.setFile(logFilename);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

//    public static String getArgument(String[] args, String argumentName) {
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].contains("--")) {
//                String arg = args[i].replaceAll("--", "");
//                if (arg.equals(argumentName)) {
//                    return args[i + 1];
//                }
//            }
//        }
//        return null;
//    }

    public static String getArgument(String[] args, String argumentName) throws ArrayIndexOutOfBoundsException, NumberFormatException{
        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i].replaceAll("--", "");
                if (arg.equals(argumentName)) {
                    return args[i + 1];
                }
                i++;
            }
            return null;
        }
        catch (NumberFormatException e1){
            System.out.println("Enter correct args values");
            return null;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Enter the last argument value");
            return null;
        }
    }
}
