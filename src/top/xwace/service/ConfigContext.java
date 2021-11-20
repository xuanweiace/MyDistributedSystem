package top.xwace.service;


import top.xwace.ObjValue;
import top.xwace.utils.LogUtil;
import top.xwace.utils.XmlUtil;

import java.util.ArrayList;
import java.util.Properties;
import java.text.DateFormat;
import java.util.Date;
import java.io.File;

/**
 * zxzComment:
 * getConfig是获取ip和端口的数组
 * getService是获取服务名比如：ParkService
 */
public class ConfigContext
{
    static String configFile = "config.xml";
    private static ObjValue USERS = null;

    //远程调用协议
    static String getYCDYXY(){//rmi://  rmi协议
        return "rmi://";
    }

    static String getProtocolInfo(String ym, int dk, String mc){
        return getYCDYXY()+ym+":"+dk+"/"+mc;
    }

    static long getSecTime(Double hours)
    {
        Double t = hours*3600*1000;
        return t.longValue();
    }

    static String[][] getParkConfig()
    {
        String servers = getConfig("PARK","SERVERS",null);

        System.out.println("[ConfigContext.getParkConfig()]" + servers);//zxzDebug:
        return getServerFromStr(servers);
    }

    static String getParkService()
    {
        System.out.println("[ConfigContext.getParkService()]" + "ParkService");//zxzDebug:

    return getConfig("PARK","SERVICE",null);
    }

    static String[] getCtorService()
    {
        return getConfig("CTOR","CTORSERVERS",null).split(":");
    }

    static String[] getWorkerConfig()
    {
        return getConfig("WORKER","SERVERS",null).split(":");
    }

    static String[][] getCacheConfig()
    {
        String servers = getConfig("CACHE","SERVERS",null);
        System.out.println("[ConfigContext.getCacheConfig()]" + servers);//zxzDebug:
        return getServerFromStr(servers);
    }

    static String getCacheService()
    {
        System.out.println("zxzDebug: [ConfigContext.getCacheService()]" + "CacheService");
        return getConfig("CACHE","SERVICE",null);
    }

    static String[] getCacheFacadeConfig()
    {
        return getConfig("CACHEFACADE","SERVERS",null).split(":");
    }

    public static String getCacheFacadeService()
    {
        //CacheFacadeService
        return getConfig("CACHEFACADE","SERVICE",null);
    }

    static int getInitServices()
    {
        int initnum = 10;
        try{
            initnum = Integer.parseInt(getConfig("CTOR","INITSERVICES",null,"10"));
        }catch(Exception e){}
        return initnum;
    }

    static int getMaxServices()
    {
        int maxnum = 100;
        try{
            maxnum = Integer.parseInt(getConfig("CTOR","MAXSERVICES",null,"100"));
        }catch(Exception e){}
        return maxnum;
    }

    static int getParallelPattern()
    {
        return Integer.parseInt(getConfig("COMPUTEMODE","MODE","DEFAULT"));
    }

    static String getConfig(String cfgname, String cfgprop, String cfgdesc)
    {
        return getConfig(cfgname, cfgprop, cfgdesc, null);
    }

    public static String getConfig(String cfgname, String cfgprop, String cfgdesc, String defvalue)
    {
        XmlUtil xu = new XmlUtil();
        ArrayList al = xu.getXmlObjectByFile(configFile,cfgname,cfgdesc);
        String v = null;
        if(al!=null&&al.size()>0)
        {
            ObjValue cfgProps = (ObjValue)al.get(0);
            v = cfgProps.getString(cfgprop);
        }
        if(v==null)
            v=defvalue;
        //LogUtil.fine("[ConfigContext]", "[getConfig]", v);
        return v;
    }

    public static String getLogLevel(String deflevel)
    {
        XmlUtil xu = new XmlUtil();
        ArrayList al = xu.getXmlPropsByFile(configFile,"LOG","LOGLEVEL");
        Properties dbProps = (Properties)al.get(0);
        String levelName = dbProps.getProperty("LEVELNAME");

        return levelName!=null?levelName:deflevel;
    }

    static ObjValue getCacheGroupConfig()
    {
        XmlUtil xu = new XmlUtil();
        ArrayList al = xu.getXmlObjectByFile(configFile,"CACHEGROUP");
        ObjValue groups = new ObjValue();

        for(int i=0;i<al.size();i++)
        {
            ObjValue cacheProps = (ObjValue)al.get(i);
            ObjValue gp = new ObjValue();
            String gpcfgstr = cacheProps.getString("GROUP");
            for(String perstr:gpcfgstr.split(";"))
            {
                String[] perstrarr = perstr.split("@");
                gp.setObj(perstrarr[0],new Long(getDateLong(perstrarr[1])));
            }
            groups.put(gp, new Long(getDateLong(cacheProps.getString("STARTTIME"))));
        }

        LogUtil.fine("[ConfigContext]", "[getCacheConfig]", groups);
        return groups;
    }

    static String getDateLong(String dateStr)
    {
        if(dateStr!=null&&!dateStr.equals(""))
        {
            try
            {
                DateFormat dateFormat = DateFormat.getDateInstance();
                Date d = dateFormat.parse(dateStr);
                dateStr = d.getTime()+"";
                if(dateStr.length()==12)
                    dateStr = "0"+dateStr;
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return  dateStr;
    }

    static String[][] getServerFromStr(String servers)
    {
        String[] serverarr = servers.split(",");
        String[][] sarr = new String[serverarr.length][];
        for(int n=0;n<serverarr.length;n++)
        {
            String[] hostport=serverarr[n].split(":");
            sarr[n]=hostport;
        }
        return sarr;
    }

    private static ObjValue getObjFromStr(String strs)
    {
        String[] strarr = strs.split(",");
        ObjValue ov = new ObjValue();
        for(String thestr:strarr)
        {
            String[] str=thestr.split(":");
            ov.setString(str[0],str[1]);
        }

        return ov;
    }

    public static void main(String args[])
    {
        BeanContext.setConfigFile("D:\\demo\\comutil\\test\\config.xml");
        System.out.println(getParkConfig()[0][0]);
        LogUtil.fine(getCacheConfig());
        LogUtil.fine("getParallelPattern:"+getParallelPattern());
        System.out.println(getConfig("CACHEFACADE","TRYKEYSNUM",null,"500"));
    }
}