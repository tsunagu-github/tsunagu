<%-- 
    Document   : userManageEdit
    Created on : 2016/09/20, 15:00:35
    Author     : kis.o-note-003
--%>
<body>
<form:form modelAttribute="userManageEditForm" name="userManageEditForm" action="${action}">

<script type="text/javascript">
function initPage() {
    displayPassword();
}
</script>

<form:hidden path="command"/>
<form:hidden path="formCd"/>
<form:hidden path="formName"/>
<form:hidden path="param1"/>
<form:hidden path="editFlg" id="editFlg" name="editFlg" value="${userManageEditForm.editFlg}"/>

    <div class="headinglv01"><h1>ユーザアカウント登録画面<span>アカウント情報の入力を行います。</span></h1></div>
    

    <div style="margin:0.5em auto 0;width:650px;">
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
        <table class="fmt05">
        <colgroup><col width="35%"/><col width="65%"/></colgroup>
            <tr>
		<th style="text-align: left;"><span class="esse">ログインID</span></th>
		<td class="r300" >
                    <form:input path="loginId" id="loginId" name="loginId" style="ime-mode:disabled" type="text" value="${userManageEditForm.loginId}" size="30" maxlength="20"/>
		</td>
            </tr>
            <tr>
            	<th style="text-align: left;"><span class="esse">氏名</span></th>
            	<td class="r300" >
                    <form:input path="userName" id="userName" name="userName" style="ime-mode: active" type="text" value="${userManageEditForm.userName}" size="30" maxlength="20" readOnly="${readOnly}"/>
            	</td>
            </tr>
            <tr >
            	<th style="text-align: left;"><span class="esse">所属保険者</span></th>
            	<td class="r300" id ="authority1_facility">
                    ${userManageEditForm.insurerName}
           	</td>
            </tr>
            <tr>
                <th class="r210" style="text-align: left;"><span class="esse">アカウント種別</span></th>
                <td class="r440" >
                    <form:radiobutton path="accountType" value="1" />
                    保険者&nbsp&nbsp&nbsp;
                    <form:radiobutton path="accountType" value="2" />
                    疾病管理事業者&nbsp;
                </td>
            </tr>
            <tr>
                <th class="r210" style="text-align: left;">追加権限</th>
                <td class="r440" >
                    <form:checkbox path="oneTimePassAuth" value="1"/>
                    参照用パスワード発行権限
                </td>
            </tr>
           <c:if test="${userManageEditForm.editFlg == false}">
                <tr>
                    <th style="text-align: left;"><span class="esse">初期パスワード</span></th>
                    <td class="r300" >
                        <form:input path="password" id="password" name="password" type="password" value="" size="35" maxlength="20" readOnly="${readOnly}"/>
                    </td>
                </tr>
                <tr>
                    <th style="text-align: left;"><span class="esse">初期パスワード(確認)</span></th>
                    <td class="r300" >
                        <form:input path="cPassword" id="cPassword" name="cPassword" type="password" value="" size="35" maxlength="20" readOnly="${readOnly}"/>
                    </td>
               </tr>
            </c:if>
            <c:if test="${userManageEditForm.editFlg == true}">
                <tr>
                    <th style="text-align: left;">
                        <span class="esse">パスワード変更の有無</span>
                    </th>
                    <td class="r300" >
                        <form:checkbox path="changeflg" id="changeflg" name="changeflg" onclick="displayPassword()"/>変更する
                    </td>
                </tr>
                <tr id="newPass" style="display:none">
                    <th style="text-align: left;"><span class="esse">初期パスワード</span></th>
                    <td class="r300" >
                        <form:input path="password" id="password" name="password" type="password" value="" size="35" maxlength="20" />
                    </td>
                </tr>
                <tr id="c_newPass" style="display:none">
                    <th style="text-align: left;"><span class="esse">初期パスワード(確認)</span></th>
                    <td class="r300" >
                        <form:input path="cPassword" id="cPassword" name="cPassword" type="password" value="" size="35" maxlength="20" />
                    </td>
                </tr>
            </c:if>
            </tr>
            <tr>
                <th style="text-align: left;"><span class="esse">ステータス</span></th>
                <td class="r300" >
                    <form:radiobutton path="invalidFlg" value="false" />
                    有効&nbsp;&nbsp;
                    <form:radiobutton path="invalidFlg" value="true" />
                    無効&nbsp;
                </td>
            </tr>
    
        </table>
            <a href="javascript:submitForm('return');" ><span class="gray_left" style="margin:0.5em 0 0;">
            <span style="vertical-align: -1em;">　戻る</span>
            </a>
            <a href="javascript:submitForm('confirm');" class="button button-blue" style="margin:0.5em 0 0;float:right;"><span>確認</span></a>
            <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>

    </div>
        
    
</form:form>
 </body>
   
<SCRIPT type="text/javascript">
initPage();

function displayPassword(){
    var target = document.getElementById("changeflg");

    if(target != null){
        var flg = target.checked;

        if(flg){
            document.getElementById("newPass").style.display = "";
            document.getElementById("c_newPass").style.display = "";
        }else{
            document.getElementById("newPass").style.display = "none";
            document.getElementById("c_newPass").style.display = "none";
        }
    }
}

</SCRIPT>
