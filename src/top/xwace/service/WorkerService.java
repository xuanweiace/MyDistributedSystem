package top.xwace.service;

import top.xwace.park.Park;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//暂时 一个Service只对应一个worker，所以ip:port是一样的。
// TODO:后续版本，provider只需要提供worker，系统去启动服务。（但是这也就是一个worker一个服务的模式了？）
public class WorkerService extends UnicastRemoteObject implements Worker{
    MigrantWorker worker;
    String serviceHost;
    int servicePort;
    String serviceName;
    //staticKey，用于把和worker去doTask无关的选项去掉，type先不去掉
    String[] staticKey = {"id","sponsor","type"};
    public WorkerService(String host, int port, String name, String... workerType) throws RemoteException {
        serviceHost = host;
        servicePort = port;
        //TODO: Type和Name有争议
        serviceName = name;
        this.worker = new MigrantWorker(workerType);
    }


    public String getServiceName() {
        return serviceName;
    }

    @Override
    public boolean registerToPark() throws RemoteException {
        Park park = BeanContext.getPark();
        park.register(serviceHost, servicePort, serviceName);
        return true;
    }

    @Override
    public boolean start() throws RemoteException {
        registerToPark();
        //TODO: 不需要经过这一层了
//        BeanContext.startWorker(serviceHost, servicePort, serviceName, );
        BeanContext.startService(serviceHost, servicePort, serviceName, this);
        return true;
    }

    @Override
    public WareHouse receiveTask(WareHouse inhouse) throws RemoteException {
        int taskid = (int) inhouse.get("id");
        //清洗数据
        boolean isFormatValid = true;
//        System.out.println("正在receiveTask");
        for (String s : staticKey) {
            if(!inhouse.containsKey(s)) {
                isFormatValid = false;
            }
            if(!s.equals("type")) inhouse.remove(s);
        }
        if(!isFormatValid) {
            System.out.println("receiveTask出错，出错原因：wronginputformat");
            return dealError(inhouse, "wronginputformat");
        }

        //检验是否在包含的计算服务内
        boolean isTypeValid = false;
        String[] type = worker.getWorkerType();
        for (String s : type) {
            if(s.equals(inhouse.get("type"))) isTypeValid = true;    //放行
        }
        if(!isTypeValid) {
            System.out.println("receiveTask出错，出错原因：wrongservicetype");
            return dealError(inhouse, "wrongservicetype") ;
        }
        //Filter之后仍然valid，则呼唤worker执行doTask

        WareHouse outhouse = worker.doTask(inhouse);

        //封装上层信息，返回给client。注意这里只需要put一下请求的id就行了，不需要sponsor了
        outhouse.put("id", taskid);
        return outhouse;
    }

    public WareHouse dealError(WareHouse wh, String type) {
        WareHouse ret = null;
        if(type == "wronginputformat") {
            ret = new WareHouse("status", "error");
            ret.put("errordetail", "wrong data format");
        }
        if(type == "wrongservicetype") {
            ret = new WareHouse("status", "error");
            ret.put("errordetail", "wrong service type");
        }
        return ret;
    }
}



