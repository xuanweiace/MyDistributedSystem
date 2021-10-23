package test.client;

import top.xwace.service.BeanContext;
import top.xwace.service.WareHouse;
import top.xwace.service.Worker;

import java.rmi.RemoteException;

public class MyClient {
    public static void main(String[] args) {
        Worker service = BeanContext.getWorkerLocal("WorkerService");
        try {
            System.out.println("获得计算结果：");
            WareHouse ans = service.doTask(new WareHouse());
            System.out.println(ans);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
