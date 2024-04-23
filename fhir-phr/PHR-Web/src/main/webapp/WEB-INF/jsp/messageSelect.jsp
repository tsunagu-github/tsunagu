<%-- 
    Document   : messageSelect
    Created on : 2016/08/26, 10:51:48
    Author     : kis.o-note-003
--%>
<html>
<head>
<META http-equiv="Content-Language" content="ja"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />

<title>メッセージ詳細画面</title>

<link href="css/import.css" rel="stylesheet" type="text/css" />
<LINK href="css/jquery.qtip.css" rel="stylesheet" type="text/css">
<LINK href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"> 
<LINK href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css"> 
<script src="js/common.js" type="text/javascript"></script>
<SCRIPT src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT> 
<SCRIPT src="js/jquery/jquery-ui.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.ui.datepicker-ja.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.colorbox-min.js" type="text/javascript"></SCRIPT>
</head>

<script language="javascript" type="text/javascript">
$(function() {
    $(".inline").colorbox({
    inline:true,
    rel:'group',
    loop:false,
    maxWidth:'100%',
    maxHeight:'100%',
    opacity: 0.0
  });
});

    function showPopup2() {
      var target = document.getElementById("selecthospital");
		target.style.position = "absolute";
      //if (!event) var event = window.event;
      //if (!event.pageX) px = event.clientX + document.body.scrollRight; else px = event.pageX;
      //if (!event.pageY) py = event.clientY + document.body.scrollTop; else py = event.pageY;
		
		var img_x = document.documentElement.clientWidth;
		var img_y = document.documentElement.clientHeight;
		
		//表示位置を中央に調節（ブラウザ画面サイズの半分－画像サイズの半分）//*7
		img_x = (img_x / 10) ;//- (img_w / 2);
		img_y = (img_y / 20) ;//- (img_h / 2);

		//画面のスクロール量を加算
		img_x += document.documentElement.scrollLeft || document.body.scrollLeft;
		img_y += document.documentElement.scrollTop || document.body.scrollTop;

        target.style.left = img_x + "px";//px-300 + "px";
        target.style.top = img_y + "px";//py+5 + "px";
        target.style.display = "block";
    }
    
    function hidePopup() {
      target = document.getElementById("selecthospital");
      target.style.display = "none";
    }
    
    
    function showHospital(){
    	var hospitals = document.getElementsByName("hospitallist");
    	var target = document.getElementById("targetHospital");
    	var hospitallist="";
    	for(var i=0,l=hospitals.length; l>i; i++){
    		if(hospitals[i].checked){
    			hospitallist = hospitallist+ hospitals[i].value +"<br/>";
    		}
    	}
    	target.innerHTML = hospitallist;
    	hidePopup();
    }
    
    function messageReply() {
        
    }
    
    function messageCheck(){
        var body = document.forms.messageSelectForm.bodyText.value;
        var errMSG = document.getElementById("errMSG");
        var chkMSG = document.getElementById("chkMSG");
        
        if (body==="" || body===null) {
            errMSG.style.display = "";
            chkMSG.style.display = "none";
        } else {
            document.forms.messageSelectForm.subject.disabled = "true";
            document.forms.messageSelectForm.bodyText.disabled = "true";
            document.getElementById("hospital").style.display = "none";
            errMSG.style.display = "none";
            chkMSG.style.display = "";
            document.getElementById("close").style.display = "none";
            document.getElementById("check").style.display = "none";
            document.getElementById("reply").style.display = "";
            document.getElementById("resist").style.display = "";
        }
    }
    
    function messageRegist(command){        
        var form = document.forms[0];
        if (form.command != null) {
            form.command.value = command;
	}
        form.submit();
        window.alert("メッセージを登録しました。");
        window.close();
    }
    
    function messageClose(){
        window.opener = "myself";
        window.close();
    }
 </script>

<form:form modelAttribute="messageSelectForm" name="messageSelectForm" action="${action}">
<form:hidden path="command"/>
<form:hidden path="formCd"/>
<form:hidden path="formName"/>

<body>
<div class="message-list" style="height:390px;">
    <div class="scroll" style="width:500px;height:530px;">
        <!-- メッセージ詳細画面 -->
	<div id="message1">
            <p>メッセージ</p>
            <HR/>
            <br/>
            <c:if test="${messageSelectForm.messageDetailList.senderStatus == 1}">
            <p>${messageSelectForm.messageDetailList.sendInsurerNo}<br/>${messageSelectForm.messageDetailList.senderName}</p>
            </c:if>
            <c:if test="${messageSelectForm.messageDetailList.senderStatus == 2}">
            <p>${messageSelectForm.messageDetailList.recPhrId}<br/>${messageSelectForm.messageDetailList.recPhrName}</p><br/><HR/>
            <p>${messageSelectForm.messageDetailList.sendMedicalOrganizationCd}<br/>${messageSelectForm.messageDetailList.senderName}</p>
            </c:if>
            <c:if test="${messageSelectForm.messageDetailList.senderStatus == 3}">
            <p>${messageSelectForm.messageDetailList.sendPHRID}<br/>${messageSelectForm.messageDetailList.senderName}</p>
            </c:if>
                <c:if test="${not empty messageSelectForm.medList}">
                    <c:forEach var="med" items="${messageSelectForm.medList}">
                        <HR/>
                        <p>${med.medicalOrganizationCd}<br/>${med.medicalOrganizationName}</p>
                    </c:forEach>
            </c:if>
            <br/>
            <HR/>
            <p style="float: right;">受信日 : ${messageSelectForm.messageDetailList.createDateTime}</p><br/>
            <table class="green-header">
                <tr>
                    <th class="r100 center">タイトル</th>
                    <td class="r300 center">${messageSelectForm.messageDetailList.subject}</td>
                </tr>	
                <tr>
                    <th class="r100 center" colspan="2">内容</th>
                </tr>	
                <tr>
                    <td class="r380 center" colspan="2">
                    ${messageSelectForm.messageDetailList.bodyText}
                    </td>
                </tr>
            </table>
            </br>
            <a href="javascript:messageClose();" class="button button-blue"><span>閉じる</span></a>
            <c:if test="${messageSelectForm.messageDetailList.senderStatus != 1}">
                <a href="javascript:submitForm('replay');" class="button button-orange"><span>返信</span></a>
            </c:if>
        </div>
    </div>
</body>
</form:form>
<form  id="MessageRegist" target="_self" action="./messageSelect" method="post">
	<input id="command" name="command" type="hidden" value="regist"/>
	<input id="cid" name="cid" type="hidden" value=""/>
</form>
</html>