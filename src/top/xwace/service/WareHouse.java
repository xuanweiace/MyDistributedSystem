package top.xwace.service;

import top.xwace.ObjValue;

import java.util.ArrayList;

/**
 * zxzComment:
 * WareHouse进行数据交互
 * 对inhouse的要求：
 *   必须有id，代表发起请求的编号
 *   必须有sponsor，代表是谁发起的请求。
 *   必须有type，代表请求的计算类型是什么
 *
 * 对outhouse的要求：
 *   必须有status，代表是否成功
 */


public class WareHouse extends ObjValue {
    public final static int NOTREADY=0,READY=1,EXCEPTION=-1;
    private int status=NOTREADY;
    public WareHouse(){
        super();
    }
    public WareHouse(Object k, Object v) {
        this();
        this.put(k,v);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public WareHouse getCopy() {
        WareHouse dest = new WareHouse();
        ArrayList names = this.getObjNames();
        for (Object name : names) {
            dest.put(name, this.get(name));
        }
        return dest;
    }
//    public static

    public static void main(String[] args) {
        WareHouse wareHouse = new WareHouse("1number", 123);
        wareHouse.put("q","w");
        WareHouse copy = wareHouse.getCopy();
        System.out.println(wareHouse);
        System.out.println(copy);
        wareHouse.put("q", "e");
        System.out.println(wareHouse);
        System.out.println(copy);
    }
}
