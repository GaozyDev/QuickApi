<html>
<link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3>
                平均价格：${average} 最低价：${min}
            </h3>
            <table class="table">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>价格</th>
                    <th>平台</th>
                </tr>
                </thead>
                <tbody>
                <#list productInfos as productInfo>
                    <tr>
                        <td>${productInfo.title}</td>
                        <td>${productInfo.price}</td>
                        <td>${productInfo.platform}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
