package test.server;

import top.xwace.service.BeanContext;

public class LoadBalanceDemo {
    public static void main(String[] args) {
        BeanContext.startBalancer();
    }
}
