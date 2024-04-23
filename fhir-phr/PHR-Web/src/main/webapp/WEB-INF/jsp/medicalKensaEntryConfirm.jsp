<%-- 
    Document   : medicalKensaEntryConfirm
    Created on : 2016/09/26, 19:16:00
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
    function initPage() {
	selectForm();
    }
    
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
    
    function selectForm(){
    var onemonth = document.getElementById("1month");
    onemonth.style.display = "none";
    var threemonth = document.getElementById("3month");
    threemonth.style.display = "none";
    var years = document.getElementById("years");
    years.style.display = "none";
    var first = document.getElementById("first");
    first.style.display = "none";

    var selected = document.getElementById("selectPeriod").value;
    if(selected=="0"){
            first.style.display = "";
    }else if(selected=="1"){
            onemonth.style.display = "";
    }else if(selected=="3"){
            threemonth.style.display = "";
    }else if(selected=="12"){
            years.style.display = "";
    }

}
    
</script>

<form:form modelAttribute="medicalKensaEntryConfirmForm" name ="medicalKensaEntryConfirmForm" action="${action}">
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
                        <td>${medicalKensaEntryConfirmForm.patientList.patientId}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>氏名（漢字）</th>
        		<td>${medicalKensaEntryConfirmForm.patientList.name}</td>
        	</tr>
        	<tr>
        		<th>氏名（カナ）</th>
        		<td>${medicalKensaEntryConfirmForm.patientList.kana}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>生年月日</th>
        		<td>${medicalKensaEntryConfirmForm.patientList.birth}</td>
        	</tr>
        	<tr>
        		<th>性別</th>
        		<td>${medicalKensaEntryConfirmForm.patientList.sex}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
</div>

<form:errors path="*" cssStyle="color:red; font-size:small"/>
                
<div class="selectArea">
    以下の内容を登録します。
	<table>
	 <tr>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査日</th>
        		<td>
	        		${medicalKensaEntryConfirmForm.startDate}
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
<!--	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査登録期間</th>
        		<td>
                            <c:if test="${medicalKensaEntryConfirmForm.selectPeriod==1}">1ヶ月</c:if>
                            <c:if test="${medicalKensaEntryConfirmForm.selectPeriod==3}">3ヶ月</c:if>
                            <c:if test="${medicalKensaEntryConfirmForm.selectPeriod==12}">年度ごと</c:if>
                            <c:if test="${medicalKensaEntryConfirmForm.selectPeriod==0}">初回・変更時</c:if>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td> -->
	 </tr>
	</table>
         <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>
<div id="ItemArea" style="padding: 2em;">
	
	<table class="fmt03 margin-auto" id="input" style="width:850px;">
		<thead>
			<tr>
		    	<th>項目</th>
		    	<th>値</th>
		        <th>基準値</th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="input" items="${medicalKensaEntryConfirmForm.inputList}">
			<tr>
				<td class="r150 center" >${input['name']}</td>
				<c:if test="${empty input.enumList}">
				<td class="r250 center">${input['value']}   ${input['unitvalue']}</td>
				</c:if>
				<c:if test="${not empty input.enumList}">
				<td class="r250 center">
				<c:forEach var="enumList" items="${input.enumList}">
				    <label><input type="radio" name='${input.id}' value='${enumList["enumId"]}' disabled='' <c:if test="${enumList['enumId']==input['value']}">checked='checked'</c:if>>${enumList["enumName"]}</label>
				</c:forEach>
				</td>
				</c:if>
				<td class="r400 center">
					${input['min']}
					-
					${input['max']}
				</td>
			</tr>
                    </c:forEach>
		</tbody>
	</table>
	</br>
	<a href="javascript:submitForm('commit');" class="button button-blue" id="insert" style="float:right;"><span>登録</span></a>
        <a href="javascript:submitForm('return');" class="button button-blue" id="return" style="float:right;"><span>戻る</span></a>

</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>

<SCRIPT type="text/javascript">
    initPage();
</SCRIPT>