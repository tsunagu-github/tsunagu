<form:form modelAttribute="targetingPatientListForm" name ="targetingPatientListForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <input id="param2" name="param2" type="hidden" value=""/>
    
<div class="search-section">
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
     
	        <br/>
	        <td>
	            <a href="javascript:submitForm('search')" class="button button-blue" style="float:right;"><span>検索</span></a>
	        </td>
	        </tr>
        </table>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>

<div id="patientList" style="padding: 2em;display:'';">
    <tr>
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    </tr>
    
    <c:if test="${targetingPatientListForm.patientFlg}">
	    <table class="fmt03 margin-auto" style="width:850px;margin-top: 10px;">
	        <thead>
	            <tr>
	                <th>PHR-ID</th>
	                <th>氏名(漢字)</th>
	                <th>氏名（カナ）</th>
	                <th>生年月日</th>
	                <th>性別</th>
	                <th></th>
	            </tr>
	        </thead>
	        <tbody>
                <c:forEach var="patient" items="${targetingPatientListForm.patientInfo}">
		            <tr>
		                <td class="r100 center" >${patient.PHR_ID}</td>
		                <td class="r150 center">${patient.familyName}  ${patient.givenName}</td>
		                <td class="r150 center">${patient.familyKana}  ${patient.givenKana}</td>
		                <td class="r100 center">${patient.birthStr}</td>
                        <td class="r80 center">
                            <c:if test="${patient.sexCd == 'M'}">男性</c:if>
                            <c:if test="${patient.sexCd == 'F'}">女性</c:if>
                        </td>
		                <td class="r80">
                            <div class="btn4 margin-auto">
                                 <a href="javascript:openPopUpPatientWindow('${insurerNo}','${patient.PHR_ID}')">選択</a>
	                        </div>
		                </td>
		            </tr>
                </c:forEach>
	        </tbody>
	    </table>
    </c:if>
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>


<SCRIPT type="text/javascript">
    function openPopUpPatientWindow(insurerNo, phrId) {
    	// 閲覧同意の確認
        var strData = {
			value1: insurerNo,
			value2: phrId
        };
		var postData = JSON.stringify(strData);
		var postUrl = "checkAgreesToShare";
		
		$.ajax({
            url: postUrl,
            type: "POST",
            dataType: "json",
            data: postData,
            contentType: "application/JSON",
            success: function(json){
                if(json){
                         var windowName = 'PatientPopupView';
                         var wind = window.open("./targetingPatientInfoDummy.html", windowName,"width=1000,height=700");
                         var form = document.getElementById("targetingPatientForm");
                         form.target = windowName;
                         form.param1.value = phrId;
                         form.submit();
                       //  wind.focus();
                } else {
                	jAlert("選択された患者は閲覧同意がありません。","");
                }
            },
            error: function() {
                jAlert("患者情報取得に失敗しました。","");
            }
        });
    }
</SCRIPT>
<form id="targetingPatientForm" target="_self" action="./targetingPatient" method="post">
    <input id="command" name="command" type="hidden" value="select"/>
    <input id="param1" name="param1" type="hidden" value=""/>
</form>