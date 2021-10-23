package top.xwace.park;

import top.xwace.ObjValue;
import top.xwace.park.exception.LeaderException;

import java.rmi.RemoteException;

//提供哪些接口让别人访问
public interface Park extends ParkActive
{
    public boolean register(String host, int port, String sn) throws RemoteException;
    public boolean unregister(String host, int port, String sn)throws RemoteException;
    public ObjValue findService(String sn) throws RemoteException;
//    public ObjValue create(String domain, String node, byte[] bts, String sessionid, int auth, boolean heartbeat) throws RemoteException;//acl
//    public ObjValue update(String domain, String node, byte[] bts, String sessionid) throws RemoteException;//acl
//    public boolean update(String domain, int auth, String sessionid) throws RemoteException;
//    public ObjValue get(String domain, String node, String sessionid) throws RemoteException;
//    public ObjValue getLastest(String domain, String node, String sessionid, long version) throws RemoteException;
//    public ObjValue delete(String domain, String node, String sessionid) throws RemoteException;
//    public String getSessionId() throws RemoteException;
//    public boolean heartbeat(String[] domainnodekey, String sessionid) throws RemoteException;
//    public ObjValue getParkinfo() throws RemoteException;
//    public boolean setParkinfo(ObjValue ov) throws RemoteException;
//    public String[] askMaster() throws RemoteException;
//    public boolean askLeader() throws RemoteException, LeaderException;
}
