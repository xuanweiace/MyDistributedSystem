package top.xwace.service;

import top.xwace.park.ParkActive;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BeanService {
    final static void putBean(String TPYFWYM, boolean TPYRZDY, int TPYDK, String rmname, ParkActive paobj)
    {
        try{
            LocateRegistry.getRegistry(TPYDK).list();
            System.out.println("LocateRegistry.getRegistry(TPYDK).list())成功！！说明当前端口已被占用，putBean失败");
        }catch(Exception ex){
            try{
                System.out.println("BeanService.putBean()异常了，说明该端口没有被占用，可以正常put");
//                UnicastRemoteObject.exportObject(paobj, 0);
                System.out.println("正在createRegistry");
                Registry rgsty = LocateRegistry.createRegistry(TPYDK);//getRegistry(TPYDK);
                System.out.println("createRegistry成功");

                System.out.println("正在rgsty.rebind");
                rgsty.rebind(rmname,paobj);
                System.out.println("rgsty.rebind成功");
            }
            catch(Exception e){
                System.out.println("出错!");
//                LogUtil.info("[ObjectService]", "[regObject]", "[Error Exception:]", e);
            }
        }
    }

    final static ParkActive getBean(String TPYYM,  int TPYDK, String rmname) throws RemoteException
    {
        try{
//            if(ConfigContext.getTMOT()>0l) //timeout
//                System.setProperty(ConfigContext.getQSXYSJ(), ConfigContext.getTMOT()+"");
            System.out.println("zxzDebug: [BeanService.getBean()]: " + "TPYYM: "+TPYYM+", TPYDK: "+TPYDK+", rmname: "+rmname);
//            return (ParkActive)Naming.lookup(ConfigContext.getProtocolInfo(TPYYM,TPYDK,rmname));
            System.out.println("正在getRegistry("+TPYYM + ", "+TPYDK + ")");
            Registry registry = LocateRegistry.getRegistry(TPYYM, TPYDK);
            System.out.println("getRegistry("+TPYYM + ", "+TPYDK + ")" + "完成");
            String[] list = registry.list();
            for (String s : list) {
                System.out.println(s);
            }
            return (ParkActive)registry.lookup(rmname);
        }catch(Exception e){
            System.out.println("BeanService.getBean()异常了");
//			LogUtil.info("[BeanService]", "[getBean]", "getBean:"+e.getClass());
            if(e instanceof RemoteException)
                throw (RemoteException)e;
            else{
                System.out.println("出错了!");
                //e.printStackTrace();
//                LogUtil.info("[ObjectService]", "[getBean]", "[Error Exception:]", e);
            }
        }
        return null;
    }
}
