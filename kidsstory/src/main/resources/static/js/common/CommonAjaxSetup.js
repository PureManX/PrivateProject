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