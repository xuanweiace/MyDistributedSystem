package top.xwace.service;

import top.xwace.park.Park;
import top.xwace.park.ParkStatg;

import java.util.ArrayList;

//ArithmeticWorker，只负责四则运算
public class MigrantWorker implements ParkStatg {
//    String host,workerType,workerjarname;
//    int port;

    //可接受的计算类型
    //如passmsg, addInt,
    String[] workerType;

    public MigrantWorker(String[] workerType) {
        this.workerType = workerType;
    }

//    public MigrantWorker(String host, int port, String workerType) {
//        this.host = host;
//        this.workerType = workerType;
//        this.port = port;
//    }

    public String[] getWorkerType() {
        return workerType;
    }

    public WareHouse doTask(WareHouse in) {
        //一不小心就上当了，别用==用equals！！！
        if(in.get("type").equals("passmsg")) return doMsg(in);
        if(in.get("type").equals("addInt")) return doAddInt(in);
        return new WareHouse("status","missing");//代表miss了worker的所有计算服务
    }

    private WareHouse doAddInt(WareHouse in) {
        int[] data = (int[]) in.get("data");
        int ans = 0;
        for(int i = 0; i<data.length; i++) {
            ans += data[i];
        }
        WareHouse ret = new WareHouse("status", "success");
        ret.put("retData",ans);
        return ret;
    }

    public WareHouse doMsg(WareHouse in) {
        WareHouse ret = new WareHouse("status", "success");//receive message
        ArrayList keys = in.getObjNames();
        for (Object o : keys) {
            if(o.equals("type")) continue;
            System.out.println("msg: {key: " + o + ", value: " + in.get(o) + "}");
            ret.put(o, in.get(o));
        }
        return ret;
    }

//
//    void waitWorkingByService(String host, int port, String workerType)//remove protected
//    {
//        //String[] wkcfg = ConfigContext.getWorkerConfig();
//        //startWorker(wkcfg[0], Integer.parseInt(wkcfg[1]), sn, mwk);
//        this.host = host;
//        this.port = port;
//        this.workerType = workerType;
//        //BeanContext.startWorker(host, port, workerType, this);
//        System.out.println("zxzDebug: [MigrantWorker.waitWorkingByService()] " + this.getClass().getName());
//        //TODO:这一条？？？
////        BeanContext.startWorker(host, port, workerType, this, this.getClass().equals(MigrantWorker.class));
////        ParkPatternExector.createWorkerTypeNode(workerType, host+":"+port);
//    }
//
//
//    public void waitWorkingByService(String host, int port) {
//
//    }
}
