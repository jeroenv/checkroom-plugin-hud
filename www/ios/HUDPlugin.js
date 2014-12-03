/**
  Phonegap HUD Plugin
*/

var exec = require('cordova/exec');
/**
 * Constructor
 */
function HUDPlugin() {
    this._callback;
}

/**
 * show - true to show the ad, false to hide the ad
 */
HUDPlugin.prototype.show = function(options, cb) {

    var defaults = {
        text : '',
        icon: '',
        timeOut: 0
    };

    for (var key in defaults) {
        if (typeof options[key] !== "undefined")
            defaults[key] = options[key];
    }
    this._callback = cb;

    exec(null, 
      null, 
      "HUDPlugin", 
      "show",
      [defaults]
    );
};

HUDPlugin.prototype._actionSelected = function(json) {
    json = json.replace(/&#34;/g, '"');
    if (this._callback)
        this._callback({status:'success', data: JSON.parse(json)});  
}

var hudPlugin = new HUDPlugin();
module.exports = hudPlugin;

// Make plugin work under window.plugins
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.hudPlugin) {
    window.plugins.hudPlugin = hudPlugin;
}