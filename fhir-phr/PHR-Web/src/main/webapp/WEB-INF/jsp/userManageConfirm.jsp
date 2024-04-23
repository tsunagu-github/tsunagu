<%-- 
    Document   : userManageEdit
    Created on : 2016/09/20, 15:00:35
    Author     : kis.o-note-003
--%>
<body>
<form:form modelAttribute="userManageConfirmForm" name="userManageConfirmForm" action="${action}">

<script type="text/javascript">
function initPage() {
}
</script>

<form:hidden path="command"/>
<form:hidden path="formCd"/>
<form:hidden path="formName"/>
<form:hidden path="param1"/>

    <div class="headinglv01"><h1>ユーザアカウント登録確認画面<span>登録情報の確認を行い、[登録]を押すとアカウント情報が登録されます。パスワードは表示されません。</span></h1></div>
    

    <div style="margin:0.5em auto 0;width:650px;">
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
        <c:if test="${userManageConfirmForm.successed == true }">
            <div id ="complete" style="	width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Blue;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
                <span>ユーザアカウントの登録が完了しました。</span>
            </div>
        </c:if>
 　     <c:if test="${userManageConfirmForm.failed == true }">
            <div id ="complete" style="	width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Red;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
                <span>ユーザアカウントの登録に失敗しました。システム管理者に連絡して下さい。</span>
            </div>
   　    </c:if>

        <table class="fmt05">
        <colgroup><col width="35%"/><col width="65%"/></colgroup>
            <tr>
		<th style="text-align: left;"><span class="esse">ログインID</span></th>
		<td class="r300" >
                    ${AccountEntity.loginId}
		</td>
            </tr>
            <tr>
            	<th style="text-align: left;"><span class="esse">氏名</span></th>
            	<td class="r300" >
                    ${AccountEntity.name}
            	</td>
            </tr>
            <tr >
            	<th style="text-align: left;"><span class="esse">所属保険者</span></th>
            	<td class="r300" id ="authority1_facility">
                    ${AccountEntity.insurerName}
           	</td>
            </tr>
            <tr>
                <th class="r210" style="text-align: left;"><span class="esse">アカウント種別</span></th>
                <td class="r440" >
                    <c:if test="${AccountEntity.accoutTypeCd == 1}">
                        保険者
                    </c:if>
                    <c:if test="${AccountEntity.accoutTypeCd == 2}">
                        疾病管理事業者
                    </c:if>
                </td>
            </tr>
            <tr>
                <th class="r210" style="text-align: left;">追加権限</th>
                <td class="r440" >
                    <c:if test="${AccountEntity.oneTimePassAuth == true}">
                        参照用パスワード発行権限
                    </c:if>
                    <c:if test="${AccountEntity.oneTimePassAuth == false}">
                        無し
                    </c:if>
                </td>
            </tr>            
            <tr>
                <th style="text-align: left;"><span class="esse">ステータス</span></th>
                <td class="r300" >
                    <c:if test="${AccountEntity.invalid == false}">
                        有効
                    </c:if>
                    <c:if test="${AccountEntity.invalid == true}">
                        無効
                    </c:if>
                </td>
            </tr>
    
        </table>
         <c:if test="${userManageConfirmForm.successed == false }">
           <a href="javascript:submitForm('return');" ><span class="gray_left" style="margin:0.5em 0 0;">
            <span style="vertical-align: -1em;">　戻る</span>
            </a>
            <a href="javascript:submitForm('regist');" class="button button-blue" style="margin:0.5em 0 0;float:right;"><span>登録</span></a>
        </c:if>
         <c:if test="${userManageConfirmForm.successed == true }">
            <a href="javascript:submitForm('returnlist');" ><span class="gray_left" style="margin:0.5em 0 0;">
                <span style="vertical-align: -1em;">　戻る</span>
            </a>
        </c:if>
            <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>

    </div>
        
    
</form:form>
 </body>
   
<SCRIPT type="text/javascript">
initPage();

function displayPassword(){
    var flg = document.getElementById("changeflg").checked;
    
    if(flg){
        document.getElementById("newPass").style.display = "";
        document.getElementById("c_newPass").style.display = "";
    }else{
        document.getElementById("newPass").style.display = "none";
        document.getElementById("c_newPass").style.display = "none";
    }
}

</SCRIPT>
