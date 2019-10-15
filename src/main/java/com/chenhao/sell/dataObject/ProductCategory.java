package com.chenhao.sell.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//与表名对应，_变为驼峰
@Entity
@DynamicUpdate //更新的时候会让mysql自动写入当前时间，即便pojo中的时间是旧时间
@Data
public class ProductCategory
{
    /**在存入数据库的时候加入自增id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;


}
