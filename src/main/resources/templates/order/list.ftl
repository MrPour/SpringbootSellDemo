
<!--【ftl】所有的效果和样式来自iBootStrap-->
<html>
<head>
    <meta charset="UTF-8">
    <title>买家订单列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">">
</head>
<body>
<div class="container">
    <div class="row clearfix">

        <#--显示结果表格-->
        <div class="col-md-12 column">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>
                        订单ID
                    </th>
                    <th>
                        姓名
                    </th>
                    <th>
                        手机号
                    </th>
                    <th>
                        地址
                    </th>
                    <th>
                        金额
                    </th>
                    <th>
                        订单状态
                    </th>
                    <th>
                        支付状态
                    </th>
                    <th>
                        创建时间
                    </th>
                    <th colspan="2">
                        操作
                    </th>
                </tr>
                </thead>

                <tbody>
                <#list pageResult.content as orderDTO>
                <tr>
                    <td>
                        ${orderDTO.orderId}
                    </td>
                    <td>
                        ${orderDTO.buyerName}
                    </td>
                    <td>
                        ${orderDTO.buyerPhone}
                    </td>
                    <td>
                        ${orderDTO.buyerAddress}
                    </td>
                    <td>
                        ${orderDTO.orderAmount}
                    </td>
                    <td>
                        ${orderDTO.getOrderStatusEnum().msg}
                    </td>
                    <td>
                        ${orderDTO.getPayStatusEnum().msg}
                    </td>
                    <td>
                        ${orderDTO.createTime}
                    </td>
                    <td>
                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}&page=${currentPageNo}">详情</a>
                    </td>
                    <td>
                    <#if orderDTO.orderStatus != 2>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}&page=${currentPageNo}">取消</a>
                    </#if>
                    </td>
                </tr>
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
</body>
</html>