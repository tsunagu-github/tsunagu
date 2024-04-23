<%-- 
    Document   : medicalKensaEntry
    Created on : 2016/09/21, 9:16:00
    Author     : KISO-NOTE-005
--%>

<script>
    $(function(){
      $(".datepicker").datepicker({
            showOn: "button",
            buttonImage: "img/icon/calendar.png",
            buttonImageOnly: true
      });
      var today = getDate('/');
      $("#StandardDate").datepicker("setDate", today);
    });
    
</script>

<script type="text/javascript">
    
    function hidePopup() {
        target = document.getElementById("selectedPtInfo");
        target.style.display = "none";
    }
    function getDate(separator) {
        var d = new Date();
 
        return [
            d.getFullYear(),
            ('0' + (d.getMonth() + 1)).slice(-2),
            ('0' + d.getDate()).slice(-2)
        ].join(separator);
    }
    
    function openwin(command, param1) {
        var windowName = '_self';
        var from = document.getElementById("MedicalKensaPatientForm");
        from.target = windowName;
        from.param1.value = param1;
        from.command.value = command;
        from.submit();
    }
    
</script>

<form:form modelAttribute="medicalKensaEntryForm" name ="medicalKensaEntryForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>

<div class="search-section">
	<table>
	 <tr>
     <td valign="top">
		<table class="medicine-body" style="width:250px;float:left;">
		<tbody>
		        <tr>
		                <th>患者ID</th>
		                <td>
		                        <form:input path="patientId" id="patientId" name="patientId" style="ime-mode:disabled;width:150px;" type="text"/>
		                </td>
		        </tr>
		</tbody>
		</table>
	 </td>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>カナ氏名_姓</th>
        		<td>
	        		<form:input path="familyKana" id="familyKana" name="familyKana" style="width:150px;" type="text"/>
        		</td>
        	</tr>
        	<tr>
        		<th>カナ氏名_名</th>
        		<td>
	        		<form:input path="givenKana" id="givenKana" name="givenKana" style="width:150px;" type="text"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>生年月日</th>
        		<td>
	        		<form:input path="birthDate" name="birthDate" id="birthDate" type="text" maxlength="10" class="datepicker"/>
        		</td>
        	</tr>
        	<tr>
        		<th>性別</th>
        		<td>
                            <form:radiobutton path="sex" id="sex" name="sex" value="M"/><label for="sex">男性&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <form:radiobutton path="sex" id="sex" name="sex" value="F"/><label for="sex">女性</label>
			</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
	<br/>
    <table style="margin-right:10px;margin-left:auto;">
        <tr>
			<td>
			   <a href="javascript:submitForm('select')" class="button button-blue" style=""><span>患者ID指定</span></a>
			</td>
			<td>
			   <a href="javascript:submitForm('search')" class="button button-blue" style=""><span>検索</span></a>
	        </td>
        </tr>
    </table>
        
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>

<div id="patientList" style="padding: 2em;display:'';">
    <tr>
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    </tr>
    <c:if test="${not empty medicalKensaEntryForm.patientList}">
	<table class="fmt03 margin-auto" style="width:850px;">
		<thead>
			<tr>
		    	<th>患者ID</th>
		        <th>氏名(漢字)</th>
		        <th>氏名（カナ）</th>
		        <th>生年月日</th>
		        <th>性別</th>
		        <th></th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="patient" items="${medicalKensaEntryForm.patientList}">
			<tr>
                            <td class="r100 center" >${patient["patientId"]}</td>
                            <td class="r150 center">${patient["name"]}</td>
                            <td class="r150 center">${patient["kana"]}</td>
                            <td class="r100 center">${patient["birth"]}</td>
                            <td class="r80 center">${patient["sex"]}</td>
                            <td class="r80"><div class="btn4 margin-auto">
                                <a href="javascript:submitForm('patient', '${patient["patientId"]}')">
                                選択
                                </a></div>
                            </td>
			</tr>
                    </c:forEach>
		</tbody>
	</table>
        </c:if>
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>

<form id="medicalKensaPatientForm" target="_self" action="./medicalKensaPatient" method="post">
    <input id="command" name="command" type="hidden" value="patient"/>
    <input id="param1" name="param1" type="hidden" value=""/>
</form>
