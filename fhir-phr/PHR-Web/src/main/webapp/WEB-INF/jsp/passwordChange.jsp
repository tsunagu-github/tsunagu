<%-- 
    Document   : passwordChange
    Created on : 2016/08/24, 17:56:18
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<form:form modelAttribute="passwordChangeForm" name ="passwordChangeForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <div style="margin: 10px 0px 0px 0px;">
        <table class="fmt05 margin-auto" style="width:490px">
            <colgroup><col style="width:47%" /><col style="width:53%" /></colgroup>
            <tr>
                <form:errors path="*" cssStyle="color:red; font-size:small"/>
            </tr>                          
            <tr>
                <th><span ><fmt:message key="login.loginId"/></span></th>
                <td><form:hidden path="userId" /><c:out value="${passwordChangeForm.userId}"/></td>
            </tr>
            <tr>
                <th><span ><fmt:message key="passwordChange.currenntPassword"/></span></th>
                <td><input id="currenntPassword" name="currenntPassword" class="password" type="password" value="" size="35" maxlength="20"/> 
                </td>
            </tr>
            <tr>
                
                <th class="orange"><span><fmt:message key="passwordChange.newPassword"/></span></th>
                <td><input id="newPassword" name="newPassword" class="password" type="password" value="" size="35" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th class="orange"><span><fmt:message key="passwordChange.confirmPassword"/></span></th>
                <td><input id="confirmPassword" name="confirmPassword" class="password" type="password" value="" size="35" maxlength="20"/>
                </td>
            </tr>
        </table>
        <div class="margin-auto2" style="width:490px">
            <div class="bt_clear btn3"><a href="javascript:clearFormAll();"><fmt:message key="login.clearButton" /></a></div>
            <div class="bt_login btn3"><a href="javascript:submitForm('passwordChange');"><fmt:message key="passwordChange.passwordChangeButton"/></a></div>
            <div class="clear_message"></div><br /><br /><br />
            <div class="login_message"></div>
        </div>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>
