<%-- 
    Document   : targetingPatient
    Created on : 2016/08/26, 15:28:00
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<SCRIPT type="text/javascript">
    function oneTimePass(){
        jPrompt('ワンタイムパスワードを入力してください', '', 'ワンタイムパスワード入力', function(r) {
            var obDoc = document;
            var errMSG = obDoc.getElementById('errMSG');
            var canMSG = obDoc.getElementById('canMSG');
            var compMSG = obDoc.getElementById('compMSG');
            if(r==='' || r===null){
                if(r===''){
                    //エラーメッセージ
                    errMSG.style.display = '';
                    canMSG.style.display = 'none';
                    compMSG.style.display = 'none';
                }else{
                    //キャンセルメッセージ
                    errMSG.style.display = 'none';
                    canMSG.style.display = '';
                    compMSG.style.display = 'none';
                }
            }else{
                //正常時メッセージ
                errMSG.style.display = 'none';
                canMSG.style.display = 'none';
                compMSG.style.display = '';
                openPopUpPatientWindow(r);
            }
        });
    }
    
    function openPopUpPatientWindow(r) {
        var windowName = 'PatientPopupView';
        var wind = window.open("./targetingPatientInfoDummy.html", windowName,"width=1000,height=700");
        var form = document.getElementById("targetPopUpForm");
        form.target = windowName;
        var onePW = document.getElementById("_onePW");
        onePW.value= r;
        form.submit();
        wind.focus();
    }
</SCRIPT>
<form:form modelAttribute="targetingPatientForm" name ="targetingPatientForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <div id="errMSG" style="padding: 1em;display:none;">
        <span style="color:red;">ワンタイムパスワードは入力必須です。<br><fmt:message key="passwordChange.passwordChangeConfirmMessage2"/></span>
    </div>
    <div id="canMSG" style="padding: 1em;display:none;">
        <span>ワンタイムパスワードの入力がキャンセルされました。<br><fmt:message key="passwordChange.passwordChangeConfirmMessage2"/></span>
    </div>
    <div id="compMSG" style="padding: 1em;display:none;">
        <span><fmt:message key="passwordChange.passwordChangeConfirmMessage2"/></span>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>
<SCRIPT type="text/javascript">
    oneTimePass();
</SCRIPT>
<form id="targetPopUpForm" target="_self" action="./targetingPatient" method="post">
    <input id="command" name="command" type="hidden" value="searchOneTimePW"/>
    <input type="hidden" id="_onePW" name="_onePW" value="">
    <input type="hidden" id="_insurerCd" name="insurerCd" value="">
</form>
