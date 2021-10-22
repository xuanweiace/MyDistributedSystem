package top.xwace.service;

import top.xwace.ObjValue;
import top.xwace.park.Park;
import top.xwace.park.ParkLeader;

import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.List;
import java.rmi.RemoteException;//ServiceException
import java.io.Serializable;
import java.util.zip.CRC32;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Date;

public class ParkService extends UnicastRemoteObject implements Park {
//    private ParkObjValue parkinfo = new ParkObjValue();
//    private static ObjValue hbinfo = new ObjValue();
    //private Lock lk = new ReentrantLock();
//    private ReadWriteLock rwlk = new ReentrantReadWriteLock();
    private ParkLeader pl = null;

    public ParkService(String host, int port, String[][] servers, String parkService) throws RemoteException{
        System.out.println("zxzDebug: [ParkService的构造函数] new 了一个 ParkService");
        this.pl = new ParkLeader(host,port,servers,parkService);
        //暂时不考虑高可用
//        pl.wantBeMaster(this);//选一个master出来
    }

    @Override
    public boolean register(String host, int port, String sn) {
        ObjValue objValue = new ObjValue();
        objValue.setString("host", host);
        objValue.setString("port", Integer.toString(port));
        objValue.setString("sn", sn);
        pl.regisServiceObj(objValue);
        return true;
    }

    @Override
    public boolean unregister(String host, int port, String sn) {
        ObjValue objValue = new ObjValue();
        objValue.setString("host", host);
        objValue.setString("port", Integer.toString(port));
        objValue.setString("sn", sn);
        pl.unregisServiceObj(objValue);
        return false;
    }
}
