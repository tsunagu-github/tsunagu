module.exports = function(ctx) {
    // make sure android platform is part of build
    if (ctx.opts.platforms.indexOf('android') < 0) {
        return;
    }
    var fs = require('fs'),
    	mkdirp = require('mkdirp'),
        path = require('path'),
        getDirName = require('path').dirname;

    // Cordovaが Appアイコン以外はコピーしないので、自前で通知アイコンをコピー
var filestocopy = [{
    "scripts/data/notification/drawable-hdpi/fcm_push_icon.png": 
    "platforms/android/res/drawable-hdpi/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-hdpi-v9/fcm_push_icon.png": 
    "platforms/android/res/drawable-hdpi-v9/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-hdpi-v11/fcm_push_icon.png": 
    "platforms/android/res/drawable-hdpi-v11/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-mdpi/fcm_push_icon.png": 
    "platforms/android/res/drawable-mdpi/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-mdpi-v9/fcm_push_icon.png": 
    "platforms/android/res/drawable-mdpi-v9/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-mdpi-v11/fcm_push_icon.png": 
    "platforms/android/res/drawable-mdpi-v11/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xhdpi/fcm_push_icon.png": 
    "platforms/android/res/drawable-xhdpi/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xhdpi-v9/fcm_push_icon.png": 
    "platforms/android/res/drawable-xhdpi-v9/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xhdpi-v11/fcm_push_icon.png": 
    "platforms/android/res/drawable-xhdpi-v11/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xxhdpi/fcm_push_icon.png": 
    "platforms/android/res/drawable-xxhdpi/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xxhdpi-v9/fcm_push_icon.png": 
    "platforms/android/res/drawable-xxhdpi-v9/fcm_push_icon.png"
}, {
    "scripts/data/notification/drawable-xxhdpi-v11/fcm_push_icon.png": 
    "platforms/android/res/drawable-xxhdpi-v11/fcm_push_icon.png"
} ];

console.log('[Hook] beforeBuildAndroidNotif.js: Copying notification Icons to cordova-android platform res folder.');

filestocopy.forEach(function(obj) {
    Object.keys(obj).forEach(function(key) {
        var val = obj[key];
        var srcfile = path.join(ctx.opts.projectRoot, key);
        var destfile = path.join(ctx.opts.projectRoot, val);
        //console.log("copying "+srcfile+" to "+destfile);
        var destdir = path.dirname(destfile);
        if (fs.existsSync(srcfile)) {
            if (!fs.existsSync(destdir)) {
//                mkdirp('./platforms/android/res', function (err) {
//                    if (err) throw err;
//                });
                
                // resディレクトリが無ければ作成する
                if (!fs.existsSync('./platforms/android/res')) {
                    fs.mkdir('./platforms/android/res', (err) => {
                        if (err) { throw err; }
                        console.log('resディレクトリが作成されました');
                    });
                }
                
                // destdirの形式をを「./～」に変換する
                var destpath = destdir.replace('C:', '.');
                var destpath2 = destpath.replace(/\\/g, '\/');
                var split = destpath2.split('\/');
                var reverse = split.reverse();
                var newpath = "./" + reverse[3] + "/" + reverse[2] + "/" + reverse[1] + "/" + reverse[0];

                // resディレクトリ直下に新しいディレクトリを作成する
                fs.mkdir(newpath, (err) => {
                    if (err) { throw err; }
                    console.log(reverse[0] + 'ディレクトリが作成されました');
                });
               
               fs.createReadStream(srcfile).pipe(
                   fs.createWriteStream(destfile)
               );
            }
        }
    });
});
    console.log('  Copy Successful');
};
