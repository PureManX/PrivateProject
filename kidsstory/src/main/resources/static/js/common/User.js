/**
 * 
 */

var User = {
	getUser : function(contact) {
		var rtnObj;

		$.ajax({
			url : "/preschool/user/" + contact,
			async : false,
			dataType : 'json',
			success : function(data) {
				rtnObj = data;
			}
		});
		
		return rtnObj;
	}
};
