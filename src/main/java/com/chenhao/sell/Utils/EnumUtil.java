package com.chenhao.sell.Utils;

import com.chenhao.sell.enums.CodeEnums;

public class EnumUtil
{
    /**
    * @title 枚举值工具
    * @description 给一个code，返回一个枚举值,<T extends CodeEnums>表示声明了变量
     * 使用这个工具类可以省略freemarker页面中的判断逻辑
    * @param code 想要查询的code值
    * @param enumClass 对应的枚举类
    * @return T 对应的枚举值
    * @author Chenhao
    * @date 2019/10/11
    */
    //【枚举】获取枚举值的巧妙运用,反射+泛型
    public static <T extends CodeEnums> T  getByCode(Integer code, Class<T> enumClass)
    {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant:enumConstants)
        {
            if (code.equals(enumConstant.getCode()))
            {
                return enumConstant;
            }
        }
        return null;
    }
}
