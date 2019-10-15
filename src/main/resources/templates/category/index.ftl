
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
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}"  />
                        </div>

                        <div class="form-group"><input hidden name="categoryId" type="text" value="${(category.categoryId)!''}"></div>
                       <div>
                           <button type="submit" class="btn btn-default">提交修改</button>
                       </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>