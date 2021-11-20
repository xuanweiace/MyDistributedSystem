package top.xwace.service;

import top.xwace.park.ParkActive;

import java.rmi.RemoteException;

public interface Worker extends ParkActive, CorrespondPark {
    //已进一步抽象成CorrespondPark接口
//    public boolean registerToPark() throws RemoteException;
    //start 就是 waitWorking
    public boolean start() throws RemoteException;
//    public void waitWorking(String host, int port, String workerType) throws RemoteException;
//    public void waitWorkingByService(String host, int port) throws RemoteException;
    public WareHouse receiveTask(WareHouse inhouse) throws RemoteException;

}
