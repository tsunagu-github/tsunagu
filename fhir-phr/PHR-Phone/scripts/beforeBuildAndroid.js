module.exports = function(ctx) {
    // make sure android platform is part of build
    if (ctx.opts.platforms.indexOf('android') < 0) {
        return;
    }
    var fs = require('fs'),
        path = require('path'),
        cordova_util = require("cordova-lib/src/cordova/util"),
        ConfigParser = require('cordova-common').ConfigParser,
        xml = cordova_util.projectConfig(ctx.opts.projectRoot),
        cfg = new ConfigParser(xml),
        id = cfg.packageName(),
        deferral = require('q').defer();

    // google-services.jsonを所定の場所にコピーする
    var copyFrom = path.join(ctx.opts.projectRoot, 'scripts', 'data', id, 'google-services.json');
    var copyTo = path.join(ctx.opts.projectRoot, 'platforms/android/google-services.json');
    var copyToDir = path.dirname(copyTo);
    console.log('[Hook] beforeBuildAndroid.js: Copying google-services.json for ' + id + ' to cordova-android platform folder.');
    console.log('  Copy From: ' + copyFrom);

    if (fs.existsSync(copyFrom) && fs.existsSync(copyToDir)) {
        var rs = fs.createReadStream(copyFrom);
        rs.on('error', function (err) {
            console.error(err);
            deferral.reject('Operation failed :' + err);
        });
        var ws = fs.createWriteStream(copyTo);
        ws.on('finish', function (err) {
            console.log('  Copy Successful');
            deferral.resolve();
        });
        ws.on('error', function (err) {
            console.error(err);
            deferral.reject('Operation failed :' + err);
        });
        rs.pipe(ws);
    } else {
    	deferral.reject('Operation failed : Missing google-services.json or cordova-android platform folder.');
    }

    return deferral.promise;
};
