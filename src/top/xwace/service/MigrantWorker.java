package top.xwace.service;

import top.xwace.park.Park;
import top.xwace.park.ParkStatg;
//ArithmeticWorker，只负责四则运算
public class MigrantWorker implements ParkStatg, Worker {
    String host,workerType,workerjarname;
    int port;

    void waitWorkingByService(String host, int port, String workerType)//remove protected
    {
        //String[] wkcfg = ConfigContext.getWorkerConfig();
        //startWorker(wkcfg[0], Integer.parseInt(wkcfg[1]), sn, mwk);
        this.host = host;
        this.port = port;
        this.workerType = workerType;
        //BeanContext.startWorker(host, port, workerType, this);
        System.out.println("zxzDebug: [MigrantWorker.waitWorkingByService()] " + this.getClass().getName());
        //TODO:这一条？？？
//        BeanContext.startWorker(host, port, workerType, this, this.getClass().equals(MigrantWorker.class));
//        ParkPatternExector.createWorkerTypeNode(workerType, host+":"+port);
    }

    @Override
    public boolean registerToPark() {
        Park park = BeanContext.getPark();
        park.register(host, port, "MyMigrantWorker");
        return true;
    }

    @Override
    public void waitWorking(String host, int port, String workerType) {

    }

    @Override
    public void waitWorkingByService(String host, int port) {

    }
}
