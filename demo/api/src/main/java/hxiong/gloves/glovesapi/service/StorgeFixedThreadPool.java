package hxiong.gloves.glovesapi.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StorgeFixedThreadPool {
    private static StorgeFixedThreadPool instance = null;
    private final int SIZE = 4;

    private ExecutorService fixedThreadPool;

    private StorgeFixedThreadPool() {
        fixedThreadPool = Executors.newFixedThreadPool(SIZE);
        System.out.println("存盘线程池初始化完成,容量:" + SIZE);
    }

    public ExecutorService getPool() {
        return fixedThreadPool;
    }

    //DCL  (Double Check Lock 双端捡锁机制）
    public static StorgeFixedThreadPool _new() {
        if (instance == null) {
            synchronized (StorgeFixedThreadPool.class) {
                if (instance == null) {
                    instance = new StorgeFixedThreadPool();
                }
            }
        }
        return instance;
    }
}
