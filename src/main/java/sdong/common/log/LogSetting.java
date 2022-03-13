package sdong.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.FileSize;

import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LogSetting {
    // https://blog.csdn.net/weixin_42258128/article/details/81942796
    private static final Map<String, Logger> container = new HashMap<>();

    private static final int MaxHistory = 15;
    private static final FileSize TotalSizeCap = FileSize.valueOf("32GB");
    private static final FileSize MaxFileSize = FileSize.valueOf("1GB");

    public static void getLogFile(){
        LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        List<ch.qos.logback.classic.Logger> logs = lc.getLoggerList();
        //Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        //System.out.println("root:"+ rootLogger.getName());
        
        // rootLogger.detachAndStopAllAppenders();
        for(Logger log: logs){
            getLoggerAppender(log);
        }
    }

    private static void getLoggerAppender(Logger logger) {
        System.out.println("log name:" + logger.getName());
        Iterator<Appender<ILoggingEvent>> appenderIterator = logger.iteratorForAppenders();
        while (appenderIterator.hasNext()) {
            Appender<ILoggingEvent> appender = appenderIterator.next();
            System.out.println("appender:" + appender.getName());
        }
    }

    public static Logger getLogger(String name, String outputFile, Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(level);
        /*
         * Logger logger = container.get(name);
         * if (logger != null) {
         * return logger;
         * }
         * synchronized (LogSetting.class) {
         * logger = container.get(name);
         * if (logger != null) {
         * return logger;
         * }
         * logger = build(name, outputFile);
         * container.put(name, logger);
         * }
         */
        //rootLogger.detachAndStopAllAppenders();
        Logger logger = build(name, outputFile);
        return logger;
    }

    public static boolean detachAppender(String name){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        return rootLogger.detachAppender(name);
    }

    private static Logger build(String name, String outputFile) {
        // RollingFileAppender errorAppender = getAppender(name, outputFile,
        // Level.ERROR);
        RollingFileAppender infoAppender = getAppender(name, outputFile, Level.INFO);
        // RollingFileAppender warnAppender = getAppender(name,outputFile,Level.WARN);
        // RollingFileAppender debugAppender = getAppender(name,outputFile,Level.DEBUG);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        // 设置不向上级打印信息
        logger.setAdditive(false);
        // logger.addAppender(errorAppender);
        logger.addAppender(infoAppender);
        // logger.addAppender(warnAppender);
        // logger.addAppender(debugAppender);

        return logger;
    }

    /**
     * Add log appender
     * 
     * @param name       appender name
     * @param outputFile appender output file
     * @param level      appender log level
     * @return appender
     */
    public static RollingFileAppender getAppender(String name, String outputFile, Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        RollingFileAppender appender = new RollingFileAppender();

        // set level
        // LevelFilter levelFilter = getLevelFilter(level);
        // levelFilter.start();
        // appender.addFilter(levelFilter);

        // set context
        appender.setContext(context);
        // appender的name属性
        appender.setName(name);
        appender.setFile(outputFile);
        appender.setAppend(true);
        appender.setPrudent(false);

        // set policy
        SizeAndTimeBasedRollingPolicy policy = getPolicy(name);
        // 设置父节点是appender
        policy.setParent(appender);
        policy.setContext(context);
        policy.start();

        // set en
        PatternLayoutEncoder encoder = getEncoder();
        encoder.setContext(context);
        encoder.start();

        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();

        return appender;
    }

    /**
     * get Level filter
     * 
     * @param level
     * @return
     */
    public static LevelFilter getLevelFilter(Level level) {
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        return levelFilter;
    }

    /**
     * Set Rolling policy
     * 
     * @param outputFile output file
     * @return policy
     */
    public static SizeAndTimeBasedRollingPolicy getPolicy(String name) {
        // 设置文件创建时间及大小的类
        SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();

        // 最大日志文件大小
        policy.setMaxFileSize(MaxFileSize);

        // 设置文件名模式
        policy.setFileNamePattern(name + "%d{yyyy-MM-dd}.%i.log");

        // 设置最大历史记录为15条
        policy.setMaxHistory(MaxHistory);

        // 总大小限制
        policy.setTotalSizeCap(TotalSizeCap);

        return policy;
    }

    /**
     * 设置格式
     * 
     * @return encoder
     */
    public static PatternLayoutEncoder getEncoder() {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{HH:mm:ss.SSS} [%-5level] [%thread] [%logger] %msg%n");

        return encoder;
    }
}