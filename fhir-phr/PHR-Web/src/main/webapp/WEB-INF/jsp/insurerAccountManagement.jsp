<%-- 
    Document   : insurerAccountManagement
    Created on : 2016/08/30, 15:16:19
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<form:form modelAttribute="insurerAccountManagementForm" name ="insurerAccountManagementForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <form:hidden path="index"/>
    <script type="text/javascript">
        function submitForm2(command, index) {
            var from = document.forms[0];
            if (from.command !== null) {
                from.command.value = command;
            }
            if (from.index !== null) {
                from.index.value = index;
            }
            from.submit();
        }
        
        function submitDelete(command, index) {
            jConfirm('削除したユーザは戻すことができません。よろしいですか？', '削除確認', function(r) {
                if(r!==null && r===true){
                    var from = document.forms[0];
                    if (from.command !== null) {
                        from.command.value = command;
                    }
                    if (from.index !== null) {
                        from.index.value = index;
                    }
                    from.submit();
                }
            });
//            if(confirm("削除したユーザは戻すことができません。よろしいですか？")===true){
//                var from = document.forms[0];
//                if (from.command !== null) {
//                        from.command.value = command;
//                }
//                if (from.index !== null) {
//                        from.index.value = index;
//                }
//                from.submit();
//            }
        }

        function init(){
//            if("${userAccountManagementForm.account}"==="1"){
//                authorityChange(0);
//            }else if("${userAccountManagementForm.account}"==="0"){
//                authorityChange(1);
//            }else{
//                authorityChange(2);
//            }
        }

        /* ページ読み込み完了時に、初期化を実行 */
        window.onload=init;
    </script>

    <div class="headinglv01"><h1>ユーザ管理画面<span>ユーザの一覧を表示します。</span></h1></div>

    <table class="fmt02" style="margin:2em auto 0;width:550px;float:left">
        <colgroup><col width="35%"/><col width="65%"/></colgroup> 
        <tbody>
            <tr><th><fmt:message key="login.loginId"/></th><td class="r300" ><form:input path="userIdStr" style="ime-mode:inactive;" size="35" maxlength="20"/></td></tr>
            <tr><th><fmt:message key="account.username"/></th><td class="r300" ><form:input path="userNameStr" style="ime-mode:active;" size="35" maxlength="20"/></td></tr>
            <!--<tr><th>利用可能期間</th><td class="r300" ><input id="showExpireUser1" name="showExpireUser" type="checkbox" value="true"/><input type="hidden" name="_showExpireUser" value="on"/><span>期限切れユーザを表示&nbsp;</span></td></tr>-->
            <tr><th><fmt:message key="account.invaliedname"/></th><td class="r300" ><form:checkbox path="showInvalidUser" value="1"/><span>無効ユーザを表示&nbsp;</span></td></tr>
        </tbody>
    </table>
    <table style="margin:8em auto 0;float:left">
	<tr>
            <td>
	    <a href= "javascript:submitForm('search');" class="button button-blue"><span><fmt:message key="button.searchname"/></span></a>
            </td>
            <td>
            <a href="javascript:submitForm('create');"  class="button button-green"><span><fmt:message key="button.newcreatename"/></span></a>
            </td>
	<tr>
    </table>
    <div class="section-line"></div>
    <c:if test="${not empty insurerAccountManagementForm.accountList}">
    <div class="result_box" id="userList">
        <table class="fmt03 margin-auto" style="width:800px">
            <thead>
                <tr>
                <th>No.</th>
                <th><fmt:message key="account.username"/></th>
                <th><fmt:message key="login.loginId"/></th>
                <th><fmt:message key="account.affiliatedfacilityname"/></th>
                <th><fmt:message key="account.statusname"/></th>
                <th><fmt:message key="button.modifyname"/></th>
                <th><fmt:message key="button.deletename"/></th>
                </tr>
            </thead>
            <tbody>
		<c:forEach varStatus="status" var="accountEntityList" items="${insurerAccountManagementForm.accountList}">
                <tr>
                    <td class="r20 center" >${status.count}</td>
                    <td class="r180 center">${accountEntityList.name}</td>
                    <td class="r100 center">${accountEntityList.loginId}</td>
                    <c:if test="${not empty accountEntityList.insurerNo}">
                        <td class="r100 center">${accountEntityList.insurerNo}</td>
                    </c:if>
                    <c:if test="${empty accountEntityList.insurerNo}">
                        <td class="r100 center">-</td>
                    </c:if>
                    <td class="r40 center"><c:if test="${not accountEntityList.invalid}">○</c:if></td>
                    <td class="r90"><div class="btn4 margin-auto">
                        <a href="javascript:submitForm2('edit', '${status.index}');">
                            <fmt:message key="button.modifyname" />
                        </a></div>
                    </td>
                    <td class="r90"><div class="btn4r margin-auto">
                        <a href="javascript:submitDelete('delete', '${status.index}');" >
                            <fmt:message key="button.deletename" />
                        </a></div>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>
