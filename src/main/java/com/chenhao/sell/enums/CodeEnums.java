package com.chenhao.sell.enums;

/**
* @title 枚举类型接口
* @description 这个类的枚举值必须实现下列方法
* @author Chenhao
* @date 2019/10/11
*/
public interface CodeEnums<T> {
    //code的类型较多，所以使用泛型
    T getCode();
}
