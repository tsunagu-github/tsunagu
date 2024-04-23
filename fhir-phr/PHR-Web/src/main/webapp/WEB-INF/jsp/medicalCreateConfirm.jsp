<%-- 
    Document   : medicalCreateEdit
    Created on : 2016/09/26, 14:31:10
    Author     : kis.o-note-002
--%>
<!DOCTYPE html>
 
        <script type="text/javascript">
            // ページ読み込み時、医療機関CDにフォーカス
            function setFocus() {
                var from = document.forms[0];
                var ctrls = from.elements;
                var ss = "";
                for (var i = 0; i < ctrls.length; i++) {
                    if (ctrls[i].type == "text" || 
                        ctrls[i].type == "radio" || 
                        ctrls[i].type == "checkbox" || 
                        ctrls[i].type == "file" ||
                        ctrls[i].type == "select-one" ||
                        ctrls[i].type == "select-multiple" ||
                        ctrls[i].type == "password") {
                        ctrls[i].focus();
                        break;
                    }
                }
            }
            
            function submitSearch(command) {
                var from = document.forms[0];
                if (from.command != null) {
                        from.command.value = command;
                }
                from.submit();
                search();
            }
            function displayPassword(){
                var target = document.getElementById("changeFlg");
                if(target != null){
                    var flg = target.checked;

                    if(flg){
                        document.getElementById("pass_display").style.display = "";
                        document.getElementById("cpass_display").style.display = "";
                    }else{
                        document.getElementById("pass_display").style.display = "none";
                        document.getElementById("cpass_display").style.display = "none";
                    }
                }
            }
            
        </script>

</head>
<body>
<form:form modelAttribute="medicalCreateConfirmForm" name ="medicalCreateConfirmForm" action="${action}" enctype="multipart/form-data">
    ﻿<input id="command" name="command" type="hidden" value=""/>
    <input id="formCd" name="formCd" type="hidden" value=""/>
    <input id="formName" name="formName" type="hidden" value=""/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <div class="headinglv01"><h1>医療機関確認画面<span>登録情報の確認を行い、[登録]を押すと医療機関情報が登録されます。パスワードは表示されません。</span></h1></div>

    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <div  style="margin:0.5em auto 0;width:650px;">

        <c:if test="${medicalCreateConfirmForm.sucessed == true }">
            <div id ="complete" style="	width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Blue;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
                <span>医療機関の登録が完了しました。</span>
            </div>
        </c:if>
        <table class="fmt05" >
        <colgroup><col width="35%"/><col width="65%"/></colgroup>
        <tbody>
            <tr>
		<th style="text-align: left;">
                    <span class="esse">医療機関CD</span>
                </th>
                <td class="r300" >
                    ${MedicalOrganizationEntity.medicalOrganizationCd}
                </td>
            </tr>
            <tr>
		<th style="text-align: left;">
                    <span class="esse">医療機関名称</span>
                </th>
                <td class="r300" >
                    ${MedicalOrganizationEntity.medicalOrganizationName}
                </td>
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">郵便番号</span>
                </th>
                <td class="r300" >
                    ${MedicalOrganizationEntity.zipCode}
                </td>
                
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">所在地</span>
                </th>
                <td class="r300" >
                    ${MedicalOrganizationEntity.address}
                </td>
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">電話番号</span>
                </th>
                <td class="r300" >
                    ${MedicalOrganizationEntity.telNo}
                </td>
            </tr>
            
        </tbody>
        </table>
        <c:if test="${medicalCreateConfirmForm.sucessed == false }">
            <a href="javascript:submitForm('return');" ><span class="gray_left" style="margin:0.5em 0 0;">
            <span style="vertical-align: -1em;">　戻る</span>
            </a>

            <a href="javascript:submitSearch('insert');" class="button button-blue" style="margin:1em 0 0;float:right;" ><span>登録</span></a>
        </c:if>
        <c:if test="${medicalCreateConfirmForm.sucessed == true }">
            <a href="javascript:submitForm('returnlist');" ><span class="gray_left" style="margin:0.5em 0 0;">
            <span style="vertical-align: -1em;">　戻る</span>
            </a>

        </c:if>

    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
    </div>
</form:form>
</body>
<script>
    displayPassword();
    setFocus();

</script>
