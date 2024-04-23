module.exports = function(ctx) {
    // make sure android platform is part of build
    if (ctx.opts.platforms.indexOf('ios') < 0) {
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

    // GoogleService-Info.plistを所定の場所にコピーする
    var copyFrom = path.join(ctx.opts.projectRoot, 'scripts', 'data', id, 'GoogleService-Info.plist');
    var copyTo = path.join(ctx.opts.projectRoot, 'platforms/ios/GoogleService-Info.plist');
    var copyToDir = path.dirname(copyTo);
    console.log('[Hook] beforeBuildiOs.js: Copying GoogleService-Info.plist for ' + id + ' to cordova-ios platform folder.');
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
    	deferral.reject('Operation failed : Missing GoogleService-Info.plist or cordova-ios platform folder.');
    }

    return deferral.promise;
};
