/**
 * Created by PureMaN on 2017-06-08.
 */
jQuery.fn.serializeObject = function() {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}//if ( arr ) {
		}
	} catch (e) {
		alert(e.message);
	} finally {
	}

	return obj;
};

var WebbrowserUtils = {
		_SUPPROT_DEVICE_REGEX_STR : /Android|iPhone|iPad/i,
		_DEVICE_IPAD_STR : "iPad",
		_DEVICE_IPHONE_STR : "iPhone",
		_DEVICE_ANDROID_STR : "Android",
		isiPhone : function() {
			return WebbrowserUtils._DEVICE_IPHONE_STR == WebbrowserUtils._getDeviceName() ? true : false;
		},
		isiPad : function() {
			return WebbrowserUtils._DEVICE_IPAD_STR == WebbrowserUtils._getDeviceName() ? true : false;
		},
		isiOs : function() {
			return WebbrowserUtils.isiPhone() || WebbrowserUtils.isiPad();
		},
		isAndroid : function() {
			return WebbrowserUtils._DEVICE_ANDROID_STR == WebbrowserUtils._getDeviceName() ? true : false;
		},
		_getDeviceName : function() {
			var userAgent = navigator.userAgent;
			var device = userAgent.match(WebbrowserUtils._SUPPROT_DEVICE_REGEX_STR)
			
			if (device == null) {
				return null;
			} else {
				return device.pop();
			}
		}
	}