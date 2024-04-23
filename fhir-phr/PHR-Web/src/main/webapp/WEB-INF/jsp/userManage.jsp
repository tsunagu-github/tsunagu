<%-- 
    Document   : userManage
    Created on : 2016/09/20, 14:37:02
    Author     : kis.o-note-003
--%>

<script type="text/javascript">
function initPage() {

}
    
function userEdit(command, param1) {
    var form = document.forms[0];
    form.target="_self";
    if (form.command != null) {
	form.command.value = command;
    }
    if (form.param1 != null) {
        form.param1.value = param1;
    }
    form.submit();
}

function userDelete(command, param1) {
    jConfirm('削除したユーザ情報は戻すことができません。よろしいですか？', '削除確認', function(r) {
        if(r!==null && r===true){
            var form = document.forms[0];
            form.target="_self";
            if (form.command != null) {
                form.command.value = command;
            }
            if (form.param1 != null) {
                form.param1.value = param1;
            }
            form.submit();
        } else {
            return false;
        }
    });
}
</script>

<form:form modelAttribute="userManageForm" name="userManageForm" action="${action}">
<div style="text-align:left; margin:0em auto 0; width:900px; ">
<form id="userManage" target="_self" action="./userManage" method="post">
<input id="command" name="command" type="hidden" value=""/>
<input id="formCd" name="formCd" type="hidden" value=""/>
<input id="formName" name="formName" type="hidden" value=""/>
<input id="param1" name="param1" type="hidden" value=""/>
<div class="headinglv01"><h1>ユーザ管理画面<span>ユーザの一覧を表示します。</span></h1></div>

<div class="search-section" style="position: relative">
	<table>
	 <tr>
            <td>
               <table class="medicine-body" style="width:600px;float:left;">
                <colgroup><col width="20%"/><col width="80%"/></colgroup>
                   <tbody>
                           <tr>
                                   <th>ログインID</th>
                                   <td class="r300" >
                                       <form:input path="loginId" id="loginId" name="loginId" style="ime-mode:inactive;" type="text" value="${userManageForm.loginId}" size="55" maxlength="50"/>
                                   </td>
                           </tr>
                            <tr>
                                <th>氏名</th>
                                <td class="r300" ><form:input path="userName" id="userName" name="userName" style="ime-mode:active;" type="text" value="${userManageForm.userName}" size="35" maxlength="20"/></td>
                            </tr>
                            <tr>
                                <th>ステータス</th>
                                <td class="r300" >
                                    <form:checkbox path="validFlg" id="validFlg" name="validFlg" value="${userManageForm.validFlg}"/>
                                    <span>無効ユーザを表示&nbsp;</span>
                                </td>
                            </tr>
                   </tbody>
               </table>
            </td>
            <div style="position: absolute;bottom: 0;right: 5em" >
                <a href="javascript:submitForm('search');" class="button button-blue"><span>検索</span></a>
                <a href="javascript:submitForm('newCreate')"  class="button button-green"><span>新規作成</span></a>
            </div>
	 </tr>
	</table>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>

    <div class="result_box" id="userInfo">
        <tr>
            <form:errors path="*" cssStyle="color:red; font-size:small"/>
        </tr>
        <c:if test="${not empty userManageForm.userList}">
            <table class="fmt03 margin-auto" style="width:800px">
		<thead>
		    <tr>
                        <th>No.</th>
                        <th>氏名</th>
                        <th>ログインID</th>
                        <th>所属施設</th>
                        <th>有効</th>
                        <th>修正</th>
                        <th>削除</th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach varStatus="status" var="showlist" items="${userManageForm.userList}">
                            <tr>
                                <td class="r20 center" >${status.count}</td>
		                <td class="r180 center">${showlist.name}</td>
		                <td class="r100 center">${showlist.loginId}</td>
	                        <td class="r100 center">${showlist.insurerNo}</td>
		                <td class="r40 center">
                                    <c:if test="${showlist.invalid == true}">×</c:if>
                                    <c:if test="${showlist.invalid == false}">○</c:if>
                                </td>
		                <td class="r90"><div class="btn4 margin-auto"><a href="javascript:userEdit('edit','${showlist.loginId}');">修正</a></div></td>
		                <td class="r90"><div class="btn4r margin-auto"><a href="javascript:userDelete('delete','${showlist.accountId}');" >削除</a></div></td>
		            </tr>
                    </c:forEach>
		</tbody>
            </table>
        </c:if>
    </div>
</form>
</div>
</form:form>
<SCRIPT type="text/javascript">
	initPage();
</SCRIPT>

