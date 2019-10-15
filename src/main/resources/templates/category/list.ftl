
<!--【ftl】所有的效果和样式来自iBootStrap-->
<html>
<#include "../common/header.ftl">
 <body>
  <div id="wrapper" class="toggled">
    <#--sidebar-->
    <#include "../common/nav.ftl">
    <#--content-->
    <div id="page -content-wrapper">
        <div class="container">
            <div class="row clearfix">

            <#--显示结果表格-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目ID</th>
                            <th>名称</th>
                            <th>type</th>
                            <th>创建状态</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                <#list categoryList as category>
                <tr class="success">
                    <td>${category.categoryId}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.categoryType}</td>
                    <td>${category.createTime}</td>
                    <td>${category.updateTime}</td>
                    <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
  </div>
 </body>
</html>