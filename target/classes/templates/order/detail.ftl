<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                            <th>订单状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDto.orderId}</td>
                            <td>${orderDto.orderAmount}</td>
                            <td>${orderDto.getOrderStatusEnum().message}</td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>商品单价</th>
                            <th>商品数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>

                    <#list orderDto.getOrderDetailList() as orderDetail>
                    <tbody>
                    <tr>
                        <td>${orderDetail.productId}</td>
                        <td>${orderDetail.productName}</td>
                        <td>${orderDetail.productPrice}</td>
                        <td>${orderDetail.productQuantity}</td>
                        <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                    </tr>
                    </tbody>
                    </#list>
                    </table>
                    <div class="container">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                        <#if orderDto.getOrderStatusEnum().code != 2>
                            <a type="button" class="btn btn-danger btn-default" href="/sell/seller/order/cancel?orderId=${orderDto.orderId}">取消订单</a>
                            <#if orderDto.getPayEnum().code != 0>
                            <a type="button" class="btn btn-default btn-success" href="/sell/seller/order/finish?orderId=${orderDto.orderId}">完结订单</a>
                            </#if>
                        </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>