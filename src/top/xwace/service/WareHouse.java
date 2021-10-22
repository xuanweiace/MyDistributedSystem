package top.xwace.service;

import top.xwace.ObjValue;

//数据交互
public class WareHouse extends ObjValue {

    public WareHouse(){
        super();
    }
    public WareHouse(Object k, Object v) {
        this();
        this.put(k,v);
    }

}
