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
            
            function editPassword() {
                var chk = document.getElementById("passEdit");
                if (chk.checked == true) {
                    document.getElementById("password").disabled = "true";
                    document.getElementById("initPassword").disabled = "true";
                } else {
                    document.getElementById("password").disabled = "";
                    document.getElementById("initPassword").disabled = "";
                }
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
<form:form modelAttribute="medicalCreateEditForm" name ="medicalCreateEditForm" action="${action}" enctype="multipart/form-data">
 <form:hidden path="editFlg" id="editFlg" name="editFlg" value="${medicalCreateEditForm.editFlg}"/>
   ﻿<input id="command" name="command" type="hidden" value=""/>
    <input id="formCd" name="formCd" type="hidden" value=""/>
    <input id="formName" name="formName" type="hidden" value=""/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <div class="headinglv01"><h1>医療機関登録画面<span>医療機関情報の入力を行います。</span></h1></div>

    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <div  style="margin:0.5em auto 0;width:650px;">
    <table class="fmt05" >
        <colgroup><col width="35%"/><col width="65%"/></colgroup>
        <tbody>
            <tr>
		<th style="text-align: left;">
                    <span class="esse">医療機関CD</span>
                </th>
                <td class="r300" >
                    <c:if test="${medicalCreateEditForm.editFlg == false}">
                        <form:input path="medicalCd" cssClass="medicalCd" value="${medicalCd}" size="10" maxlength="10"/>
                    </c:if>
                    <c:if test="${medicalCreateEditForm.editFlg == true}">
                        ${medicalCreateEditForm.medicalCd}
                    </c:if>
                </td>
            </tr>
            <tr>
		<th style="text-align: left;">
                    <span class="esse">医療機関名称</span>
                </th>
                <td class="r300" >
                    <form:input path="medicalName" cssClass="medicalName" value="${medicalName}" size="30" maxlength="50"/>
                    
                </td>
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">郵便番号</span>
                    <span style="font-size: 8pt;">※ハイフン(-)なし</span>
                </th>
                <td class="r300" >
                    <form:input path="zipCode" cssClass="zipCode" value="${zipCode}" size="30" maxlength="7" onKeyup="this.value=this.value.replace(/[^0-9]+/,'')" readOnly="${readOnly}"/>
                </td>
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">所在地</span>
                </th>
                <td class="r300" >
                    <form:textarea path="address" cssClass="address" value="${address}" cols="50" rows="3" maxlength="255" />
                </td>
            </tr>
            <tr>
                <th style="text-align: left;">
                    <span class="esse">電話番号</span>
                </th>
                <td class="r300" >
                    <form:input path="telNo" cssClass="telNo" value="${telNo}" size="30" maxlength="15"/>
                </td>
            </tr>
            <c:if test="${medicalCreateEditForm.editFlg == false}">
                <tr id ="pass_display">
                    <th style="text-align: left;">
                        <span class="esse">初期パスワード</span>
                    </th>
                    <td class="r300" >
                        <form:password path="password" cssClass="password" value="${medicalCreateEditForm.password}" size="35" maxlength="10"/>
                    </td>
                </tr>
                <tr id ="cpass_display">
                    <th style="text-align: left;">
                        <span class="esse">初期パスワード(確認)</span>
                    </th>
                    <td class="r300" >
                        <form:password path="initPassword" id="initPassword" name="initPassword" cssClass="initPassword" value="${medicalCreateEditForm.initPassword}" size="35" maxlength="10" />
                    </td>
                </tr>
            </c:if>
            <c:if test="${medicalCreateEditForm.editFlg == true}">
                <tr >
                    <th style="text-align: left;">
                        <span class="esse">パスワード変更の有無</span>
                    </th>
                    <td class="r300" >
                        <form:checkbox path="changeFlg" id="changeFlg" name="changeFlg" value="${medicalCreateEditForm.changeFlg}" onClick="displayPassword()"/>変更する
                    </td>
                </tr>
                <tr id ="pass_display" style="display:none">
                    <th style="text-align: left;">
                        <span class="esse">初期パスワード</span>
                    </th>
                    <td class="r300" >
                        <form:password path="password" cssClass="password" value="${medicalCreateEditForm.password}" size="35" maxlength="10" />
                    </td>
                </tr>
                <tr id ="cpass_display" style="display:none">
                    <th style="text-align: left;">
                        <span class="esse">初期パスワード(確認)</span>
                    </th>
                    <td class="r300" >
                        <form:password path="initPassword" id="initPassword" name="initPassword" cssClass="initPassword" value="${medicalCreateEditForm.initPassword}" size="35" maxlength="10" />
                    </td>
                </tr>
            </c:if>
            
        </tbody>
        </table>
        <a href="javascript:submitForm('return');" ><span class="gray_left" style="margin:0.5em 0 0;">
        <span style="vertical-align: -1em;">　戻る</span>
        </a>

        <a href="javascript:submitSearch('confirm');" class="button button-blue" style="margin:1em 0 0;float:right;" ><span>確認</span></a>
                

    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
    </div>
</form:form>
</body>
<script>
    displayPassword();
    setFocus();

</script>
