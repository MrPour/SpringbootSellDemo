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
        <#--上面部分--订单信息-->
        <div class="col-md-4 column">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        订单编号
                    </th>
                    <th>
                        订单总金额
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        ${orderDTO.orderId}
                    </td>
                    <td>
                        ￥${orderDTO.orderAmount}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <#--下面部分--订单详情-->
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        商品编号
                    </th>
                    <th>
                        商品名称
                    </th>
                    <th>
                        价格
                    </th>
                    <th>
                        数量
                    </th>
                    <th>
                        总金额
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetails as orderDetail>
                <tr>
                    <td>
                        ${orderDetail.productId}
                    </td>
                    <td>
                        ${orderDetail.productName}
                    </td>
                    <td>
                        ${orderDetail.productPrice}
                    </td>
                    <td>
                        ${orderDetail.productQuantity}
                    </td>
                    <td>
                        ￥${orderDetail.productPrice * orderDetail.productQuantity}
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

            <#--操作界面-->
        <div>
            <#if orderDTO.orderStatus==0>
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}&page=${currentPage}" type="button" class="btn btn-default btn-primary">完结订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}&page=${currentPage}" type="button" class="btn btn-default btn-danger">取消订单</button></a>
            </#if>
        </div>
    </div>
</div>
    </div>
  </div>
 </body>
</html>