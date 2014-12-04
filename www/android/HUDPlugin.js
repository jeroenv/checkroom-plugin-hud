
/**
 * Constructor
 */
function HUDPlugin() {
  //this._callback;
}

/**
 * show - true to show the ad, false to hide the ad
 */

 HUDPlugin.prototype = {

	show: function(options) {
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
  
		cordova.exec(null, 
			null, 
			"HUDPlugin", 
			"show",
			[defaults]);
	},

	hide: function() {
		var defaults = {};

		cordova.exec(null, 
      		null, 
      		"HUDPlugin", 
      		"hide",
      		[defaults]);
	},

	isShowing:function(cb){
		
		var defaults = {};

		var callback = function(visibility) {
			if(visibility == 'visible'){
      			cb({status:'success', data: true});  
    		} else{
      			cb({status:'success', data: false});  
    		}
		};

		cordova.exec(callback, 
      		null, 
      		"HUDPlugin", 
      		"isShowing",
      		[defaults]);
	}

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