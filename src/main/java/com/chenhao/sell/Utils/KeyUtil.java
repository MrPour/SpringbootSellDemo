package com.chenhao.sell.Utils;

import java.util.Random;

public class KeyUtil
{
    //并发条件下可能重号
    public synchronized static String createKey()
    {
        Random random = new Random();
        /**生成六位随机数*/
        int key = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(key);
    }
}
