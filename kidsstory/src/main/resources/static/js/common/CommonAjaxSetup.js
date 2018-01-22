/**
 * 
 */
$.ajaxSetup({
	beforeSend : function() {
		$("#full-popup-loading").removeClass("hidden")
	}
	,
	error : function(jqXHR, textStatus, errorThrown) {
		console.log(JSON.stringify(jqXHR));
		
		if (jqXHR.readyState != 4) {
			alert("일시적으로 접속이 원활하지 않습니다.\n잠시 후 다시 이용해\n 주시기 바랍니다.")
			return false;
		}
		
		if (jqXHR.status == 401) {
			location.href = "/error/401"
		} else {
			alert(jqXHR.responseJSON.message);
		}
	}
	,
	complete : function() {
		$("#full-popup-loading").addClass("hidden")
	}
});