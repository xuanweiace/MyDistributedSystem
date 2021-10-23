package top.xwace.service;

import top.xwace.park.Park;
import top.xwace.park.ParkStatg;
//ArithmeticWorker，只负责四则运算
public class MigrantWorker implements ParkStatg {
    String host,workerType,workerjarname;
    int port;

    public MigrantWorker(String host, int port, String workerType) {
        this.host = host;
        this.workerType = workerType;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getWorkerType() {
        return workerType;
    }

    public int getPort() {
        return port;
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
