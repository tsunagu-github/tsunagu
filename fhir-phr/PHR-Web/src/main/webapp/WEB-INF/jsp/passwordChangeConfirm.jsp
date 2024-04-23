<%-- 
    Document   : passwordChangeConfirm
    Created on : 2016/08/30, 10:56:44
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<form:form modelAttribute="passwordChangeForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <div style="margin: 10px 0px 0px 0px;"></div>
    <div class="section">
        <div class="result_box">
            <div><form:errors path="*" cssStyle="color:red; font-size:small"/></div>
            <div class="messsage-box">
                <span>
                    <fmt:message key="passwordChange.passwordChangeConfirmMessage1" /><br/>
                    <fmt:message key="passwordChange.passwordChangeConfirmMessage2" />
                </span>
            </div>
        </div>
    </div>
</form:form>
