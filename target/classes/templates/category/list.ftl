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
                            <th>商品类目id</th>
                            <th>类目名称</th>
                            <th>类目编号</th>
                            <th>创建时间</th>
                            <th>更新时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list categoryList as category>
                <tr>
                    <td>${category.categoryId}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.categoryType}</td>
                    <td>${category.createTime}</td>
                    <td>${category.updateTime}</td>
                    <td> <a href="/sell/seller/category/addCategory?categoryId=${category.categoryId}">修改</a></td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <#--<div class="col-md-12 column">-->
                    <#--<ul class="pagination pull-right">-->
                <#--<#if currentPage lte 1>-->
                <#--<li class="disabled"><a href="#">上一页</a></li>-->
                <#--<#else >-->
                 <#--<li><a href="/sell/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a></li>-->
                <#--</#if>-->
                <#--<#list 1..productInfoPage.getTotalPages() as index>-->
                    <#--<#if currentPage == index>-->
                         <#--<li class="disabled"><a href="#" >${index}</a></li>-->
                    <#--<#else>-->
                         <#--<li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>-->
                    <#--</#if>-->
                <#--</#list>-->
                <#--<#if currentPage gte productInfoPage.getTotalPages()>-->
                    <#--<li class="disabled"><a href="#">下一页</a></li>-->
                <#--<#else >-->
                    <#--<li ><a href="/sell/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a></li>-->
                <#--</#if>-->
                    <#--</ul>-->
                <#--</div>-->
            </div>
        </div>
    </div>
</div>

</body>
</html>