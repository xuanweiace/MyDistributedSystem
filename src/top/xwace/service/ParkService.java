package top.xwace.service;

import top.xwace.ObjValue;
import top.xwace.park.Park;
import top.xwace.park.ParkLeader;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;//ServiceException
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ParkService extends UnicastRemoteObject implements Park {
//    private ParkObjValue parkinfo = new ParkObjValue();
//    private static ObjValue hbinfo = new ObjValue();
    //private Lock lk = new ReentrantLock();
//    private ReadWriteLock rwlk = new ReentrantReadWriteLock();
    private ParkLeader pl = null;
    private int serviceCalledTimes = 0;
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

//    @Override
    //host:port-name
    public ObjValue findService(String sn) throws RemoteException {
        ArrayList<ObjValue> services = pl.findService(sn);
        serviceCalledTimes++;
        return selectService(services);
    }

    //去调用findService
    //TODO:下一版本改这里
    @Override
    public ObjValue setTask(String sn, int num, WareHouse... inhouses) throws RemoteException {
        ObjValue ret = new ObjValue();
        if(num == 1) {
            //TODO：inhouses还是数组[]吗？
            System.out.println("danduchuli");
        }
        ArrayList<ObjValue> services = pl.findService(sn);
        System.out.println(services);
        CompletableFuture<WareHouse>[] futures = new CompletableFuture[services.size()];

        Worker[] wks = new Worker[services.size()];
        WareHouse[] wh_pool = new WareHouse[wks.length];//存放返回的WareHouse数据，临界资源，就跟生产者消费者的缓冲池一样
        boolean[] has = new boolean[wh_pool.length];//标记是否访问过了 has sth. need to be fetched
        ExecutorService executor = Executors.newFixedThreadPool(wks.length);
        //这段不能删啊。。。得绑定到对应服务啊！！！
        for (int i = 0; i < wks.length; i++) {
            String host = services.get(i).getString("host");
            int port = services.get(i).getStringInt("port");
            //得到workerService
            Worker workerService = BeanContext.getService(Worker.class, host, port, sn);
            wks[i] = workerService;
        }
        //挨个处理任务
        int curTask = 0, finishTask = 0;
        while(finishTask < inhouses.length) {
            for(int j = 0; j<wh_pool.length; j++) {
                int finalJ = j;
                //第j个空闲
                //需要加上这一行判断，因为不然这个数据还没取走的时候，就有被占用了！！！！！！！！！
                if(wh_pool[j] == null && has[j] == false &&curTask < inhouses.length) {
                    final int finalCurTask = curTask;
                    has[j] = true;
                    //注意这种写法，有异常是不报错的！！！气死我了。。
                    futures[j] = CompletableFuture.supplyAsync(new Supplier<WareHouse>() {
                        @Override
                        public WareHouse get() {
                            System.out.println("finalJ:" + finalJ + ", finalCurTask: " + finalCurTask);
                            System.out.println("===task" + inhouses[finalCurTask].get("id") + " start===");
                            WareHouse ret = null;
                            try {
                                System.out.println("curTask[inhouse]: " + inhouses[finalCurTask]);
                                ret = wks[finalJ].receiveTask(inhouses[finalCurTask]);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            System.out.println("ret[outhouse]: " + ret);
                            System.out.println("===task" + inhouses[finalCurTask].get("id") + " finish===");
                            return ret;
                        }
                    }, executor);
                    curTask++;
                }
                else if(futures[j].isDone() && has[j]) {
                    //取出outhouse到缓冲池wh_pool[finalJ]中
                    futures[j].thenAccept((e) -> {
                        System.out.println("thenAccept: finalJ: " + finalJ);
                        wh_pool[finalJ] = e;
                    });
                    System.out.println("第"+j+"个worker完成了计算: " + wh_pool[j]);
                    //TODO: 需要单测
                    ret.put(wh_pool[j].get("id"), wh_pool[j].getCopy());
                    wh_pool[j] = null;
                    has[j] = false;
                    finishTask++;
//                    futures[j].cancel(true);
                }
            }
        }

        //好像不加也可以，因为上面的while循环就是用的finishTask去break的。
        while(finishTask < inhouses.length) {
            System.out.println("finishTask: " + finishTask);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return ret;
//        for (int i = 0; i < inhouses.length;) {
//            ObjValue service = findService(sn);
//            String host = service.getString("host");
//            int port = service.getStringInt("port");
//            //得到workerService
//            Worker workerService = BeanContext.getService(Worker.class, host, port, sn);
//            for(int j = 0; j<wks.length; j++) {
//                if(futures[j].isDone()) {
//                    i++;
//                }
//            }
//            int finalI = i;
//            futures[i] = CompletableFuture.supplyAsync(new Supplier<WareHouse>() {
//                @Override
//                public WareHouse get() {
//                    System.out.println("===task" + inhouses[finalI].get("id") + " finish===");
//                    WareHouse ret = null;
//                    try {
//                        ret = workerService.receiveTask(inhouses[finalI]);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("===task2 finish===");
//                    return ret;
//                }
//            }, executor);
//        }
    }

    public ObjValue selectService(ArrayList<ObjValue> services) {
        int len = services.size();
        return services.get(serviceCalledTimes%len);
    }
}
