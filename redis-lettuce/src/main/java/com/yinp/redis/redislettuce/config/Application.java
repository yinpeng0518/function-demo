/*
 * Copyright(C) 2019 FUYUN DATA SERVICES CO.,LTD. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 该源代码版权归属福韵数据服务有限公司所有
 * 未经授权，任何人不得复制、泄露、转载、使用，否则将视为侵权
 */
package com.yinp.redis.redislettuce.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By YinP
 * 2020/6/10
 */
public class Application {

    private static volatile Application instance = null;

    private final long firstUseTime;

    private Application() {
        this.firstUseTime = System.currentTimeMillis();
    }

    public static Application getInstance() {
        if (instance == null) {
            synchronized (Application.class) {
                if (instance == null) {
                    instance = new Application();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list=null;
        list.size();
    }
}
