package top.xwace.utils;


import top.xwace.service.ConfigContext;

import java.util.logging.*;

public class LogUtil
{
    private static Logger logger;
    private static boolean fineFlag = false;
    private static boolean infoFlag = false;
    static
    {
        String levelName = ConfigContext.getLogLevel("FINE");
        fineFlag = Boolean.parseBoolean(ConfigContext.getConfig("LOG","FINE",null,"false"));
        infoFlag = Boolean.parseBoolean(ConfigContext.getConfig("LOG","INFO",null,"false"));
        logger =  Logger.getLogger("com.log");
        Level lv = Level.parse(levelName);
        logger.setLevel(lv);
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setLevel(lv);
        logger.addHandler(handler);
    }

    public static void info(Object obj)
    {
        info(obj.toString());
    }

    public static void info(String logStr)
    {
        if(infoFlag)
            logger.log(Level.INFO, logStr);
    }

    public static void info(String logStr, Throwable e)
    {
        if(infoFlag)
            logger.log(Level.INFO, logStr, e);
    }

    public static void info(String className, String methodName, Object logStr)
    {
        if(infoFlag)
            logger.logp(Level.INFO, className, methodName, getLogStr(logStr));
    }

    public static void info(String className, String methodName, Throwable e)
    {
        if(infoFlag)
            logger.logp(Level.INFO, className, methodName, "", e);
    }

    public static void info(String className, String methodName, Object logStr, Throwable e)
    {
        if(infoFlag)
            logger.logp(Level.INFO, className, methodName, getLogStr(logStr), e);
    }

    public static void fine(Object obj)
    {
        fine(obj.toString());
    }

    public static void fine(String logStr)
    {
        if(fineFlag)
            logger.log(Level.FINE, logStr);
    }

    public static void fine(String logStr, Throwable e)
    {
        if(fineFlag)
            logger.log(Level.FINE, logStr, e);
    }

    public static void fine(String className, String methodName, Object logStr)
    {
        if(fineFlag)
            logger.logp(Level.FINE, className, methodName, getLogStr(logStr));
    }

    public static void fine(String className, String methodName, Throwable e)
    {
        if(fineFlag)
            logger.logp(Level.FINE, className, methodName, "", e);
    }

    public static void fine(String className, String methodName, Object logStr, Throwable e)
    {
        if(fineFlag)
            logger.logp(Level.FINE, className, methodName, getLogStr(logStr), e);
    }

    public static void warn(String className, String methodName, Object logStr)
    {
        logger.logp(Level.WARNING, className, methodName, getLogStr(logStr));
    }

    public static String getLogStr(Object logStr)
    {
        return logStr!=null?logStr.toString():null;
    }

    public static void main(String args[])
    {
        LogUtil.fine("这里是fine函数的调用入口");
        LogUtil.info("这里是info的调用入口");
    }
}