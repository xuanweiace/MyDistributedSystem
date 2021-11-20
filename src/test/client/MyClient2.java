package test.client;

import top.xwace.ObjValue;
import top.xwace.loadbalance.Balancer;
import top.xwace.service.BeanContext;
import top.xwace.service.WareHouse;

import java.rmi.RemoteException;

public class MyClient2 {

    public static void main(String[] args) {
        Balancer service = BeanContext.getBalancerLocal("BalanceService");
        WareHouse[] tasks = new WareHouse[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new WareHouse("id", i);
            tasks[i].put("sponsor","zxz");
            tasks[i].put("type", "passmsg");
            tasks[i].put("Iam","t" + i);
        }
        try {
            ObjValue result = service.setTask("WorkerService", 3, tasks);
            System.out.println(result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
