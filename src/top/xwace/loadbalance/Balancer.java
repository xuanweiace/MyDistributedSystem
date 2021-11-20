package top.xwace.loadbalance;

import top.xwace.ObjValue;
import top.xwace.park.ParkActive;
import top.xwace.service.CorrespondPark;
import top.xwace.service.WareHouse;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Balancer extends ParkActive, CorrespondPark {
    public ObjValue setTask(String sn, int num, WareHouse... inhouses) throws RemoteException;
    public ArrayList<ObjValue> askServiceList(String sn, int num) throws RemoteException;
}
