<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>PS5价格跟踪</title>
    <link rel='icon' href='/img/flash.png' type=‘image/x-ico’/>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="styleSheet" href="/css/style.css"/>

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
</head>
<body>
<div>
    <div id="title-bar">
        <h3 class="container">
            PS5价格跟踪
            <span style="font-size: xx-small; float:right;"> ${updateTime}</span>
        </h3>
    </div>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="price-desc-wrapper">
                    <div class="price-desc">
                        <h4>
                            光驱版
                        </h4>
                        <h4>
                            最低均价：${opticalResultData.minAveragePrice}元
                            <#if opticalChartData.priceChange gt 0>
                                <span class="price-change-red">&nbsp;+${opticalChartData.priceChange}元</span>
                            <#else>
                                <span class="price-change-green">&nbsp;${opticalChartData.priceChange}元</span>
                            </#if>
                        </h4>
                        <h5>
                            均价：${opticalResultData.averagePrice}元 &nbsp; 最低价：${opticalResultData.minPrice}元
                        </h5>
                    </div>
                    <div class="price-desc">
                        <h4>
                            数字版
                        </h4>
                        <h4>
                            最低均价：${digitalResultData.minAveragePrice}元
                            <#if digitalChartData.priceChange gt 0>
                                <span class="price-change-red">&nbsp;+${digitalChartData.priceChange}元</span>
                            <#else>
                                <span class="price-change-green">&nbsp;${digitalChartData.priceChange}元</span>
                            </#if>
                        </h4>
                        <h5>
                            均价：${digitalResultData.averagePrice}元 &nbsp; 最低价：${digitalResultData.minPrice}元
                        </h5>
                    </div>
                </div>
                <div class="chart-wrapper">
                    <div class="chart-border">
                        <canvas id="chart-1"></canvas>
                    </div>
                    <div class="chart-border">
                        <canvas id="chart-2"></canvas>
                    </div>
                </div>
                <table class="table table-striped price-list">
                    <thead>
                    <tr>
                        <th>PS5光驱版</th>
                        <th>价格</th>
                        <th>平台</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list opticalResultData.productDataList as productData>
                        <tr>
                            <td>${productData.title}</td>
                            <td>${productData.price}</td>
                            <td>${productData.platform}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <table class="table table-striped price-list">
                    <thead>
                    <tr>
                        <th>PS5数字版</th>
                        <th>价格</th>
                        <th>平台</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list digitalResultData.productDataList as productData>
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
</div>


<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script>
    const opticalAveragePriceArray = [];
    const opticalMinAveragePriceArray = [];
    const opticalMinPriceArray = [];
    const opticalLabelArray = [];

    const digitalAveragePriceArray = [];
    const digitalMinAveragePriceArray = [];
    const digitalMinPriceArray = [];
    const digitalLabelArray = [];

    $(document).ready(function () {

        <#list opticalChartData.averagePriceList as price>
        opticalAveragePriceArray.push(${price});
        </#list>

        <#list opticalChartData.minAveragePriceList as price>
        opticalMinAveragePriceArray.push(${price});
        </#list>

        <#list opticalChartData.minPriceList as price>
        opticalMinPriceArray.push(${price});
        </#list>

        <#list opticalChartData.labelList as label>
        opticalLabelArray.push("${label}");
        </#list>

        var ctx1 = document.getElementById('chart-1').getContext('2d');
        const chart1 = new Chart(ctx1, {
            type: 'line',
            data: {
                labels: opticalLabelArray,
                datasets: [{
                    label: "均价",
                    fill: false,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: opticalAveragePriceArray
                }, {
                    label: "取市场20%低价计算均价",
                    fill: false,
                    backgroundColor: 'rgb(0, 152, 288)',
                    borderColor: 'rgb(0, 152, 288)',
                    data: opticalMinAveragePriceArray
                }, {
                    label: "最低价",
                    fill: false,
                    backgroundColor: 'rgb(38, 184, 183)',
                    borderColor: 'rgb(38, 184, 183)',
                    data: opticalMinPriceArray
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "光驱版价格趋势"
                },
                hover: {
                    animationDuration: 0
                }
            }
        });

        <#list digitalChartData.averagePriceList as price>
        digitalAveragePriceArray.push(${price});
        </#list>

        <#list digitalChartData.minAveragePriceList as price>
        digitalMinAveragePriceArray.push(${price});
        </#list>

        <#list digitalChartData.minPriceList as price>
        digitalMinPriceArray.push(${price});
        </#list>

        <#list digitalChartData.labelList as label>
        digitalLabelArray.push("${label}");
        </#list>

        var ctx2 = document.getElementById('chart-2').getContext('2d');
        const chart2 = new Chart(ctx2, {
            type: 'line',
            data: {
                labels: digitalLabelArray,
                datasets: [{
                    label: "均价",
                    fill: false,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: digitalAveragePriceArray
                }, {
                    label: "取市场20%低价计算均价",
                    fill: false,
                    backgroundColor: 'rgb(0, 152, 288)',
                    borderColor: 'rgb(0, 152, 288)',
                    data: digitalMinAveragePriceArray
                }, {
                    label: "最低价",
                    fill: false,
                    backgroundColor: 'rgb(38, 184, 183)',
                    borderColor: 'rgb(38, 184, 183)',
                    data: digitalMinPriceArray
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "数字版价格趋势"
                },
                hover: {
                    animationDuration: 0
                }
            }
        });
    });
</script>

</body>
</html>