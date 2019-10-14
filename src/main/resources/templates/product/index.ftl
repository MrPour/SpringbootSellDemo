
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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>商品名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>商品价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>商品库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>描述信息</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>商品图片</label>
                            <img src="${(productInfo.productIcon)!''}" height="250" width="350">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"  />
                        </div>
                        <div class="form-group">
                            <label>商品类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categories as category>

                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected</#if>>
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <#--传递隐藏字段id-->
                        <div class="form-group"><input hidden name="productId" type="text" value="${productInfo.productId}"></div>
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