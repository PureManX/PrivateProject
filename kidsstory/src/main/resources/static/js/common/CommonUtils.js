/**
 * Created by PureMaN on 2017-06-08.
 */
jQuery.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
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