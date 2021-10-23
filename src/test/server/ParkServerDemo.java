package test.server;

import top.xwace.service.BeanContext;

public class ParkServerDemo {
    public static void main(String[] args)
    {
        BeanContext.startPark();
        System.out.println("Park is ready");
    }
}
