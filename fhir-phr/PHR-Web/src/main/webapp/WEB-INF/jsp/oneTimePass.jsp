<%-- 
    Document   : oneTimePass
    Created on : 2018/02/08,
    Author     : KISO-NOTE-027
--%>

<script type="text/javascript">
    function initPage() {

    }
function confirm(command, param1) {
    jConfirm('ID:' + param1 + 'のワンタイムパスワードを発行します。よろしいですか？', '確認', function(r) {
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

<form:form modelAttribute="oneTimePassForm" name ="oneTimePassForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>

<div class="search-section">
    <c:if test="${oneTimePassForm.auth==false}">
        <p cssStyle="color:red; font-size:small">
          ワンタイムパスワード発行権限が確認できませんでした。            
        </p>
    </c:if>   
    <c:if test="${oneTimePassForm.auth==true}">
	<table>
	 <tr>
	 <td valign="top">
            <table class="medicine-body" style="width:600px;float:left;">
            <tbody>
                    <tr>
                            <th>PHR-ID</th>
                            <td>
                                    <form:input path="patientId" id="patientId" name="patientId" style="ime-mode:disabled;width:150px;" type="text"/>
                            </td>
                    </tr>
                    <tr>
                            <th>漢字氏名_姓</th>
                            <td>
                                    <form:input path="familyName" id="familyName" name="familyName" style="width:150px;" type="text"/>
                            </td>
                            <th>漢字氏名_名</th>
                            <td>
                                    <form:input path="givenName" id="givenName" name="givenName" style="width:150px;" type="text"/>
                            </td>
                    </tr>
                    <tr>
                            <th>カナ氏名_姓</th>
                            <td>
                                    <form:input path="familyKana" id="familyKana" name="familyKana" style="width:150px;" type="text"/>
                            </td>
                            <th>カナ氏名_名</th>
                            <td>
                                    <form:input path="givenKana" id="givenKana" name="givenKana" style="width:150px;" type="text"/>
                            </td>
                    </tr>                    
            </tbody>
            </table>
	 </td>
         <td>
            <a href="javascript:submitForm('select')" class="button button-blue" style="float:right;"><span>患者確認</span></a>
         </td>
         </tr>
        </table>
    </c:if>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>

<div id="patientInfo" style="padding: 2em;display:'';">
    
    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    
    <c:if test="${oneTimePassForm.patientFlg}">
	<table class="fmt03 margin-auto" style="width: 850px;">
            <thead>
		<tr>
                    <th>PHR-ID</th>
                    <th>氏名(漢字)</th>
                    <th>氏名(カナ)</th>
                    <th>生年月日</th>
                    <th>性別</th>
                    <th></th>
		</tr>
            </thead>
            <tbody>
                <c:forEach var="patient" items="${oneTimePassForm.patientInfo}">
			<tr>
                            <td class="r100 center" >${patient.PHR_ID}</td>
                            <td class="r150 center">${patient.familyName}  ${patient.givenName} </td>
                            <td class="r150 center">${patient.familyKana}  ${patient.givenKana}</td>
                            <td class="r100 center">${patient.birthStr}</td>
                            <td class="r80 center">
                                <c:if test="${patient.sexCd == 'M'}">男性</c:if>
                                <c:if test="${patient.sexCd == 'F'}">女性</c:if>
                            </td>
                            <td class="r80">
                                <a href="javascript:confirm('getpassword', '${patient.PHR_ID}')" class="button button-blue">
                                <span> 発行</span></a>
                            </td>
			</tr>
                    </c:forEach>
            </tbody>
	</table>
    </c:if>
    <c:if test="${oneTimePassForm.passwordFlg}">
	<table class="fmt03 margin-auto" style="width: 850px;">
            <thead>
		<tr>
                    <th>PHR-ID</th>
                    <th>ワンタイムパスワード</th>
                    <th>有効期限</th>
                </tr>
            </thead>
            <tbody>
		<tr>
                    <td class="r100 center">${oneTimePassForm.patientId}</td>
                    <td class="r150 center">${oneTimePassForm.oneTimePass}</td>
                    <td class="r150 center">${oneTimePassForm.expirationDate}</td>
		</tr>
            </tbody>
	</table>
	
	</c:if>	
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>

<SCRIPT type="text/javascript">
	initPage();
</SCRIPT>
