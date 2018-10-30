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
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>订单金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDtoPage.content as orderDto>
                <tr>
                    <td>${orderDto.orderId}</td>
                    <td>${orderDto.orderName}</td>
                    <td>${orderDto.orderPhone}</td>
                    <td>${orderDto.orderAddress}</td>
                    <td>${orderDto.orderAmount}</td>
                    <td>${orderDto.getOrderStatusEnum().message}</td>
                    <td>${orderDto.getPayEnum().message}</td>
                    <td>${orderDto.createTime}</td>
                    <td><a href="/sell/seller/order/detail?orderId=${orderDto.orderId}">详情</a>
                         <#if orderDto.getOrderStatusEnum().code != 2>
                             <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}">取消</a>
                         </#if>
                    </td>

                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if currenPage lte 1>
                <li class="disabled"><a href="#">上一页</a></li>
                <#else >
                 <li><a href="/sell/seller/order/list?page=${currenPage-1}&size=${size}">上一页</a></li>
                </#if>
                <#list 1..orderDtoPage.getTotalPages() as index>
                    <#if currenPage == index>
                         <li class="disabled"><a href="#" >${index}</a></li>
                    <#else>
                         <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                    </#if>
                </#list>
                <#if currenPage gte orderDtoPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else >
                    <li ><a href="/sell/seller/order/list?page=${currenPage+1}&size=${size}">下一页</a></li>
                </#if>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.10.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var webSocket = null;
    if('WebSocket' in window)(
        webSocket=new WebSocket('ws://wuhao191919.nat300.top/sell/webSocket');
    )else {
        alert('该浏览器不支持websocket');
    }

    webSocket.onOpen =function (event) {
        console.log('建立连接');
    }

    webSocket.onClose = function (event) {
        console.log('关闭连接');
    }
    webSocket.onMessage = function (event) {
        console.log('接受到消息：' + event.data);
        //弹框提示 播放音乐
        $('#MyModal').modal('show');
    }
    webSocket.onerror = function (event) {
        alert('websocket发生错误');
    }
    window.onbeforeunload = function () {
        webSocket.close();
    }
</script>
            <div class="modal fade" id="MyModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                提示
                            </h4>
                        </div>
                        <div class="modal-body">
                            你有新的订单！
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button onclick="location.reload()" type="button" class="btn btn-primary">查看新订单</button>
                        </div>
                    </div>
                </div>
            </div>
</body>
</html>