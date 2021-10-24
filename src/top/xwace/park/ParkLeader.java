package top.xwace.park;

import top.xwace.ObjValue;

import java.util.ArrayList;

public class ParkLeader {
    boolean ismaster = true;// 第一版，不考虑高可用，因此默认是master
    private String parkservicecfg = null;
    //thisserver是长度为2的arr，分别是ip:port
    private String[] thisserver; //thishost,thisport;cur host for service and cur leader for proxy
    //一个主机的一个端口就代表一个服务器
    //配置多台服务器，用以满足缓存的高性能与可靠性
    String[][] groupserver = null;
    private ArrayList<ObjValue> registrationList = new ArrayList<>();
    public ParkLeader(String host, int port){
        thisserver = new String[]{host, ""+port};
        this.parkservicecfg = "ParkService";
        this.groupserver = new String[][]{{"localhost","1888"},{"localhost","1889"},{"localhost","1890"}};
    }
    public ParkLeader(String host, int port, String parkservicecfg){
        thisserver = new String[]{host, ""+port};
        this.groupserver = new String[][]{{"localhost","1888"},{"localhost","1889"},{"localhost","1890"}};
        this.parkservicecfg = parkservicecfg;
    }

    public ParkLeader(String host, int port, String[][] groupserver, String parkservicecfg){
        thisserver = new String[]{host, ""+port};
        this.groupserver = groupserver;
        this.parkservicecfg = parkservicecfg;
    }
    public boolean regisServiceObj(ObjValue service) {
        registrationList.add(service);
        return true;
    }

    public boolean unregisServiceObj(ObjValue objValue) {
        registrationList.remove(objValue);
//        for (int i = 0; i < registrationList.size(); i++) {
//            if(registrationList.get(i).equals(objValue)) {
//                registrationList.remove(i);
//                break;;
//            }
//        }
        return true;
    }

    public ArrayList<ObjValue> findService(String sn) {
        return findBySn(sn);
    }
    private ArrayList<ObjValue> findBySn(String sn) {
        ArrayList<ObjValue> retList = new ArrayList<>();
        for (int i = 0; i < registrationList.size(); i++) {
            if(registrationList.get(i).getString("sn").equals(sn)) {
                retList.add(registrationList.get(i));
            }
        }
        return retList;
    }
}
