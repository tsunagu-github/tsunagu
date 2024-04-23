/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* ブラウザ変数
 **/
var isWin9X = (navigator.appVersion.toLowerCase().indexOf('windows 98')+1);
var isIE = (navigator.appName.toLowerCase().indexOf('internet explorer')+1?1:0);
var isOpera = (navigator.userAgent.toLowerCase().indexOf('opera')+1?1:0);
if (isOpera) isIE = false;
var isSafari = (navigator.appVersion.toLowerCase().indexOf('safari')+1?1:0);

/**
 * 特定健診登録時に登録中を表示する
 * 位置を調整済みなので別画面で意図した位置にならないときは別の関数を用意すること
 **/
function waitMethod(title) {
    var waitForm = document.getElementById("waitEntry");
    var waitTitle = document.getElementById("waitEntryTitle");

    var scrol = getScrollPosition();
    var scren = getScreenSize();
    var top = scrol.y + (scren.y / 3)
    waitForm.style.top = top + "px";
    waitForm.style.display = "block";

}

/**
 * スクロールポジション取得
 **/
function getScrollPosition() {
	var obj = new Object();
	obj.x = document.documentElement.scrollLeft || document.body.scrollLeft;
	obj.y = document.documentElement.scrollTop || document.body.scrollTop;
	return obj;
}
/**
 * 画面サイズ取得
 **/
function getScreenSize() {
	var obj = new Object();
	if (!isSafari && !isOpera) {
		obj.x = document.documentElement.clientWidth || document.body.clientWidth || document.body.scrollWidth;
		obj.y = document.documentElement.clientHeight || document.body.clientHeight || document.body.scrollHeight;
	} else {
		obj.x = window.innerWidth;
		obj.y = window.innerHeight;
	}
	obj.mx = parseInt((obj.x)/2);
	obj.my = parseInt((obj.y)/2);
	return obj;
}

