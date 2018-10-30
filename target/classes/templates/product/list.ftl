<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include  "../common/nav.ftl">
<#--主题内容-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>图片预览</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list productInfoPage.getContent() as productInfo>
                <tr>
                    <td>${productInfo.productId}</td>
                    <td>${productInfo.productName}</td>
                    <td><img style="width: 150px;width: 150px" src="${productInfo.productIcon}"></td>
                    <td>${productInfo.productPrice}</td>
                    <td>${productInfo.productStock}</td>
                    <td>${productInfo.productDescription}</td>
                    <td>${productInfo.categoryType}</td>
                    <td>${productInfo.getProductStatusEnums().message}</td>
                    <td>${productInfo.createTime}</td>
                    <td>${productInfo.updateTime}</td>
                    <td>
                        <a href="/sell/seller/product/addProduct?productId=${productInfo.productId}">修改</a>
                         <#if productInfo.productStatus = 0>
                             <a href="/sell/seller/product/lowerFrame?productId=${productInfo.productId}">下架</a>
                         </#if>
                        <#if productInfo.productStatus = 1>
                             <a href="/sell/seller/product/upFrame?productId=${productInfo.productId}">上架</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if currentPage lte 1>
                <li class="disabled"><a href="#">上一页</a></li>
                <#else >
                 <li><a href="/sell/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                </#if>
                <#list 1..productInfoPage.getTotalPages() as index>
                    <#if currentPage == index>
                         <li class="disabled"><a href="#" >${index}</a></li>
                    <#else>
                         <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                    </#if>
                </#list>
                <#if currentPage gte productInfoPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else >
                    <li ><a href="/sell/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>