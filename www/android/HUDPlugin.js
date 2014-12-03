
/**
 * Constructor
 */
function HUDPlugin() {
  //this._callback;
}

/**
 * show - true to show the ad, false to hide the ad
 */
HUDPlugin.prototype.show = function(options, cb) {
  
	var defaults = {
        text: '',
        icon: '',
        timeOut: 0
    };

	for (var key in defaults) {
		if (typeof options[key] !== "undefined") {
			defaults[key] = options[key];
		}
	}

	//this._callback = cb;

	var callback = function(message) {
		var m = '' + message;
		if(m == 'cancelled'){
			cb({status: 'cancelled'});
		} else{
			m = m.replace(/&#34;/g, '"');
			cb({status:'success', data: JSON.parse(m)});
		}
	}
  
	cordova.exec(callback, 
		null, 
		"HUDPlugin", 
		defaults.text,
		[defaults]
	);
};

var hud = new HUDPlugin();
module.exports = hud;

// Make plugin work under window.plugins
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.hud) {
    window.plugins.hud = hud;
}