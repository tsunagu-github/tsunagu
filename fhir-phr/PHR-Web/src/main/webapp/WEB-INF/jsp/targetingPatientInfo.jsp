<%-- 
    Document   : targetingPatientInfo
    Created on : 2016/08/26, 20:12:58
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">
<head>
    <META http-equiv="Content-Language" content="ja"> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Script-Type" content="text/javascript">
    <meta http-equiv="Content-Style-Type" content="text/css">

    <title>患者情報</title>

    <link href="css/import.css" rel="stylesheet" type="text/css">
    <LINK href="css/jquery.qtip.css" rel="stylesheet" type="text/css">
    <LINK href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"> 
    <LINK href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css"> 
    <link href="js/libs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen">

    <script src="js/common.js" type="text/javascript"></script>
    <SCRIPT src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT> 
    <SCRIPT src="js/jquery/jquery-ui.min.js" type="text/javascript"></SCRIPT>
    <SCRIPT src="js/jquery/jquery.ui.datepicker-ja.js" type="text/javascript"></SCRIPT>
    <SCRIPT src="js/jquery/jquery.colorbox-min.js" type="text/javascript"></SCRIPT>
    <script src="js/libs/jquery.alerts.js" type="text/javascript" charset="UTF-8"></script>
    <script>
$(function(){
    $(".datepicker").datepicker({
　	showOn: "button",
　	buttonImage: "img/icon/calendar.png",
　	buttonImageOnly: true
    });
});



$(function() {
    $(".tabbtn a").on('click', function(e) {
 	e.preventDefault();
	var target = $(this).attr('href');
	if (! $(target).length) return false;
	$('.tab', $(this).closest('.tabbtn')).removeClass('active');
	$(this).closest('.tab').addClass('active');
	$('.content', $(target).closest('.tabcontents')).removeClass('active');
	$(target).addClass('active');
    });
});

</script>

    <script type="text/javascript">
    function initPage() {
        //管理疾病の初期表示
	kensinChange();
        getTargPatiInfo();
    }

    function getDate(separator) {
        var d = new Date();
 
        return [
            d.getFullYear(),
            ('0' + (d.getMonth() + 1)).slice(-2),
            ('0' + d.getDate()).slice(-2)
        ].join(separator);
    }


    function kensinChange(id){
	//全部非表示にする
	document.getElementById("kensin_kensa").style.display = "none";
	document.getElementById("kensin_qus").style.display = "none";
	document.getElementById("kensin_sin").style.display = "none";
	//表示対象を取得する
        var target;
        if(id === "3"){
            target = document.getElementById("kensin_sin");         
        }else if(id === "2"){
            target = document.getElementById("kensin_qus");         
        }else{
            target = document.getElementById("kensin_kensa");
        }
        target.style.display = "";
    }

    function showPopup(event) {
      target = document.getElementById("selectedPtInfo");

      if (!event) var event = window.event;
      if (!event.pageX) px = event.clientX + document.body.scrollRight; else px = event.pageX;
      if (!event.pageY) py = event.clientY + document.body.scrollTop; else py = event.pageY;

        target.style.left = px-300 + "px";
        target.style.top = py+5 + "px";
        target.style.display = "block";
    }
    function hidePopup() {
      target = document.getElementById("selectedPtInfo");
      target.style.display = "none";
    }
    function unlockMyself(){
        jConfirm('今回入力したワンタイムパスワードでの患者情報閲覧を解除しますが、よろしいですか？', '指定解除実施確認', function(r) {
            if(r){
                //指定解除実行
                var strData = {
                    value1: $("#basedate").val(),
                    value2: $("#basedate").val()
                };
                var postData = JSON.stringify(strData);
                var postUrl = "unassignPatient";
                $.ajax({
                    url: postUrl,
                    type: "POST",
                    dataType: "json",
                    data: postData,
                    contentType: "application/JSON",
                    success: function(json){
        //                alert("success:" + json);
                        var itemErrors = document.getElementById("itemErrors");
                        itemErrors.textContent = "";
                        itemErrors.style.display = "none";
                        if(json){
                            if(json[0]===""){
                                //画面を閉じる
                                closeMyself();
                            }else{
                                jAlert(json[0],"",function(){
                                    //画面を閉じる
//                                    closeMyself();
                                });
                            }
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        //検索エラー
                        jAlert("指定解除に失敗しました。","");
                    }
                });
            }
        });
    }
    function closeMyself(){
        window.opener = "myself";
        window.close();
    }
    
    function getObservationDosageAjax(num) {
        var strData = {
            value1: $("#basedate").val(),
            value2: $("#basedate").val(),
            viewId:$("#selectView").val(),
            nextDataNum: num
        };
        var postData = JSON.stringify(strData);
		var postUrl = "getJsonData";
	        $.ajax({
	            url: postUrl,
	            type: "POST",
	            dataType: "json",
	            data: postData,
	            contentType: "application/JSON",
	            success: function(json){
	//                alert("success:" + json);
	                var itemErrors = document.getElementById("itemErrors");
	                itemErrors.textContent = "";
	                itemErrors.style.display = "none";
	                if(json){
	                    setObservationJson(json);
	                    changeMsgList();
	                }
	            },
	            error: function() {
	                //検索エラー
	                jAlert("基準日の患者情報取得に失敗しました。","");
	            }
		});
    }
    function setObservationJson(json) {
//      // エラー表示クリア
//      var itemErrors = document.getElementById("itemErrors");
//      itemErrors.textContent = "";
//      itemErrors.style.display = "none";
        // 背景色(0:緑,1:黄,2:橙,3:赤,4:白,5,6,7,8,9:白)：（正常,予備軍,発症,合併症リスク,重症,,,,,なし）
        var bgColors = new Array("#ccffcc","#ffff99","#ffd700","#FF0000","#FFFFFF","","","","","#FFFFFF");
        // 管理疾患(1:糖尿病,2:高血圧,3:脂質異常症,4:CKD,5:冠動脈,6:脳卒中)
        var aCtrlCase = new Array("0000000011","0000000015","0000000019","0000000021", "0000000020", "0000000103");
        var aCtrlId = new Array("1","2","3","4","5","6");
        // 健診ヘッダの日付用変数
        var dtkensin1="";
        var dtkensin2="";
        var dtkensin3="";
        var dtmonsin1="";
        var dtmonsin2="";
        var dtmonsin3="";
        var dtsin1="";
        var dtsin2="";
        var dtsin3="";
        //おくすり一覧情報
        var myTblKusuri=$("#kusuri_table");
        //自己測定
        var myTblKatei=$("#katei_table");
        //おしらせ一覧情報
        var myOsiraseMsg=$("#osirase_msg_list");
        //検査結果
        var myTBL=$("#myTBL");
        //健診
        var kenKensaTBL=$("#kensin_kensa_tb");
        var kenQusTBL=$("#kensin_qus_tb");
        var kenSinTBL=$("#kensin_sin_tb");
        var len=json.length;
        var setCnt1=0;
        var setCnt2=0;
        var setCnt3=0;
        
        // 測定・全検査結果
        var allKensaTBL = $("#allKensaTBL");
        
        for(var i = 0; i < len; i++) {
            if(i===0){
                if(myTBL.length===1){
                    var tbody = myTBL[0].tBodies;
                    // 検査結果一旦全行をクリアする
                    var clearLen = tbody[0].rows.length;
                    if(clearLen>0){
                        for(var j2 = clearLen-1; j2 >= 0; j2--) {
                            tbody[0].rows[j2].cells[1].innerText="";
                            tbody[0].rows[j2].cells[1].style.backgroundColor = "";
                            tbody[0].rows[j2].cells[2].innerText="";
                        }
                    }
                    var valBtnKensaLeft=$("#kensa_leftbtn");
                    valBtnKensaLeft[0].style.display="none";
                    var valBtnKensaRight=$("#kensa_rightbtn");
                    valBtnKensaRight[0].style.display="none";
                }
                if(kenKensaTBL.length===1){
                    // 健診検査一旦全行をクリアする
                    var clearLen = kenKensaTBL[0].rows.length;
                    if(clearLen>0){
                        for(var j2 = clearLen-1; j2 >= 0; j2--) {
                            kenKensaTBL[0].rows[j2].cells[1].innerText="";
                            kenKensaTBL[0].rows[j2].cells[2].innerText="";
                            kenKensaTBL[0].rows[j2].cells[3].innerText="";
                        }
                    }
                    var valBtnKensinLeft=$("#kensin-leftbtn");
                    valBtnKensinLeft[0].style.display="none";
                    var valBtnKensinRight=$("#kensin-rightbtn");
                    valBtnKensinRight[0].style.display="none";
                }
                if(kenQusTBL.length===1){
                    // 健診問診一旦全行をクリアする
                    var clearLen = kenQusTBL[0].rows.length;
                    if(clearLen>0){
                        for(var j2 = clearLen-1; j2 >= 0; j2--) {
                            kenQusTBL[0].rows[j2].cells[1].innerText="";
                            kenQusTBL[0].rows[j2].cells[2].innerText="";
                            kenQusTBL[0].rows[j2].cells[3].innerText="";
                        }
                    }
                }
                if(kenSinTBL.length===1){
                    // 健診診察一旦全行をクリアする
                    var clearLen = kenSinTBL[0].rows.length;
                    if(clearLen>0){
                        for(var j2 = clearLen-1; j2 >= 0; j2--) {
                            kenSinTBL[0].rows[j2].cells[1].innerText="";
                            kenSinTBL[0].rows[j2].cells[2].innerText="";
                            kenSinTBL[0].rows[j2].cells[3].innerText="";
                        }
                    }
                }
                for(var l = 0; l < aCtrlId.length; l++){
                    //管理疾患のチェックを初期化
                    var chkBox=$("#"+aCtrlId[l]+"_dtype");
                    if(chkBox.length===1){
                        chkBox.prop("checked",false);
                        displayChange(aCtrlId[l]+"_dtype");
                    }
                }
                if(myTblKusuri.length===1){
                    var tbody = myTblKusuri[0].tBodies;
                    // おくすり一旦全行を削除する
                    var delLen = tbody[0].rows.length;
                    if(delLen>0){
                        for(var j = delLen-1; j >= 0; j--) {
                            tbody[0].deleteRow(j);
                        }
                    }
                }
                if(myTblKatei.length===1){
                    var tbody = myTblKatei[0].tBodies;
                    // 自己測定一旦全行を削除する
                    var delLen = tbody[0].rows.length;
                    if(delLen>0){
                        for(var j = delLen-1; j >= 0; j--) {
                            tbody[0].deleteRow(j);
                        }
                    }
                }
                if(myOsiraseMsg.length===1){
                    //最初にクリア
                    myOsiraseMsg[0].innerHTML="";
                }
                
                if(allKensaTBL.length===1){
                    var tbody = allKensaTBL[0].tBodies;
                    // 検査結果一旦全行をクリアする
                    allKensaTBL[0].rows[0].cells[0].innerText="";
                    allKensaTBL[0].rows[1].cells[0].style.backgroundColor="";
                    allKensaTBL[0].rows[1].cells[1].style.backgroundColor="";
                    allKensaTBL[0].rows[1].cells[2].style.backgroundColor="";
                    var clearLen = tbody[0].rows.length;
                    if(clearLen>0){
                        for(var j2 = clearLen-1; j2 >= 0; j2--) {
                            tbody[0].rows[j2].cells[0].innerText="";
                            tbody[0].rows[j2].cells[1].innerText="";
                            tbody[0].rows[j2].cells[2].innerText="";
                            tbody[0].rows[j2].cells[2].style.backgroundColor = "";
                            tbody[0].rows[j2].style.display="none";
                        }
                    }
                    var valBtnKensaLeft=$("#allkensa_leftbtn");
                    valBtnKensaLeft[0].style.display="none";
                    var valBtnKensaRight=$("#allkensa_rightbtn");
                    valBtnKensaRight[0].style.display="none";
                }
            }
            
            if(json[i].dataInputTypeCd===1){
                //検査結果
                var myTr=$("#"+json[i].id+"_kind");   //json[i].kind
                if(myTr.length===1){
//                  alert(json[i].id);
                    var myTdUnit=$("#"+json[i].id);
                    var myTdDate=$("#"+json[i].id+"_date");
//                  ulObj.append($("<li>").attr({"id"json[i].id}).text(json[i].name));
                    if(myTdUnit.length===1){
                        myTdUnit[0].innerText=json[i].value+" "+json[i].unit;
                        if(bgColors.length>=json[i].alertLevel && json[i].alertLevel>=0){
                            myTdUnit[0].style.backgroundColor = bgColors[json[i].alertLevel];
                        }else{
                            myTdUnit[0].style.backgroundColor = bgColors[0];
                        }
                        //管理疾患のチェック
                        for(var m = 0; m < aCtrlId.length; m++){
                            if(json[i].id===aCtrlCase[m] && json[i].value!==""){
                                if (m === 4) {
                                	var chkBox2=$("#"+aCtrlId[m]+"_dtype");
                                	if (json[i].value == "あり（造影検査）" || json[i].value == "あり（その他検査）") {
                                        if(chkBox2.length===1){
                                            chkBox2.prop("checked",true);
                                            displayChange(aCtrlId[m] + "_dtype");
                                        }
                                	} else {
                                		chkBox2.prop("checked",false);
                                		displayChange(aCtrlId[m] + "_dtype");
                                	}
                                } else if (m === 5){
                                	var chkBox2=$("#"+aCtrlId[m]+"_dtype");
                                	if (json[i].value == "あり（脳梗塞）" || json[i].value == "あり（脳出血）" || json[i].value == "あり（くも膜下出血）" || json[i].value == "あり（種別不明）") {
                                        if(chkBox2.length===1){
                                            chkBox2.prop("checked",true);
                                            displayChange(aCtrlId[m] + "_dtype");
                                        }
                                    } else {
                                        chkBox2.prop("checked",false);
                                        displayChange(aCtrlId[m] + "_dtype");
                                    }
                                } else if (m <= 3){
                                    var chkBox2=$("#"+aCtrlId[m]+"_dtype");
                                    if(chkBox2.length===1){
                                        chkBox2.prop("checked",true);
                                        displayChange(aCtrlId[m] + "_dtype");
                                    }
                                }
                            }
                        }
                    }
                    if(myTdDate.length===1){
                        if (json[i].value != "") {
                            myTdDate[0].innerText=json[i].examinationDate;
	                        if(json[i].reminderFlg){
	                            myTdDate[0].style.color=bgColors[3];
	                        }else{
	                            myTdDate[0].style.color="#000000";
	                        }
                        }
                    }
                }
                if(setCnt1===0){
                    //過去未来矢印設定
                    var valBtnKensaLeft=$("#kensa_leftbtn");
                    if(valBtnKensaLeft.length===1 && json[i].leftFlg){
                        valBtnKensaLeft[0].setAttribute("href", "javascript:setStandardDate(\'"+json[i].leftFlg+"\');");
                        valBtnKensaLeft[0].style.display="";
                    }else{
                        valBtnKensaLeft[0].style.display="none";
                    }
                    var valBtnKensaRight=$("#kensa_rightbtn");
                    if(valBtnKensaRight.length===1 && json[i].rightFlg){
                        valBtnKensaRight[0].setAttribute("href", "javascript:setStandardDate(\'"+json[i].rightFlg+"\');");
                        valBtnKensaRight[0].style.display="";
                    }else{
                        valBtnKensaRight[0].style.display="none";
                    }
                    setCnt1=1;
                }
            }else if(json[i].dataInputTypeCd===2){
                //自己測定
                if(myTblKatei.length===1){
                    var tbody = myTblKatei[0].tBodies;
                    var thead = myTblKatei[0].tHead;
                    // ヘッダ列数取得
                    var cell_len = thead.rows[0].cells.length;
                    // ボディ行数取得
                    var row_len = tbody[0].rows.length;
                    // 行の挿入
                    var newRow;
                    if(row_len===0){
                        //最初の行を追加
                        newRow = tbody[0].insertRow(-1);
                    }else{
                        //既存行取得
                        newRow = tbody[0].rows[row_len-1];
//                        if(newRow.cells[0].textContent!==json[i].examinationDate){
                        if(json[i].eventId!==json[i-1].eventId){
                            //既存行が対象でない場合、行追加
                            newRow = tbody[0].insertRow(-1);
                        }
                    }
                    // セルの挿入(新行の場合のみ)
                    var cell;
                    if(newRow.cells.length===0){
                        for(var k = 0; k < cell_len; k++){
                            cell = newRow.insertCell(-1);
                            cell.className="r100 right";
                            // セルの内容入力
                            if(k===0){
                                cell.innerHTML = json[i].examinationDate;
                            }
                        }
                    }
                    // セルに値を設定
                    for(var l = 1; l < cell_len; l++){
                        var headCell = thead.rows[0].cells[l];
                        cell = newRow.cells[l];
                        if(headCell.id===json[i].id){
                            cell.innerHTML = json[i].value+" "+json[i].unit;
                            if(bgColors.length>=json[i].alertLevel && json[i].alertLevel>=0){
                                cell.style.backgroundColor = bgColors[json[i].alertLevel];
                            }else{
                                cell.style.backgroundColor = bgColors[0];
                            }
                            break;
                        }
                    }
                }
            }else if(json[i].dataInputTypeCd>=3 && json[i].dataInputTypeCd<=5){
                //健診
                var myTdVal1=$("#"+json[i].id+"_1");
                var myTdVal2=$("#"+json[i].id+"_2");
                var myTdVal3=$("#"+json[i].id+"_3");
                if(myTdVal1.length===1 && json[i].dispPos===1){
                    myTdVal1[0].innerText=json[i].value+" "+json[i].unit;
                    if(json[i].dataInputTypeCd===3 && dtkensin1===""){
                        dtkensin1=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===4 && dtmonsin1===""){
                        dtmonsin1=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===5 && dtsin1===""){
                        dtsin1=json[i].examinationDate;
                    }
                }
                if(myTdVal2.length===1 && json[i].dispPos===2){
                    myTdVal2[0].innerText=json[i].value+" "+json[i].unit;
                    if(json[i].dataInputTypeCd===3 && dtkensin2===""){
                        dtkensin2=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===4 && dtmonsin2===""){
                        dtmonsin2=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===5 && dtsin2===""){
                        dtsin2=json[i].examinationDate;
                    }
                }
                if(myTdVal3.length===1 && json[i].dispPos===3){
                    myTdVal3[0].innerText=json[i].value+" "+json[i].unit;
                    if(json[i].dataInputTypeCd===3 && dtkensin3===""){
                        dtkensin3=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===4 && dtmonsin3===""){
                        dtmonsin3=json[i].examinationDate;
                    }else if(json[i].dataInputTypeCd===5 && dtsin3===""){
                        dtsin3=json[i].examinationDate;
                    }
                }
                if(i===len-1 || json[i].dataInputTypeCd!==json[i+1].dataInputTypeCd){
                    //健診(検査)ヘッダ
                    var kensin_kensa=$("#kensin_kensa_date");
                    if(kensin_kensa.length===1){
                        if(dtkensin1===""){
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-3].innerText="　　　　";
                        }else{
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-3].innerText=dtkensin1;
                        }
                        if(dtkensin2===""){
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-2].innerText="　　　　";
                        }else{
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-2].innerText=dtkensin2;
                        }
                        if(dtkensin3===""){
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-1].innerText="　　　　";
                        }else{
                            kensin_kensa[0].cells[kensin_kensa[0].cells.length-1].innerText=dtkensin3;
                        }
                    }
                    //健診(問診)ヘッダ
                    var kensin_qus=$("#kensin_qus_date");
                    if(kensin_qus.length===1){
                        if(dtkensin1===""){
                            kensin_qus[0].cells[kensin_qus[0].cells.length-3].innerText="　　　　";
                        }else{
                            kensin_qus[0].cells[kensin_qus[0].cells.length-3].innerText=dtmonsin1;
                        }
                        if(dtmonsin2===""){
                            kensin_qus[0].cells[kensin_qus[0].cells.length-2].innerText="　　　　";
                        }else{
                            kensin_qus[0].cells[kensin_qus[0].cells.length-2].innerText=dtmonsin2;
                        }
                        if(dtmonsin3===""){
                            kensin_qus[0].cells[kensin_qus[0].cells.length-1].innerText="　　　　";
                        }else{
                            kensin_qus[0].cells[kensin_qus[0].cells.length-1].innerText=dtmonsin3;
                        }
                    }
                    //健診(診察)ヘッダ
                    var kensin_sin=$("#kensin_sin_date");
                    if(kensin_sin.length===1){
                        if(dtsin1===""){
                            kensin_sin[0].cells[kensin_sin[0].cells.length-3].innerText="　　　　";
                        }else{
                            kensin_sin[0].cells[kensin_sin[0].cells.length-3].innerText=dtsin1;
                        }
                        if(dtsin2===""){
                            kensin_sin[0].cells[kensin_sin[0].cells.length-2].innerText="　　　　";
                        }else{
                            kensin_sin[0].cells[kensin_sin[0].cells.length-2].innerText=dtsin2;
                        }
                        if(dtsin3===""){
                            kensin_sin[0].cells[kensin_sin[0].cells.length-1].innerText="　　　　";
                        }else{
                            kensin_sin[0].cells[kensin_sin[0].cells.length-1].innerText=dtsin3;
                        }
                    }
                }
                //過去未来矢印設定
                if(setCnt2===0 || (json[i].dataInputTypeCd===3 && json[i-1].dataInputTypeCd<3)){
                    var valBtnKensinLeft=$("#kensin-leftbtn");
                    if(valBtnKensinLeft.length===1 && json[i].leftFlg){
                        valBtnKensinLeft[0].setAttribute("href", "javascript:setStandardDate(\'"+json[i].leftFlg+"\');");
                        valBtnKensinLeft[0].style.display="";
                    }else{
                        valBtnKensinLeft[0].style.display="none";
                    }
                    var valBtnKensinRight=$("#kensin-rightbtn");
                    if(valBtnKensinRight.length===1 && json[i].rightFlg){
                        valBtnKensinRight[0].setAttribute("href", "javascript:setStandardDate(\'"+json[i].rightFlg+"\');");
                        valBtnKensinRight[0].style.display="";
                    }else{
                        valBtnKensinRight[0].style.display="none";
                    }
                    setCnt2=1;
                }
            }else if(json[i].dataInputTypeCd===-2){
                //おしらせ一覧情報
                var myOsiraseMsg=$("#osirase_msg_list");
                if(myOsiraseMsg.length===1){
                    var strA ="<a id=\"commu_"+json[i].id+"_"+json[i].seq+"\""
                                +" href=\"javascript:showDetailCommu(\'"+json[i].id+"\',\'"+json[i].seq+"\');\"";
                    if(json[i].readFlg===true){
                        strA = strA+" class=\"doneReadMsg\"";
                    }else{
                        strA = strA+" class=\"notReadMsg\"";
                    }
                    strA = strA+">"
                                +json[i].examinationDate+"&nbsp;&nbsp;&nbsp;&nbsp;"+json[i].name+"<br>"
                                +json[i].title
                                +"</a>";
                    if(json[i].readFlg===true){
                        strA = strA+"<HR id=\"comli_"+json[i].id+"_"+json[i].seq+"\" class=\"doneReadMsg\">";
                    }else{
                        strA = strA+"<HR id=\"comli_"+json[i].id+"_"+json[i].seq+"\" class=\"notReadMsg\">";
                    }
                    myOsiraseMsg[0].innerHTML=myOsiraseMsg[0].innerHTML+strA;
                }
            }else if (json[i].dataInputTypeCd===-1) {
                //おくすり一覧情報
                if(myTblKusuri.length===1){
                    // 情報表示
                    var tbody = myTblKusuri[0].tBodies;
                    var row = tbody[0].insertRow(-1);
                    // セルの挿入
                    var cell1 = row.insertCell(-1);
                    var cell2 = row.insertCell(-1);
                    var cell3 = row.insertCell(-1);
                    // ボタン用 HTML
                    var button = "<a href=\""
                            +"javascript:showDetailKusuri(\'"
                            +json[i].id+"\',\'"+json[i].seq
                            +"\');\""
                            +"class=\"button button-blue\" style=\"float:right;\"><span>参照</span></a>";
                    // セルの内容入力
                    cell1.className="r100 right";
                    cell2.className="r200 left";
                    cell3.className="r100 center";
                    cell1.innerHTML = json[i].examinationDate;
                    cell2.innerHTML = json[i].name;
                    cell3.innerHTML = button;
                }
            }else if (json[i].dataInputTypeCd<=-3) {
                if(setCnt3===0){
                    // 検査日・項目欄の色
                    if (json[i].dataInputTypeCd===-3) {
                        allKensaTBL[0].rows[0].cells[0].innerText=json[i].medicalOrganizationName + "("+json[i].examinationDate+")";
                    }else if(json[i].dataInputTypeCd===-4){
                        allKensaTBL[0].rows[0].cells[0].innerText="自己測定("+json[i].examinationDate+")";
                    }else if(json[i].dataInputTypeCd===-5){
                        allKensaTBL[0].rows[0].cells[0].innerText="特定健診("+json[i].examinationDate+")";
                    }
                    
                    // 項目欄の色
                    allKensaTBL[0].rows[1].cells[0].style.backgroundColor=json[i].headerColor;
                    allKensaTBL[0].rows[1].cells[1].style.backgroundColor=json[i].headerColor;
                    allKensaTBL[0].rows[1].cells[2].style.backgroundColor=json[i].headerColor;
                    
                    //過去未来矢印設定(測定・全検査結果画面は←過去、→未来)
                    // var valBtnKensaLeft=$("#allkensa_leftbtn");
                    var valBtnKensaLeft=$("#allkensa_rightbtn");
                    if(valBtnKensaLeft.length===1 && json[i].leftFlg){
                        valBtnKensaLeft[0].setAttribute("href", "javascript:getAllTabData(\'"+json[i].leftFlg+"\');");
                        valBtnKensaLeft[0].style.display="";
                    }else{
                        valBtnKensaLeft[0].style.display="none";
                    }
                    // var valBtnKensaRight=$("#allkensa_rightbtn");
                    var valBtnKensaRight=$("#allkensa_leftbtn");
                    if(valBtnKensaRight.length===1 && json[i].rightFlg){
                        valBtnKensaRight[0].setAttribute("href", "javascript:getAllTabData(\'"+json[i].rightFlg+"\');");
                        valBtnKensaRight[0].style.display="";
                    }else{
                        valBtnKensaRight[0].style.display="none";
                    }
                    setCnt3=1;
                }
                
                // 情報表示
                var tbody = allKensaTBL[0].tBodies;
                var row = tbody[0].insertRow(-1);
                // セルの挿入
                //td分追加
                var cell1 = row.insertCell(-1);
                var cell2 = row.insertCell(-1);
                var cell3 = row.insertCell(-1);
                // セルの内容入力
                cell1.innerHTML = json[i].name;
                cell2.innerHTML = json[i].referenceValue;
                cell3.innerHTML = json[i].value;
                
                // tableのclass設定
                cell1.className = 'r150 left';
                cell2.className = 'r60 right';
                cell3.className = 'r90 right';
                
                // 値の背景色
                cell3.style.backgroundColor = json[i].alertColor;
            }
        }
        if(len===0){
            //検索０件のメッセージ表示
            jAlert("基準日の直近の患者情報はありませんでした。","");
        }
    }
    function setStandardDate(info){
        var doc=document;
        var target = doc.getElementById("basedate");
        target.value = info;
        getTargPatiInfo();
    }
    function getAllTabData(num){
        getObservationDosageAjax(num);
    }
    function showDetailKusuri(info,seqNo){
        var doc=document;
        var target = doc.getElementById("medicineDetail");
        target.style.display = "";
        var tarFoot = doc.getElementById("medicinefooter");
        tarFoot.style.display = "";
        var listdisplay = doc.getElementById("kusuri_view");
        listdisplay.style.display = "none";
        getDetailAjax(info,seqNo);
    }
    function getDetailAjax(targetId,seqNo) {
	var strData = {
            value1: targetId,
            value2: seqNo
        };
        var postData = JSON.stringify(strData);
	var postUrl = "getJsonDataKusuri";
        $.ajax({
            url: postUrl,
            type: "POST",
            dataType: "json",
            data: postData,
            contentType: "application/JSON",
            success: function(json){
                //おくすり情報
                if(json){
                    var doc = document;
                    var valDosageDate=$("#dosageDate");
                    var valMediName=$("#medicalOrganizationName");
                    var valPharmacy=$("#pharmacy");
                    var valDoctorName=$("#doctorName");
                    var valPharmaName=$("#pharmaceutistName");
                    var tblConte=$("#medicineDetailData");
                    var valDosageRemarks=$("#dosageRemarks");
                    var nowCreateTbl;
                    
                    if(tblConte.length===1){
                        // おくすり詳細一旦全削除する
                        var tbllen=tblConte[0].children.length;
                        for(var k = tbllen-1; k > 0; k--) {
                            tblConte[0].removeChild(tblConte[0].children.item(k)); 
                        }
                    }
                    
                    // 情報表示
                    var len=json.length;
                    var j=0;
                    var tbody;
                    for(var i = 0; i < len; i++) {
                        if(i===0){
                            // ヘッダ部内容入力
                            if(valDosageDate.length===1){
                                valDosageDate[0].innerText = json[i].dosageDate;
                            }
                            if(valMediName.length===1){
                                valMediName[0].innerText=json[i].medicalOrganizationName;
                            }
                            if(valPharmacy.length===1){
                                valPharmacy[0].innerText=json[i].pharmacy;
                            }
                            if(valDoctorName.length===1){
                                valDoctorName[0].innerText=json[i].doctorName;
                            }
                            if(valPharmaName.length===1){
                                valPharmaName[0].innerText=json[i].pharmaceutistName;
                            }
//                            if(valDosageRemarks.length===1){
//                                valDosageRemarks[0].innerText=json[i].dosageRemark;
//                            }
                            var valBtnLeft=$("#medicinefooterleft");
                            if(valBtnLeft.length===1 && json[i].leftFlg){
                                valBtnLeft[0].setAttribute("href", "javascript:showDetailKusuri("+json[i].leftFlg+");");
                                valBtnLeft[0].style.display="";
                            }else{
                                valBtnLeft[0].style.display="none";
                            }
                            var valBtnRight=$("#medicinefooterright");
                            if(valBtnRight.length===1 && json[i].rightFlg){
                                valBtnRight[0].setAttribute("href", "javascript:showDetailKusuri("+json[i].rightFlg+");");
                                valBtnRight[0].style.display="";
                            }else{
                                valBtnRight[0].style.display="none";
                            }
                        }
                        if(tblConte.length===1){
                            // ボディ部内容入力
                            if(j===0 || (!(json[i-1].dosageId===json[i].dosageId && 
                                        json[i-1].seq===json[i].seq && 
                                        json[i-1].dosageTypeCd===json[i].dosageTypeCd && 
                                        json[i-1].recipeNo===json[i].recipeNo))){
                                if(j!==0 && nowCreateTbl.length===1){
                                    // 作成したbodyをテーブルに書き出し
                                    nowCreateTbl[0].appendChild(tbody);
                                }
                                // ボディheadを初回のみ作成
                                tblConte.append("<table id=\"CreateTbl_"+j+"\" class=\"medicine-body\"></table>");
                                nowCreateTbl=$("#CreateTbl_"+j);
                                var thead = doc.createElement("thead");
                                var tr = doc.createElement("tr");
                                var th1 = doc.createElement("th");
                                var th2 = doc.createElement("th");
                                var textnode1 = doc.createTextNode(json[i].dosageForms+":"+json[i].usageName);
                                var textnode2 = doc.createTextNode(json[i].dosageQuantity+json[i].dosageUnit);
                                if(json[i].dosageQuantity===-1){
                                    textnode2 = doc.createTextNode(json[i].dosageUnit);
                                }
                                th1.appendChild(textnode1);
                                th2.appendChild(textnode2);
                                tr.appendChild(th1);
                                tr.appendChild(th2);
                                thead.appendChild(tr);
                                if(nowCreateTbl.length===1){
                                    // テーブルにhead書き出し
                                    nowCreateTbl[0].appendChild(thead);
                                }
                                // ボディのbody内容を作成
                                tbody = doc.createElement("tbody");
                                var trb = doc.createElement("tr");
                                var td1=doc.createElement("td");
                                td1.className = 'r250 center';
                                var td2=doc.createElement("td");
                                td2.className = 'r100 center';
                                var textnodeb1 = doc.createTextNode(json[i].medicineName);
                                var textnodeb2 = doc.createTextNode(json[i].amount+json[i].unitName);
                                td1.appendChild(textnodeb1);
                                td2.appendChild(textnodeb2);
                                trb.appendChild(td1);
                                trb.appendChild(td2);
                                tbody.appendChild(trb);
                                j+=1;
                            }else{
                                var trb = doc.createElement("tr");
                                var td1=doc.createElement("td");
                                td1.className = 'r250 center';
                                var td2=doc.createElement("td");
                                td2.className = 'r100 center';
                                var textnodeb1 = doc.createTextNode(json[i].medicineName);
                                var textnodeb2 = doc.createTextNode(json[i].amount+json[i].unitName);
                                td1.appendChild(textnodeb1);
                                td2.appendChild(textnodeb2);
                                trb.appendChild(td1);
                                trb.appendChild(td2);
                                tbody.appendChild(trb);
                            }
                            if(i===(len-1)){
                                // 最後はテーブルに書き出し
                                nowCreateTbl[0].appendChild(tbody);
                            }
                        }
                    }
                    if(len===0){
                        //検索０件のメッセージ表示
                        jAlert("情報はありませんでした。","");
                    }
                }
            },
            error: function() {
                //検索エラー
                jAlert("情報取得に失敗しました。","");
            }
	});
    }
    function showDetailCommu(info,seq){
        getCommuDetailAjax(info,seq);
        windowModalOpen();
    }
    //コミニュケーション情報の詳細を取得するAjax処理
    function getCommuDetailAjax(targetId,seq) {
	var strData = {
            value1: targetId,
            value2: seq,
            value3: $("#unread").prop('checked')
        };
        var postData = JSON.stringify(strData);
	var postUrl = "getJsonDataCommu";
        $.ajax({
            url: postUrl,
            type: "POST",
            dataType: "json",
            data: postData,
            contentType: "application/JSON",
            success: function(json){
                //コミニュケーション情報
                if(json){
                    var commuType=$("#commu-type");
                    var commuSend=$("#commu-send");
                    var commuDate=$("#commu-date");
                    var commuSubject=$("#commu-subject");
                    var commuBodyText=$("#commu-bodyText");
                    var commuBtnLeft=$("#modal-left");
                    var commuBtnRight=$("#modal-right");
                    // 情報表示
                    var len=json.length;
                    for(var i = 0; i < len; i++) {
                        if(commuType.length===1){
                            commuType[0].innerText=json[i].communicationTypeName;
                        }
                        if(commuSend.length===1){
                            commuSend[0].innerText=json[i].sendOrganizationName
                                                    +"\r\n"
                                                    +json[i].senderName;
                        }
                        if(commuDate.length===1){
                            commuDate[0].innerText=json[i].strUpdateDateTime;
                        }
                        if(commuSubject.length===1){
                            commuSubject[0].innerText=json[i].subject;
                        }
                        if(commuBodyText.length===1){
                            commuBodyText[0].innerText=json[i].bodyText;
                        }
                        if(commuBtnLeft.length===1 && json[i].leftFlg){
                            commuBtnLeft[0].setAttribute("href", "javascript:getCommuDetailAjax("+json[i].leftFlg+");");
                            commuBtnLeft[0].style.display="";
                        }else{
                            commuBtnLeft[0].style.display="none";
                        }
                        if(commuBtnRight.length===1 && json[i].rightFlg){
                            commuBtnRight[0].setAttribute("href", "javascript:getCommuDetailAjax("+json[i].rightFlg+");");
                            commuBtnRight[0].style.display="";
                        }else{
                            commuBtnRight[0].style.display="none";
                        }
                        if(json[i].readFlg){
                            //一覧の未読情報クリア
                            var commuA=$("#commu_"+json[i].communicationId+"_"+json[i].seqNo);
                            var commuHR=$("#comli_"+json[i].communicationId+"_"+json[i].seqNo);
                            commuA.attr("class","doneReadMsg");
                            commuHR.attr("class","doneReadMsg");
                            //未読制御の呼び出し
                            changeMsgList();
                        }
                    }
                    if(len===0){
                        //検索０件のメッセージ表示
                        jAlert("情報はありませんでした。","");
                    }
                }
            },
            error: function() {
                //検索エラー
                jAlert("情報取得に失敗しました。","");
            }
	});
    }
    //おくすり情報の詳細を消し、一覧を表示する
    function showList(){
        var doc=document;
        var target = doc.getElementById("medicineDetail");
        target.style.display = "none";
        var tarFoot = doc.getElementById("medicinefooter");
        tarFoot.style.display = "none";
        var listdisplay = doc.getElementById("kusuri_view");
        listdisplay.style.display = "";
    }
    //患者情報の詳細を取得するAjaxを呼び出す起点の関数
    function getTargPatiInfo() {
        getObservationDosageAjax(0);
    }
    //自動submitを検出し、エラー防止する
    function submitTrigger(e){
        if(!e){
            e = window.event;
        }
        if(e.keyCode === 13){
            getTargPatiInfo();
            return false;   //submitが飛ばないようにfalseを戻す
        }
    }
    function openwin(pass) {
	window.open("./" + pass, "", "width=600,height=450");
    }

    //モーダルウィンドウを出現させるクリックイベント
    function windowModalOpen() {
        //キーボード操作などにより、オーバーレイが多重起動するのを防止する
        $( this ).blur() ;	//ボタンからフォーカスを外す
        if( $( "#modal-overlay" )[0] ) return false ;		//新しくモーダルウィンドウを起動しない (防止策1)
        //if($("#modal-overlay")[0]) $("#modal-overlay").remove() ;		//現在のモーダルウィンドウを削除して新しく起動する (防止策2)

        //オーバーレイを出現させる
        $( "body" ).append( '<div id="modal-overlay"></div>' ) ;
        $( "#modal-overlay" ).fadeIn( "slow" ) ;

        //コンテンツをセンタリングする
        centeringModalSyncer() ;

        //コンテンツをフェードインする
        $( "#modal-content" ).fadeIn( "slow" ) ;

        //[#modal-overlay]、または[#modal-close]をクリックしたら…
        $( "#modal-overlay,#modal-close" ).unbind().click( function(){
            //[#modal-content]と[#modal-overlay]をフェードアウトした後に…
            $( "#modal-content,#modal-overlay" ).fadeOut( "slow" , function(){
                //[#modal-overlay]を削除する
                $('#modal-overlay').remove() ;
            } ) ;
        } ) ;
    }
    //センタリングを実行する関数
    function centeringModalSyncer() {
        //画面(ウィンドウ)の幅、高さを取得
        var w = $( window ).width() ;
        var h = $( window ).height() ;

        // コンテンツ(#modal-content)の幅、高さを取得
        var cw = $( "#modal-content" ).outerWidth();
        var ch = $( "#modal-content" ).outerHeight();
        if((h - ch)<80){ch=h-80;}

        //センタリングを実行する
//        $( "#modal-content" ).css( {"left": ((w - cw)/2) + "px","top": ((h - ch)/2) + "px"} ) ;
        $( "#modal-content" ).css( {"left": ((w - cw)/2) + "px"} ) ;
    }
    
    //コミュニケーション情報の未読のみ表示
    function changeMsgList(){
        if($("#unread").prop('checked')){
            //一括非表示
            $(".doneReadMsg").hide();
        }else{
            //一括表示
            $(".doneReadMsg").show();
        }
    }
    function openPopUpPatientWindow() {
        openwin("targetingPatientMessage");
    }
    // メッセージ領域消去
    window.onload = onLoad;
    var checkok=false;
    <c:if test="${empty accountEntity}">
        checkok = true;
    </c:if>
    function onLoad() {
        var target = document.getElementById("makeMessage");
        if( checkok == true ){
            if( target !== null ){
                target.style.display = "none";
            }
        }
    }
</script>
<style>
    #modal-content{
        width:50%;
	margin:0;
	padding:10px 20px;
	position: fixed ;
	display: none ;
	z-index: 2 ;
        clear: both;
	max-height:600px;
	background-color:#fff;
	border:solid #ADE 1px;
	border-radius:10px;
    }
    #modal-overlay{
	z-index:1;
	display:none;
	position:fixed;
	top:0;
	left:0;
	width:100%;
	height:120%;
	background-color:rgba(0,0,0,0.75);
    }

    .modal-p{
	margin-top:1em;
    }

    .modal-p:first-child{
	margin-top:0;
    }

    .button-link{
	color:#00f;
	text-decoration:underline;
    }
 
    .button-link:hover{
	cursor:pointer;
	color:#f00;
    }
</style>
</head>
    
<body>
<form:form modelAttribute="targetingPatientInfoForm" name ="targetingPatientInfoForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    
<div style="text-align:left; margin:0em auto 0; width:900px; ">
<div class="headerinfo">
</div><!-- / header end -->

<div id="container">
<div id="contents"><!-- コンテンツ ここから -->
    <div class="menubox">
	<div style="float:left;">
	       <c:if test="${not empty accountEntity}">
            <a href= "javascript:unlockMyself();" class="button button-blue">
                <span>指定解除</span>
            </a>
            </c:if>
            <a href= "javascript:closeMyself();" class="button button-blue">
    		<span>閉じる</span>
            </a>	
        </div>
	
        <div class="selectedPatient"  onclick="showPopup(event);">
            対象者：${targetingPatientInfoForm.phrid}</br>
            氏名　：${targetingPatientInfoForm.name}
	</div>
    </div>
    <div id="selectedPtInfo" style="display:none; height: auto;">
        <table class="green-header">
            <tbody>
                <tr>
                        <th class="r100 center">PHR ID</th>
                        <td class="r150 center" path="phrid">${targetingPatientInfoForm.phrid}</td>
                </tr>
                <tr>
                        <th class="r100 center">氏名</th>
                        <td class="r150 center" path="name">${targetingPatientInfoForm.name}</td>
                </tr>
                <tr>
                        <th class="r100 center">カナ氏名</th>
                        <td class="r150 center" path="kananame">${targetingPatientInfoForm.kananame}</td>
                </tr>
                <tr>
                        <th class="r100 center">生年月日</th>
                        <td class="r150 center" path="birthday">${targetingPatientInfoForm.birthday}</td>
                </tr>
                <tr>
                        <th class="r100 center">性別</th>
                        <td class="r150 center" path="sex">${targetingPatientInfoForm.sex}</td>
                </tr>
                <tr>
                        <th class="r100 center">住所</th>
                        <td class="r150 center">
                                ${targetingPatientInfoForm.zipcode}<br/>
                                ${targetingPatientInfoForm.address}
                        </td>
                </tr>
                <tr>
                        <th class="r100 center">電話番号</th>
                        <td class="r150 center" path="telnumber">${targetingPatientInfoForm.telnumber}</td>
                </tr>			
            </tbody>
        </table>
        <a href="javascript:hidePopup();" class="button button-blue" style="float:right;"><span>閉じる</span></a>
    </div>

    <div><span id="itemErrors" style="color:red;font-weight:normal;font-size:small;display:none"></span></div>
    <div class="standardDate">
        <p>基準日&nbsp;
        <form:input path="basedate" class="datepicker" type="text" maxlength="10" onchange="javascript:getTargPatiInfo();" onKeyPress="return submitTrigger(event);"/>
        </p>
        <p style="margin-top: 5px">
        View選択&nbsp;
        <form:select path="selectView" items="${targetingPatientInfoForm.viewList}" itemLabel="viewName" itemValue="viewId" onchange="javascript:submitForm('changeview');"/>
        </p>
    </div>
		
    <div class="selectDisease" style="width: 580px;">
        <div style="float:left;padding: 17px 10px;">管理疾患</div>
        <div style="float:right;padding: 11px 1px;">
            <div class="diabetes-button">
            <c:if test="${targetingPatientInfoForm.diseaseList != null}">
            <c:forEach varStatus="status" var="diseaseList" items="${targetingPatientInfoForm.diseaseList}">
                <input id="${diseaseList.diseaseTypeCd}_dtype" type="checkbox" onclick="displayChange('${diseaseList.diseaseTypeCd}_dtype');" value="${diseaseList.diseaseTypeCd}" />
                <label for="${diseaseList.diseaseTypeCd}_dtype" style="margin: 0 1px 0;">${diseaseList.name}</label>
            </c:forEach>
            </c:if>
            </div>
        </div>
    </div>

    <div class="communication" id="communication">
        お知らせ/メッセージ
        <div id="osirase_msg_list" class="scroll" style="height:380px;">
        </div>
        <input id="unread" type="checkbox" onclick="changeMsgList();"/><label for="unread">未読のみ表示</label>
        <div id="makeMessage">
            <a href="javascript:openPopUpPatientWindow();" class="button button-blue" style="float:right;"><span>新規作成</span></a>
        </div>
    </div>
    <div id="modal-content">
        <p><a id="modal-close" class="button-link" style="float: right; margin: 0 0 0 5px;">閉じる</a></p>
        <p style="float: right; margin: 0 5px 0 0;"><a id="modal-right" class="blue_sankaku_right"></a></p>
        <p style="float: right; position:absolute; right:90px;"><a id="modal-left"class="blue_sankaku_left"></a></p>
        <section class="scroll" style="max-height:450px;">
            <p id="commu-type"></p>
            <HR/>
            <p id="commu-send"></p>
            <HR/>
            <p id="commu-date" style="float: right;"></p><br/>
            <p id="commu-subject"></p>
            <HR/>
            <p id="commu-bodyText">
            </p>
        </section>
    </div>

    <div class="mainScreen">
        <div class="tabbtn">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <li id="btn1" class="tab active"><a href="#kensa">自己管理セット</a></li>
            <li id="btn5" class="tab"><a href="#allkensa">測定・全検査結果</a></li>
            <li id="btn2" class="tab"><a href="#kensin">健診結果</a></li>
            <li id="btn3" class="tab"><a href="#kusuri">おくすり情報</a></li>
            <li id="btn4" class="tab"><a href="#katei">自己測定</a></li>
        </div>
        <div class="tabcontents">
            <div id="kensa" class="content active" style="height:440px;">
                <div class="scroll" style="height:400px;">
                <table class="fmt08" style="margin:2em auto 0;width:400px;" id="myTBL">
                    <thead>
                        <tr>
                            <!--<th></th>-->
                            <th>項目</th>
                            <th>値</th>
                            <th>更新日</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach varStatus="status" var="observationList" items="${targetingPatientInfoForm.observationList}">
                            <tr id="${observationList.observationDefinitionId}_kind">
                                <!--<td class="r20 center" ><input type="checkbox" id="${observationList.observationDefinitionId}_box" name="${observationList.observationDefinitionId}_box" value="item01"/></td>-->
                                <td class="r150 left">${observationList.displayName}</td>
                                <td class="r60 right" id="${observationList.observationDefinitionId}" name="${observationList.observationDefinitionId}"></td>
                                <td class="r90 right" id="${observationList.observationDefinitionId}_date" name="${observationList.observationDefinitionId}_date"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
                <div id="kensa_btn" style="height:15px;">
                    <a id="kensa_leftbtn" class="blue_sankaku_left" style="float:left; position:relative; left:10px; display:none;"></a>
                    <a id="kensa_rightbtn" class="blue_sankaku_right" style="float:right; display:none;"></a>
                </div>
            </div>
            <div id="allkensa" class="content" style="height:440px;">
                <div class="scroll" style="height:400px;">
                
                <table class="fmt08" style="margin:2em auto 0;width:400px;" id="allKensaTBL">
                    <thead>
                        <tr>
                            <td colspan="3" style="text-align: center;background-color: #006699;font-weight: bold;color:#ffffff"></td>
                        </tr>
                        <tr>
                            <th>検査項目</th>
                            <th>基準値</th>
                            <th>値</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                </div>
                <div id="kensa_btn" style="height:15px;">
                    <a id="allkensa_leftbtn" class="blue_sankaku_left" style="float:left; position:relative; left:10px; display:none;"></a>
                    <a id="allkensa_rightbtn" class="blue_sankaku_right" style="float:right; display:none;"></a>
                </div>
            </div>
            
            <div id="kensin" class="content" style="height:450px;">
                <div class="scroll"  id="kensin_kensa" style="height:400px;">
                    <table id="kensin_kensa_tb" class="fmt08" style="margin:2em auto 0;width:450px;">
                        <thead>
                            <tr id="kensin_kensa_date">
                                <!--<th></th>-->
                                <th>項目</th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach varStatus="status" var="kensinOList" items="${targetingPatientInfoForm.kensinOList}">
                            <tr>
                                <td class="r150 left">${kensinOList.displayName}</td>
                                <td class="r60 right" id="${kensinOList.observationDefinitionId}_1" name="${kensinOList.observationDefinitionId}_1"></td>
                                <td class="r60 right" id="${kensinOList.observationDefinitionId}_2" name="${kensinOList.observationDefinitionId}_2"></td>
                                <td class="r60 right" id="${kensinOList.observationDefinitionId}_3" name="${kensinOList.observationDefinitionId}_3"></td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                </div>

                <div class="scroll"  id="kensin_qus" style="height:400px; display:none;">
                    <table id="kensin_qus_tb" class="fmt08" style="margin:2em auto 0;width:450px;">
                        <thead>
                            <tr id="kensin_qus_date">
                                    <!--<th></th>-->
                                    <th>項目</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach varStatus="status" var="kensinQList" items="${targetingPatientInfoForm.kensinQList}">
                                <tr>
                                    <td class="r150 left">${kensinQList.displayName}</td>
                                    <td class="r60 right" id="${kensinQList.observationDefinitionId}_1" name="${kensinQList.observationDefinitionId}_1"></td>
                                    <td class="r60 right" id="${kensinQList.observationDefinitionId}_2" name="${kensinQList.observationDefinitionId}_2"></td>
                                    <td class="r60 right" id="${kensinQList.observationDefinitionId}_3" name="${kensinQList.observationDefinitionId}_3"></td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>

                <div class="scroll"  id="kensin_sin" style="height:400px; display:none;">
                    <table id="kensin_sin_tb" class="fmt08" style="margin:2em auto 0;width:450px;">
                        <thead>
                            <tr id="kensin_sin_date">
                                <!--<th></th>-->
                                <th>項目</th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach varStatus="status" var="kensinSList" items="${targetingPatientInfoForm.kensinSList}">
                                <tr>
                                    <td class="r150 left">${kensinSList.displayName}</td>
                                    <td class="r60 right" id="${kensinSList.observationDefinitionId}_1" name="${kensinSList.observationDefinitionId}_1"></td>
                                    <td class="r60 right" id="${kensinSList.observationDefinitionId}_2" name="${kensinSList.observationDefinitionId}_2"></td>
                                    <td class="r60 right" id="${kensinSList.observationDefinitionId}_3" name="${kensinSList.observationDefinitionId}_3"></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="kensin-displaybtn">
                    <a id="kensin-leftbtn" class="blue_sankaku_left" style="float:left; position:relative; left:10px; display:none;"></a>
                    <input id="kensinkensin" name="kensin" type="radio" value="kensin" onclick="kensinChange('1');" checked/>
                    <label for="kensinkensin">健診</label>
                </div>
                <div class="kensin-displaybtn">
                    <input id="kensinmonsin" name="kensin" type="radio" value="monsin" onclick="kensinChange('2');"/>
                    <label for="kensinmonsin">問診</label>
                </div>
                <div class="kensin-displaybtn">
                    <input id="kensinsinsatsu" name="kensin" type="radio" value="sinsatsu" onclick="kensinChange('3');"/>
                    <label for="kensinsinsatsu" style="margin:0 15px 10px 35px;">診察</label>
                    <a id="kensin-rightbtn" class="blue_sankaku_right" style="float:right; display:none;"></a>
                </div>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="Sample_graph.html" class="button button-blue" id="kensin-graph" style="display:none;"><span>グラフ作成</span></a>
            </div>
            <div id="kusuri" class="content">
                <div class="scroll" style="height:400px;" id="kusuri_view">
                    <table id="kusuri_table" class="fmt08" style="margin:2em auto 0;width:450px;">
                        <thead>
                            <tr>
                                <!--<th></th>-->
                                <th>日付</th>
                                <th>薬局-医療機関</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            
                <div id="medicineDetail" class="scroll" style="height:390px; display:none;">
                    <div id="medicineDetailData">
                        <table class="green-header">
                            <tbody>
                                <tr>
                                    <th class="r100 center">調剤実施日</th><td id="dosageDate" class="r150 center"></td>
                                    <th class="r100 center">医療機関名</th><td id="medicalOrganizationName" class="r150 center"></td>
                                </tr>
                                <tr>
                                    <th class="r100 center">薬局名</th><td id="pharmacy" class="r150 center"></td>
                                    <th class="r100 center">医師名</th><td id="doctorName" class="r150 center"></td>
                                </tr>
                                <tr>
                                    <th class="r100 center">薬剤師名</th><td id="pharmaceutistName" class="r150 center"></td>
                                    <th class="r100 center"></th><td id="dosageRemarks" class="r150 center"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="medicinefooter" style="height:15px;display:none;">
                    <a href="javascript:showList();" class="button button-blue" style="float:left;margin:0 0 0 10px;"><span>一覧へ</span></a>
                    <a id="medicinefooterleft" class="blue_sankaku_left" style="float:left; position:relative; left:10px;"></a>
                    <a id="medicinefooterright" class="blue_sankaku_right" style="float:right;"></a>
                </div>
            </div>
            
            <div id="katei" class="content">
                <div class="scroll" style="height:400px;">
                    <table id="katei_table" class="fmt08" style="margin:2em auto 0;width:450px;">
                        <thead>
                            <tr>
                                <!--<th></th>-->
                                <th>更新日</th>
                                <c:forEach varStatus="status" var="kateiList" items="${targetingPatientInfoForm.kateiList}">
                                   <th id="${kateiList.observationDefinitionId}">${kateiList.displayName}</th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</div><!-- / contents end -->
</div><!-- / container end -->

<div id="footer">
    <!-- コピーライト -->
    <div id="footer"><font style="float:right;font-weight:bold;color:#002182;">BuildVer：${buildVer}&nbsp;&nbsp;</font></div>
</div><!-- / footer end -->
</div><!-- / wrapper end -->
<SCRIPT type="text/javascript">
    var display_map = new Map();
    var condition_map = new Map();
    <c:forEach varStatus="status" var="displayList" items="${targetingPatientInfoForm.displayList}">
        display_map.set( "${displayList.key}" , "${displayList.value}" );
    </c:forEach>

    <c:forEach varStatus="status" var="conditionList" items="${targetingPatientInfoForm.conditionList}">
        condition_map.set( "${conditionList.key}" , "${conditionList.value}" );
    </c:forEach>

    function displayChange(id){
        var value = document.getElementById(id).value;
        var results = display_map.get(value);
        var result = [];
        if(results){
            result = results.split(",");
        }
        for (var i = 0; i < result.length; i++) {
            var targetid = result[i];
            var target = document.getElementById(targetid);
            var condition = condition_map.get(targetid.replace("_kind",""));
            var conditions = [];
            conditions = condition.split(",");
            var flg = true;

            for(var m=0; m < conditions.length;m++){
                flg = document.getElementById(conditions[m] + "_dtype").checked;
                if(flg){
                    break;
                }
            }
            if(flg){
                target.style.display="";
            }else{
                target.style.display="none";
            }
        }    
    }

    initPage();
</SCRIPT>

</form:form>
<form id="targetingPatientMessage" target="_self" action="./targetingPatientMessage" method="post">
    <input id="command" name="command" type="hidden" value="searchtargetingPatientMessage"/>
</form>

</body>
</html>
