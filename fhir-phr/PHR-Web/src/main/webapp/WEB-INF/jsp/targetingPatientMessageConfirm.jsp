<html>
<head>
<META http-equiv="Content-Language" content="ja"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />

<link href="css/import.css" rel="stylesheet" type="text/css" />
<LINK href="css/jquery.qtip.css" rel="stylesheet" type="text/css"/>
<LINK href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"/> 
<LINK href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css"/> 
<link href="js/libs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script src="js/common.js" type="text/javascript"></script>
<SCRIPT src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT> 
<SCRIPT src="js/jquery/jquery-ui.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.ui.datepicker-ja.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.colorbox-min.js" type="text/javascript"></SCRIPT>
<script src="js/libs/jquery.alerts.js" type="text/javascript" charset="UTF-8"></script>
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
    
    function hidePopup() {
      target = document.getElementById("selecthospital");
      target.style.display = "none";
    }
     
    function submitClose(command){
        var from = document.forms[0];
        if (from.command != null) {
                from.command.value = command;
                from.submit();
        }
        jAlert('メッセージを送信しました。','送信完了',function(){
            window.close();
        });       
    }
    function messageClose(){
        window.opener = "myself";
        window.close();
    }
    
    function complite(){
        var flg = ${targetingPatientMessageConfirmForm.status};
        var conf = document.getElementById("returnwindow");
        var comp = document.getElementById("complite");
        if(flg){
            conf.style.display = "none";
            comp.style.display =  "";
        }
    }
    function initPage() {
        complite();
    }
 </script>

<form:form modelAttribute="targetingPatientMessageConfirmForm" name="targetingPatientMessageConfirmForm" action="${action}">
<form:hidden path="command"/>
<form:hidden path="formCd"/>
<form:hidden path="formName"/>

<body>
<div class="message-list" style="height:590px;">
    <div class="scroll" style="width:500px;height:600px;">

        <!-- メッセージ返信画面 -->
	<div id="returnwindow">
            <p>メッセージ</p>
            <HR/>
                <p>${messageDto.medicalname}<br/></p>
            <HR/>
            <table class="green-header">
                <tr>
                    <th class="r100 center">宛先（PHR ID）</th>
                    <td class="r300 center">${messageDto.phrid}</td>
                </tr>	
		<tr>
                    <th class="r100 center">氏名</th>
                    <td class="r300 center">${messageDto.name}</td>
		</tr>	
            </table>
            <table class="green-header">
                <tr>
                    <c:if test = "${messageDto.typeflg}">
                    <th class="r100 center">宛先（医療機関）</th>
                    <td class="r300 center">
                        <c:if test="${not empty messageDto.medicalLsit}">
                            <c:forEach varStatus="status" var="medical" items="${messageDto.medicalLsit}">
                                ${medical.medicalOrganizationName}<br/>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty messageDto.medicalLsit}">
                            医療機関に送付しない
                        </c:if>
                     </td>
                    </c:if>
                    <c:if test = "${!messageDto.typeflg}">
                    <th></th>
                    <td class="r300 center">
                    <c:if test = "${!messageDto.insureFlg}">
                        保険者に送らない
                    </c:if>
                    <c:if test="${messageDto.insureFlg}">
                        保険者に送る
                    </c:if>
                    </td>
                    </c:if>
                </tr>
                <tr>
                    <th class="r100 center">タイトル</th>
                    <td class="r300 center">
                        ${messageDto.subject}
                    </td>
                </tr>
            </table>
            <HR/>
            <table class="green-header" >
                <tr>
                    <th class="r80 center">内容</th>
                </tr>
                <tr>
                    <td class="r380 center" colspan="2">
                        ${messageDto.bodytext}
                    </td>
                </tr>
            </table>
            <a href="javascript:submitForm('return');" class="button button-blue" name="close" id="close" style="display: '';"><span>戻る</span></a>
            <a href="javascript:submitForm('entry');" class="button button-orange" name="entry" id="entry" ><span>送信</span></a>
        </div>
        <div id="complite" style="display:none">
            </br>
            </br>
            送信しました。
            </br>
            </br>
            <a href="javascript:messageClose();" class="button button-blue" name="close" id="close" style="display: '';"><span>閉じる</span></a>
        </div>
    </div>
</div>
</body>
</form:form>
 <script>
     initPage();
 </script>
</html>