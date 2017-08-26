<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style>
div.studuent_info {
	overflow: auto;
}

div.studuent_info img {
	width: 70px;
	height: 70px;
	background: #92a3c8;
}

div.studuent_info div.stduent_text {
	height: 70px;
	padding: 5px;
    margin-left: 10px;
}

div.studuent_info div.stduent_text .btn_wrap {
	padding: 0;
}

.modal {
	text-align: center;
	padding: 0 !important;
}

.modal:before {
	content: '';
	display: inline-block;
	height: 100%;
	vertical-align: middle;
	margin-right: -4px;
}

.modal-dialog {
	display: inline-block;
	text-align: left;
	vertical-align: middle;
}

#message-text {
	min-width: 250px;
}

.header_wrapper{
	overflow: auto;
}

.header_wrapper img{
    width: 70px;
    height: 70px;
    background: white;
}

.studuent_info {
	text-align: left;
}

.board_comp {
	background: #e4e7ed
}

.inline {
	display : inline;
}
div.btn_wrap {
    position: absolute;
    bottom: 10px;
    right: 15px;
    width: 50%;
    text-align: right;    
}

button.btn-sm {
	width: 48%;
}

h5.h5 {
	margin-left: 10px;
}
</style>
<script type="text/javascript">
	var curUser;
	
	function updateTest(button) {
// 		$("#test_btn1").removeClass("btn-success");
// 		$("#test_btn1").addClass("btn-primary");
// 		$("#test_btn2").hide();
// 		$("#li_test3").addClass("board_comp");
// 		$("#test_btn3").removeClass("btn-info");
// 		$("#test_btn3").addClass("btn-success");
// 		$("#test_btn3").text("탑승완료");

		var btnType = $(button).text();		
		
		var param = {};
		
		if (btnType == "탑승처리") {
			param.boardDiv = "C";
		} else if (btnType == "미탑승") {
			param.boardDiv = "N";
		}
		
		param.lineDtlId = $(button).parent().parent().parent().parent().attr("id").replace("stdu_", "");
		param.histDate = '0211'
// 		alert(JSON.stringify(param));

		$.ajax({
			type : "POST",
			url : "/preschool/board/process",
			data : JSON.stringify(param),
// 			data : param,
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			success : function(){
				refresh();
			},
			dataType : "JSON"
		});
	}

	function refresh() {
		
		$("#board_line").empty();
		
		$.get("/preschool/board/list/1", function(data, status) {
			var curFlg = false;
			for (var i = 0; i < data.length; i++) {
				if (curFlg == false && data[i].boardDiv == null) {
					addList(data[i], true);
					curFlg = true;
				} else {
					addList(data[i], false);
				}
			}
		});		
	}
	
	$(document).ready(function() {
		curUser = User.getUser("01033334444");

		$.get("/preschool/board/list/1", function(data, status) {
			var curFlg = false;
			for (var i = 0; i < data.length; i++) {
				if (curFlg == false && data[i].boardDiv == null) {
					addList(data[i], true);
					curFlg = true;
				} else {
					addList(data[i], false);
				}
			}
		});
	});

	function addList(lineDtlData, curFlg) {
		var liId = "stdu_" + lineDtlData.lineDtlId
		$("#board_line")
				.append(
						'<li class="list-group-item" id="' + liId +'"><div class="studuent_info"><a href="/preschool/test/test2"><img class="img-rounded pull-left" src=""></a><div class="stduent_text pull-left"><h4 class="h4 inline"></h4><h5 class="h5 inline stdu_cls"></h5><h5 class="h5 inline board_tm"></h5><div class="btn_wrap"></div></div></div></li>');

		$("#" + liId + " h4").text(lineDtlData.userNm);
		$("#" + liId + " h5.stdu_cls").text(lineDtlData.clsNm + "반");
		$("#" + liId + " h5.board_tm").text(
				lineDtlData.boardTm.substring(0, 2) + ":"
						+ lineDtlData.boardTm.substring(2, 4));
		$("#" + liId + " img").attr("src",
				"/preschool/file/data" + lineDtlData.imgSrc)

		if (lineDtlData.boardDiv == "C" || lineDtlData.boardDiv == "N") {
			$("#" + liId).addClass("board_comp");

			if (lineDtlData.boardDiv == "C") {
				$("#" + liId + " div.btn_wrap").append(
						'<button class="btn btn-primary btn-sm">탑승완료</button>');
			} else if (lineDtlData.boardDiv == "N") {
				$("#" + liId + " div.btn_wrap").append(
						'<button class="btn btn-warning btn-sm">미탑승</button>');
			}
		} else {
			if (curFlg) {
				$("#" + liId + " div.btn_wrap")
						.append(
								'<button class="btn btn-success btn-sm" onclick="updateTest(this)">탑승</button>');
			} else {
				$("#" + liId + " div.btn_wrap").append(
						'<button class="btn btn-info btn-sm">탑승대기</button>');
			}
			$("#" + liId + " div.btn_wrap")
					.append(
							' <button class="btn btn-danger btn-sm" data-toggle="modal" data-target=".bs-example-modal-sm" onclick="updateTest(this)">미탑승</button>');
		}

	}
</script>
<div class="panel panel-warning">
	<div class="panel-heading">
		<div class="header_wrapper">
			<div class="pull-left">
				<h4 class="h4">1호차 A구역</h4>
				<h5 class="h5">지은탁 선생님</h5>
			</div>
			<img class="img-rounded pull-right" src="/preschool/file/data/prof/prof_3.jpg">
		</div>
	</div>
	<div class="panel-body">
		<ul class="list-group" id="board_line">
		</ul>
	</div>
</div>

<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">미탑승 입력</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="message-text" class="control-label">미탑승 사유</label>
					<textarea class="form-control" id="message-text"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
