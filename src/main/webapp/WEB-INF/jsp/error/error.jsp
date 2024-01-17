<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        .clearfix:before,
        .clearfix:after {
            display: table;

            content: ' ';
        }
        .clearfix:after {
            clear: both;
        }
        body {
            background: #f0f0f0 !important;
        }
        .page-404 .outer {
            position: absolute;
            top: 0;

            display: table;

            width: 100%;
            height: 100%;
        }
        .page-404 .outer .middle {
            display: table-cell;

            vertical-align: middle;
        }
        .page-404 .outer .middle .inner {
            width: 300px;
            margin-right: auto;
            margin-left: auto;
        }
        .page-404 .outer .middle .inner .inner-circle {
            height: 300px;

            border-radius: 50%;
            background-color: #ffffff;
        }
        .page-404 .outer .middle .inner .inner-circle:hover i {
            color: #39bbdb!important;
            background-color: #f5f5f5;
            box-shadow: 0 0 0 15px #39bbdb;
        }
        .page-404 .outer .middle .inner .inner-circle:hover span {
            color: #39bbdb;
        }
        .page-404 .outer .middle .inner .inner-circle i {
            font-size: 5em;
            line-height: 1em;

            float: right;

            width: 1.6em;
            height: 1.6em;
            margin-top: -.7em;
            margin-right: -.5em;
            padding: 20px;

            -webkit-transition: all .4s;
            transition: all .4s;
            text-align: center;

            color: #f5f5f5!important;
            border-radius: 50%;
            background-color: #39bbdb;
            box-shadow: 0 0 0 15px #f0f0f0;
        }
        .page-404 .outer .middle .inner .inner-circle span {
            font-size: 5em !important;
            font-weight: 700;
            line-height: 1.2em;

            display: block;

            -webkit-transition: all .4s;
            transition: all .4s;
            text-align: center;

            color: #e0e0e0;
        }
        .page-404 .outer .middle .inner .inner-status {
            font-size: 20px;

            display: block;

            margin-top: 20px;
            margin-bottom: 5px;

            text-align: center;

            color: #39bbdb;
        }
        .page-404 .outer .middle .inner .inner-detail {
            line-height: 1.4em;

            display: block;

            margin-bottom: 10px;

            text-align: center;

            color: #999999;
        }

        .return-btn {
            padding: 10px 15px;
            margin-top: 10px;
            margin-bottom: 30px;
            display: inline-block;
            -webkit-border-radius: 5px;
            -webkit-background-clip: padding-box;
            -moz-border-radius: 5px;
            -moz-background-clip: padding;
            border-radius: 5px;
            background-clip: padding-box;
            color: #999999;
            font-weight: bold !important;
            font-size: 20px;
            font-weight: 300;
        }
        .return-btn:hover{
            color: #39bbdb!important;
            background-color: #f5f5f5;
            box-shadow: 0 0 0 1px #39bbdb;
        }
    </style>
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="page-404">
    <div class="outer">
        <div class="middle">
            <div class="inner">
                <!--BEGIN CONTENT-->
                <div class="inner-circle"><i class="fa fa-cogs"></i><span>ERROR</span></div>
                <span class="inner-status">Opps! Internal Server Error!</span>
                <span class="inner-detail">Unfortunately we're having trouble loading the page you are looking for. Please come back in a while.
                    <a href="/sample/main.do" class="return-btn"><i class="fa fa-home"></i>Home</a>
                </span>
                <!--END CONTENT-->
            </div>
        </div>
    </div>
</div>
</div>
</body>
<script>
    console.log('${url}');
    console.log('${error}');
</script>
</html>