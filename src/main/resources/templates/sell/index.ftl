<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--主题内容-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <h3>商户端登录</h3>
                    <form role="form" method="post" action="/sell/seller/index/login">
                        <div class="form-group">
                            <label>用户名称：</label>
                            <input type="text" class="form-control" name="sellerName" value=""/>
                        </div>
                        <div class="form-group">
                            <label>用户密码：</label>
                            <input type="password" class="form-control" name="sellerPassword" value=""/>
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