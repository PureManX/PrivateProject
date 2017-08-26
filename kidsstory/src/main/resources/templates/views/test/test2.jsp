<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
#message-text {
	min-width: 250px;
}
.header_wrapper{
	overflow: auto;
}
.header_wrapper img{
    width: 100px;
    height: 100px;
    background: white;
}
.header_wrapper {
	text-align: right;
}
.board_comp {
	background: #e4e7ed
}
.msg_left {
	margin-right: 20%;
}
.msg_right {
	margin-left: 20%;
	text-align: right;
}
.inline-block {
	display: inline-block;
}
.msg_time {
    margin-bottom: 20px;
    vertical-align: bottom;
    padding: 8px;
    width: 20%;
}
.msg_body {
	max-width: 80%;
}
.msg_footer {
	position: fixed;
	bottom: 0;
	left: 0;
	padding : 3%;
	background: white;
	border : 1px solid #faebcc;
}
#msgContent{
	text-align: left;
}
div.panel-body {
	padding-bottom: 40px;
}
</style>
<div class="panel panel-warning">
	<div class="panel-heading">
		<div class="header_wrapper">
			<img class="img-rounded pull-left"
				src="http://img1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/news/201612/14/ned/20161214111302993glut.jpg">
			<div class="pull-right">
				<h4 class="h4">개나리반 김신</h4>
				<a href="tel:010-2570-9371"><span class="glyphicon glyphicon glyphicon-earphone" aria-hidden="true"></span></a>
				<h5 class="h5">삼성파크빌</h5>
				<h5 class="h5">오전 8시 30분</h5>
			</div>
		</div>
	</div>
	<div class="panel-body">
<!-- 		<div class="msg_right"> -->
<!-- 			<div class="inline-block msg_time">오후 08:30</div> -->
<!-- 			<div class="alert alert-success inline-block">안녕하세요</div> -->
<!-- 		</div> -->
<!-- 		<div class="alert alert-success msg_right">오전 10시에 아이 감기약을 먹여야 -->
<!-- 			합니다</div> -->
<!-- 		<div class="alert alert-warning msg_left">네 알아서 잘 챙기겠습니다</div> -->
<!-- 		<div class="alert alert-warning msg_left">약 잘 먹였습니다</div> -->
<!-- 		<div class="alert alert-success msg_right">감사합니다</div> -->
	</div>
	<div class="msg_footer">
		<div class="input-group">
			<input type="text" class="form-control" id="msgContent">
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="sendMsg()">전송</button>
			</span>
		</div>
	</div>
</div>
<script type="text/javascript">
var curUser = 4;

$(document).ready(function() {
	refresh();
});

function addMsgList(msgVo) {
	var msgClass = "";
	
	if (msgVo.srcId == curUser) {
		msgClass = "msg_left";
	} else {
		msgClass = "msg_right";
	}
	
	var date = new Date(msgVo.createdDt);

	var hour = date.getHours();
	var min = date.getMinutes();
	
	var msgTime = hour + ":" + min;
	if (msgClass == "msg_right") {
		$(".panel-body").append('<div class="' + msgClass + '"><div class="inline-block msg_time">' + msgTime + '</div><div class="alert alert-success inline-block msg_body">' + msgVo.msgContent +'</div></div>');
	} else {
		$(".panel-body").append('<div class="' + msgClass + '"><div class="alert alert-warning inline-block msg_body">' + msgVo.msgContent +'</div><div class="inline-block msg_time">' + msgTime + '</div></div>');
	}
}

function sendMsg() {
	var param = {};
	param.srcId = 4;
	param.dstId = 1;
	param.msgContent = $("#msgContent").val();
	
	$.ajax({
		type : "POST",
		url : "/preschool/usermsg/send",
		data : JSON.stringify(param),
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		success : function(){
			refresh();
			$("#msgContent").val("");
		},
		dataType : "JSON"
	});
}

function refresh() {
	$(".panel-body").empty();
	
	$.get("/preschool/usermsg/1/4", function(data, status) {
		for (var i = 0; i < data.length; i++) {
			addMsgList(data[i]);
		}
	});
	
	$("body").animate({ scrollTop: $(document).height() }, 1000);}
</script>
