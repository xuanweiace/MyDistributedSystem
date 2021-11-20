package test.client;

import top.xwace.ObjValue;
import top.xwace.park.Park;
import top.xwace.service.BeanContext;
import top.xwace.service.WareHouse;
import top.xwace.service.Worker;

import java.rmi.RemoteException;
/*
*
*   对inhouse的要求：
*   必须有id，代表发起请求的编号
*   必须有sponsor，代表是谁发起的请求。
*   必须有type，代表请求的计算类型是什么
*
* */

public class MyClient {
    public static void main(String[] args) {
        Worker service = BeanContext.getWorkerLocal("WorkerService");
        Park park = BeanContext.getPark();
        WareHouse t0 = new WareHouse("id",0);
        t0.put("sponsor","zxz");
        t0.put("type", "passmsg");
        t0.put("Iam","t0");

        WareHouse t1 = new WareHouse("id",1);
        t1.put("sponsor","zxz");
        t1.put("type", "passmsg");
        t1.put("Iam","t1");

        WareHouse t2 = new WareHouse("id",2);
        t2.put("sponsor","zxz");
        t2.put("type", "passmsg");
        t2.put("Iam","t2");
        try {
            System.out.println("获得计算结果：");
            ObjValue result = park.setTask("WorkerService", 3, t0, t1, t2);
            System.out.println(result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //单独获取计算服务
        try {
            System.out.println("获得计算结果：");
            WareHouse ans = service.receiveTask(t0);
            System.out.println(ans);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
