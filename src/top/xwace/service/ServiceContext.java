package top.xwace.service;

import top.xwace.park.ParkActive;

import java.rmi.RemoteException;

class ServiceContext extends BeanService
{
    static <I extends ParkActive> void startService(String host, int port, String sn, I i)
    {
        try{
//            System.out.println("startService: " + i.getClass().getName());
            System.out.println("[ServiceContext.startService()], port: " + port + ", sn: " + sn);//zxzDebug:
            putBean(host,false,port,sn,i);
        }catch(Exception e){
            System.out.println("startService: Catch Exception");
//            LogUtil.info("[ObjectService]", "[startService]", e);
        }
    }

    //涉及RMISecurity的
//    static <I extends ParkActive> void startService(String host, int port, String sn, I i, String cb, String pl)//
//    {
//        try{
//            putBean(host,false,port,sn,i,cb,pl,new ParkManager());
//            //pm.checkPermission(new ParkPermission("park","all"));
//        }catch(Exception e){
//            LogUtil.info("[ObjectService]", "[startService]", e);
//        }
//    }

    //获取服务，service层
    //这里虽然参数a没啥用，但是为了获取泛型I，所以要传进来
    //分布式缓存里面，sn指的是"CacheFacadeService", I: Cache.class(远程Cache的class)
    public static <I extends ParkActive> I getService(Class<I> a, String host, int port, String sn){
        I i=null;
        try{
            i=(I)getBean(host,port,sn);//具体去pojo层取实体
        }catch(RemoteException e){
            System.out.println("getService出错了");
//            LogUtil.info("[ObjectService]", "[getService]", e);
        }
        return i;
    }
}
