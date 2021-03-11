<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>${title}</title>

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
            <h2>
                ${title}
            </h2>
            <h3>
                均价：${resultData.averagePrice}元 &nbsp; 最低价：${resultData.minPrice}元
            </h3>
            <canvas id="myChart"></canvas>

            <table class="table table-striped" style="margin-top: 20px;">
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
</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script>
    const averagePriceArray = [];
    const minAveragePriceArray = [];
    const minPriceArray = [];
    const labelArray = [];
    $(document).ready(function () {

        <#list averagePriceList as price>
        averagePriceArray.push(${price});
        </#list>

        <#list minAveragePriceList as price>
        minAveragePriceArray.push(${price});
        </#list>

        <#list minPriceList as price>
        minPriceArray.push(${price});
        </#list>

        <#list labelList as label>
        labelArray.push(${label});
        </#list>

        var ctx = document.getElementById('myChart').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labelArray,
                datasets: [{
                    label: "市场均价",
                    fill: false,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: averagePriceArray
                }, {
                    label: "取市场20%低价计算均价",
                    fill: false,
                    backgroundColor: 'rgb(0, 152, 288)',
                    borderColor: 'rgb(0, 152, 288)',
                    data: minAveragePriceArray
                }, {
                    label: "最低价",
                    fill: false,
                    backgroundColor: 'rgb(38, 184, 183)',
                    borderColor: 'rgb(38, 184, 183)',
                    data: minPriceArray
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "价格趋势"
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            min: 3000,
                            max: 6000,
                            stepSize: 500
                        },
                    }]
                }
            }
        });
    });
</script>

</body>
</html>