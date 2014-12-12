var exec = require('cordova/exec');
/**
 * Constructor
 */
function HUDPlugin() {
    this._callback;
}

HUDPlugin.prototype.show = function(options) {

    var defaults = {
        text : '',
        icon: '',
        timeOut: 0
    };

    for (var key in defaults) {
        if (typeof options[key] !== "undefined"){
          defaults[key] = options[key];
        }
    }

    exec(null, 
      null, 
      "HUDPlugin", 
      "show",
      [defaults]);
};

HUDPlugin.prototype.hide = function() {
    exec(null, 
      null, 
      "HUDPlugin", 
      "hide",
      null);
};


HUDPlugin.prototype.isShowing = function(cb) {
    this._callback = cb;
    exec(null, 
      null, 
      "HUDPlugin", 
      "isShowing",
      null);
};

HUDPlugin.prototype._isShowing = function(visibility) {
    if(visibility == 'visible'){
      this._callback({status:'success', data: true});  
    } else{
      this._callback({status:'success', data: false});  
    }
};


var hudPlugin = new HUDPlugin();
module.exports = hudPlugin;

// Make plugin work under window.plugins
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.hudPlugin) {
    window.plugins.hudPlugin = hudPlugin;
}