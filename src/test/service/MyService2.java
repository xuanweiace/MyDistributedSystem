package test.service;

import top.xwace.service.WorkerService;

import java.rmi.RemoteException;

public class MyService2 {
    public static void main(String[] args) {
        WorkerService workerService = null;
        try {
            workerService = new WorkerService("localhost", 2001, "WorkerService", "passmsg", "addInt");
            workerService.start();
            System.out.println(workerService.getClass().getName() + " is ready.");
        } catch (RemoteException e) {
            System.out.println("workerService.registerToPark() 出错了");
        }
    }
}
