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
    public WorkerService(String host, int port, String name, String workerType) throws RemoteException {
        serviceHost = host;
        servicePort = port;
        //TODO: Type和Name有争议
        serviceName = name;
        this.worker = new MigrantWorker(host, port, workerType);
    }


    public String getServiceName() {
        return serviceName;
    }

    @Override
    public boolean registerToPark() throws RemoteException {
        Park park = BeanContext.getPark();
        park.register(worker.getHost(), worker.getPort(), serviceName);
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
    public WareHouse doTask(WareHouse inhouse) throws RemoteException {
        return new WareHouse("Hello","Distributed System!");
    }
}



