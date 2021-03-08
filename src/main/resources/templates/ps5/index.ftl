<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>PS5</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3>
                平均价格：${resultData.averagePrice} 最低价格：${resultData.minPrice}
            </h3>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>价格</th>
                    <th>平台</th>
                </tr>
                </thead>
                <tbody>
                <#list resultData.productDataList as productData>
                    <tr>
                        <td>${productData.title}</td>
                        <td>${productData.price}</td>
                        <td>${productData.platform}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <canvas id="myChart"></canvas>
</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script>
    var checkedArray = [];
    $(document).ready(function () {

        //初始化将测试集包含的用例存在数组里面
        <#list price as item>
        checkedArray.push("${item/100}");
        </#list>

        var ctx = document.getElementById('myChart').getContext('2d');
        var chart = new Chart(ctx, {
            // 要创建的图表类型
            type: 'line',

            // 数据集
            data: {
                labels: checkedArray,
                datasets: [{
                    label: "My First dataset",
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: checkedArray,
                }]
            },

            // 配置选项
            options: {}
        });
    });
</script>


</body>
</html>