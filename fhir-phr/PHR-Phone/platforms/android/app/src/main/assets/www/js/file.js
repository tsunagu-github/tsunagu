"use strict";
//************************************************************
// module: file.js
// author: otak-lab
// use plugin: cordova-plugin-file, cordova.plugins.diagnostic
//************************************************************
if (!window.hasOwnProperty("fn")) {
  window.fn = {};
}
window.fn.file = {};
//************************************************************
// authorize external storage
//************************************************************
window.fn.file.authExtStorage = function () {
  return new Promise(function (resolve, reject) {
    cordova.plugins.diagnostic.requestExternalStorageAuthorization(function (status) {
      if (status == cordova.plugins.diagnostic.permissionStatus.GRANTED) {
        resolve(status);
      } else {
        reject(status);
      }
    });
  });
};
//************************************************************
// get file list
//************************************************************
window.fn.file.getFileList = function (path) {
  return new Promise(function (resolve, reject) {
    var i = 0;
    var fileEntries = [];
    var list = [];
    var errorReject = function (err) {
      reject(err);
    };
    // read entries
    var readEntries = function (reader) {
      return new Promise(function (resolve, reject) {
        var errorReject = function (err) {
          reject(err);
        };
        reader.readEntries(function (entries) {
          if (entries.length > 0) {
            for (i = 0; i < entries.length; i++) {
              if (entries[i].isFile) {
                fileEntries.push(entries[i]);
              }
            }
            readEntries(reader).then(function () {
              resolve();
            }, errorReject);
          } else {
            resolve();
          }
        }, errorReject);
      });
    };
    // get meta data
    var getMetaData = function (index) {
      return new Promise(function (resolve, reject) {
        var errorReject = function (err) {
          reject(err);
        };
        if (index < fileEntries.length) {
          fileEntries[index].getMetadata(function (meta) {
            list.push({
              name: fileEntries[index].name,
              modifiedTime: meta.modificationTime,
              size: meta.size,
              nativeURL: fileEntries[index].nativeURL
            });
            getMetaData(index + 1).then(function () {
              resolve();
            }, errorReject);
          }, errorReject);
        } else {
          resolve();
        }
      });
    };
    window.resolveLocalFileSystemURL(path, function (fileSystem) {
      var reader = fileSystem.createReader();
      readEntries(reader).then(function () {
        getMetaData(0).then(function () {
          resolve(list);
        }, errorReject);
      }, errorReject);
    }, errorReject);
  });
};
//************************************************************
// read file
//************************************************************
window.fn.file.read = function (path, fileName, isBinary) {
  return new Promise(function (resolve, reject) {
    var errorReject = function (err) {
      reject(err);
    };
    window.resolveLocalFileSystemURL(path, function (directoryEntry) {
      directoryEntry.getFile(fileName, { create: false, exclusive: true }, function (fileEntry) {
        fileEntry.file(function (file) {
          var reader = new FileReader();
          reader.onloadend = function (e) {
            resolve(e.target.result);
          };
          reader.onerror = errorReject;
          if (isBinary) {
            reader.readAsArrayBuffer(file);
          } else {
            reader.readAsText(file);
          }
        }, errorReject);
      }, errorReject);
    }, errorReject);
  });
};
//************************************************************
// write file
//************************************************************
window.fn.file.write = function (path, fileName, data, isAppend) {
  return new Promise(function (resolve, reject) {
    var errorReject = function (err) {
      reject(err);
    };
    window.resolveLocalFileSystemURL(path, function (directoryEntry) {
      directoryEntry.getFile(fileName, { create: true, exclusive: false }, function (fileEntry) {
        fileEntry.createWriter(function (fileWriter) {
          fileWriter.onwriteend = function (e) {
            resolve(e.target.length);
          };
          fileWriter.onerror = errorReject;
          if (isAppend && (fileWriter.length > 0)) {
            fileWriter.seek(fileWriter.length);
          }
          fileWriter.write(data);
        });
      }, errorReject);
    }, errorReject);
  });
};
//************************************************************
// file error message
//************************************************************
window.fn.file.errorMessage = function (err) {
  var msg = "";
  switch (err.code) {
    case FileError.NOT_FOUND_ERR:
      msg = "NOT_FOUND_ERR";
      break;
    case FileError.SECURITY_ERR:
      msg = "SECURITY_ERR";
      break;
    case FileError.ABORT_ERR:
      msg = "ABORT_ERR";
      break;
    case FileError.NOT_READABLE_ERR:
      msg = "NOT_READABLE_ERR";
      break;
    case FileError.ENCODING_ERR:
      msg = "ENCODING_ERR";
      break;
    case FileError.NO_MODIFICATION_ALLOWED_ERR:
      msg = "NO_MODIFICATION_ALLOWED_ERR";
      break;
    case FileError.INVALID_STATE_ERR:
      msg = "INVALID_STATE_ERR";
      break;
    case FileError.SYNTAX_ERR:
      msg = "SYNTAX_ERR";
      break;
    case FileError.INVALID_MODIFICATION_ERR:
      msg = "INVALID_MODIFICATION_ERR";
      break;
    case FileError.QUOTA_EXCEEDED_ERR:
      msg = "QUOTA_EXCEEDED_ERR";
      break;
    case FileError.TYPE_MISMATCH_ERR:
      msg = "TYPE_MISMATCH_ERR";
      break;
    case FileError.PATH_EXISTS_ERR:
      msg = "PATH_EXISTS_ERR";
      break;
    default:
      msg = "UNKNOWN_ERR";
  };
  return msg;
};