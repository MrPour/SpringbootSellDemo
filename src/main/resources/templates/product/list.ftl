
<!--【ftl】所有的效果和样式来自iBootStrap-->
<html>
<#include "../common/header.ftl">
 <body>
  <div id="wrapper" class="toggled">
    <#--sidebar-->
    <#include "../common/nav.ftl">
    <#--content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">

            <#--显示结果表格-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建状态</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>

                        <tbody>
                <#list pageResult.content as productInfo>
                <#if productInfo.productStatus == 0>
                <tr class="success">
                    <td>${productInfo.productId}</td>
                    <td>${productInfo.productName}</td>
                    <td><img src="${productInfo.productIcon} " height="100" width="100"></td>
                    <td>${productInfo.productPrice}</td>
                    <td>${productInfo.productStock}</td>
                    <td>${productInfo.productDescription}</td>
                    <td>${productInfo.categoryType}</td>
                    <td>${productInfo.createTime}</td>
                    <td>${productInfo.updateTime}</td>
                    <td><a href="/sell/seller/product/index?productId=${productInfo.productId}&page=${currentPageNo}">修改</a></td>
                    <td><a href="/sell/seller/product/off_sale?productId=${productInfo.productId}&page=${currentPageNo}">下架</a>
                    </td>
                </tr>
                <#else>
                <tr class="error">
                    <td>${productInfo.productId}</td>
                    <td>${productInfo.productName}</td>
                    <td><img src="${productInfo.productIcon}" height="100" width="100""></td>
                    <td>${productInfo.productPrice}</td>
                    <td>${productInfo.productStock}</td>
                    <td>${productInfo.productDescription}</td>
                    <td>${productInfo.categoryType}</td>
                    <td>${productInfo.createTime}</td>
                    <td>${productInfo.updateTime}</td>
                    <td><a href="/sell/seller/product/index?productId=${productInfo.productId}&page=${currentPageNo}">修改</a></td>
                    <td><a href="/sell/seller/product/on_sale?productId=${productInfo.productId}&page=${currentPageNo}">上架</a></td>
                 </tr>
                </#if>
                </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if currentPageNo lte 1>
                <li class="disable"><a href="#">上一页</a></li>
                <#else>
                <li><a href="/sell/seller/order/list?page=${currentPageNo-1}&size=${pageSize}">上一页</a></li>
                </#if>
                    <#--1~总页数作为装index的list-->
                <#list 1..pageResult.getTotalPages() as index>
                <#--当前页灰色显示-->
                    <#if currentPageNo == index>
                    <li class="disabled"><a href="#">${index}</a></li>
                    <#else>
                    <#--否则正常显示-->
                    <#--/表示根目录=local，这是相对路径-->
                    <li><a href="/sell/seller/order/list?page=${index}&size=${pageSize}">${index}</a></li>
                    </#if>
                </#list>
                <#if currentPageNo gte pageResult.getTotalPages()>
                <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                <li><a href="/sell/seller/order/list?page=${currentPageNo+1}&size=${pageSize}">下一页</a></li>
                </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
  </div>
 </body>
</html>