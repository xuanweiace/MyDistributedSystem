package test;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class forfun {
    public int xx;
    public static void main(String[] args) {
        String[][] p = {{"localhost", "1888"},{"localhost", "1889"}};
        for(int i = 0; i<p.length; i++) {
            for(int j = 0; j<p[i].length; j++) System.out.println(p[i][j]);
        }
//        System.out.println(p);
        forfun A = new forfun();
        try {
//            A.test2();
//            A.test1();
//            A.test3();
            System.out.println("结束了");
            System.out.println("A.xx: " + A.xx);
        } catch (Exception e) {
            e.printStackTrace();
        }


        A.test4();

     }

    private void test4() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        System.out.println(arrayList);
        System.out.println(arrayList.subList(0,3));
    }

    public void test1() throws Exception {

        System.out.println("main函数开始执行");

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                System.out.println("===task start===");
                Thread.sleep(5000);
                System.out.println("===task finish===");
                return 3;
            }
        });
        //这里需要返回值时会阻塞主线程，如果不需要返回值使用是OK的。倒也还能接收
        Integer result=future.get();
        System.out.println("main函数执行结束");
        System.out.println("re:" + result);
//        System.in.read();

    }

    public void test2() throws Exception {
        System.out.println("main2函数开始执行");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("===task2 start===");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("===task2 finish===");
                return 5;
            }
        }, executor);
//        future.thenAccept(getresult(int e););
        future.thenAccept((e) -> {
            System.out.println("这是test2: " + e);
            this.xx = e;
//            callback();
        });
        while(!future.isDone()) {
            System.out.println("future is not Done();");
        }

        System.out.println("main2函数执行结束");
    }
    public void callback(int e) {
        System.out.println("这是test2的 callback: " + e);
    }


    public void test3() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("===task3 start===");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
            }
            System.out.println("===task3 finish===");
            System.out.println("run end ...");
            return System.currentTimeMillis();
        });

        long time = future.get();
        System.out.println("time = "+time);
    }
}
