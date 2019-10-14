<html>
<head>
    <meta charset="UTF-8">
    <title>操作错误</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误!
                </h4> <strong>${errorMsg}，即将在3s内跳转回订单面板... <a href="${returnUrl}" class="alert-link">或点击此处</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    setTimeout('location.href="${returnUrl}"',3000);
</script>