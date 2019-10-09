package com.chenhao.sell.Utils;

public class MathUtil
{
    public static final double PAY_SCALE = 0.01;
/**
* @title 判断两个double是否相当
* @param num1
* @param num2
* @author Chenhao
* @date 2019/10/9
*/
    public static Boolean doubleEquals(Double num1,Double num2)
    {
        //计算结果取绝对值
        double result = Math.abs(num1 - num2);
        /**精度小于0.01,则相等*/
        if(result<PAY_SCALE)
        {
            return true;
        }else {
            return false;
        }
    }
}
