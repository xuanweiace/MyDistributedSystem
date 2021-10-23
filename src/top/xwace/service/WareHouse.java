package top.xwace.service;

import top.xwace.ObjValue;

import java.util.ArrayList;

//数据交互
public class WareHouse extends ObjValue {

    public WareHouse(){
        super();
    }
    public WareHouse(Object k, Object v) {
        this();
        this.put(k,v);
    }


    public static void main(String[] args) {
        WareHouse wareHouse = new WareHouse("1number", 123);
        wareHouse.put("q","w");
        System.out.println(wareHouse);
    }
}
