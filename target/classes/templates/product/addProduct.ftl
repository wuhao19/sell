<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include  "../common/nav.ftl">
<#--主题内容-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>商品Id：${(productInfo.productId)!''}</label>

                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input type="text" class="form-control" name="productDescription" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label><br>
                            <#if productInfo ??>
                                <img style="width: 200px;height: 200px" src="${(productInfo.productIcon)!''}">
                            </#if>
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" id="categoryType" class="form-control">
                                <#list productCategoryList as catregory>
                                    <option value="${catregory.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == catregory.categoryType>
                                        selected
                                        </#if>
                                    >
                                        ${catregory.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>