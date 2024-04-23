<%-- 
    Document   : medicalKensaEntryInput
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
    
/*    function selectForm(){
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
    }*/

    
    function confirmwin(command) {
        jConfirm('現在表示されている項目のみ登録します。\n\他の検査登録期間の項目は破棄されますがよろしいですか？', '登録確認', function(r) {
            if(r!==null && r===true){
                var from = document.forms[0];
                from.target="_self";
                if (from.command != null) {
                    from.command.value = command;
                }
                from.submit();
            } else {
                return false;
            }
        });
    }
    
</script>

<form:form modelAttribute="medicalKensaEntryInputForm" name ="medicalKensaEntryInputForm" action="${action}">
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
                        <td>${medicalKensaEntryInputForm.patientList.patientId}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>氏名（漢字）</th>
        		<td>${medicalKensaEntryInputForm.patientList.name}</td>
        	</tr>            
        	<tr>
        		<th>氏名（カナ）</th>
        		<td>${medicalKensaEntryInputForm.patientList.kana}</td>
        	</tr>

        </tbody>
        </table>
	 </td>
	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>生年月日</th>
        		<td>${medicalKensaEntryInputForm.patientList.birth}</td>
        	</tr>
        	<tr>
        		<th>性別</th>
        		<td>${medicalKensaEntryInputForm.patientList.sex}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
</div>

<!--<div class="selectArea">
	<table>
	 <tr>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査日</th>
        		<td>
	        		<form:input path="startDate" id="startDate" name="startDate" style="width:150px;" type="text" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査登録期間</th>
        		<td>
					<form:select path="selectPeriod" id="selectPeriod" onchange="javascript:selectForm();">
                                            <c:if test="${not empty medicalKensaEntryInputForm.kensaListOne}">
						<form:option label="1ヶ月" value="1"/>
                                            </c:if>
                                            <c:if test="${not empty medicalKensaEntryInputForm.kensaListThree}">
						<form:option label="3ヶ月" value="3"/>
                                            </c:if>
                                            <c:if test="${not empty medicalKensaEntryInputForm.kensaListYears}">
						<form:option label="年度ごと" value="12"/>
                                            </c:if>
                                            <c:if test="${not empty medicalKensaEntryInputForm.kensaListFirst}">
						<form:option label="初回・変更時" value="0"/>
                                            </c:if>
                                        </form:select>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
         <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>-->
                                
<div id="ItemArea" style="padding: 2em;">

        <table class="fmt03" style="width:300px;float:left;;margin:10px">
        <tbody>
        	<tr>
        		<th>検査日</th>
        		<td>
	        		<form:input path="startDate" id="startDate" name="startDate" style="width:150px;" type="text" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
    
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    
	
	<table class="fmt03 margin-auto" style="width:850px;"><!--id="1month"-->
        <c:if test="${not empty medicalKensaEntryInputForm.kensaListOne}">
		<thead>
			<tr>
		    	<th>項目</th>
		    	<th>値</th>
		        <th>基準値</th>
		    </tr>
		</thead>
		<tbody>
            <c:forEach var="one" items="${medicalKensaEntryInputForm.kensaListOne}" varStatus="i">
                <c:if test="${one.diseaseManagementTargetFlg}">
				<tr>
					<td class="r150 center" >${one.observationDefinitionName}</td>
		                <form:hidden path="dtoOne[${i.index}].defid" value="${one.observationDefinitionId}"/>
		                <form:hidden path="dtoOne[${i.index}].defname" value="${one.observationDefinitionName}"/>
		                <form:hidden path="dtoOne[${i.index}].unitvalue" value="${one.unitValue}"/>
		                <td class="r230 center">
		                       <c:if test="${empty one.enumList}">
		                           <form:input path="dtoOne[${i.index}].value" type="text" value="${one.value}"/>${one.unitValue}
		                       </c:if>
		                       <c:if test="${not empty one.enumList}">
		                        <c:forEach var="enumOne" items="${one.enumList}">
		                            <c:if test="${enumOne['enumId'] == one.value}">
		                                <form:radiobutton path="dtoOne[${i.index}].value" label="${enumOne['enumName']}" value="${enumOne['enumId']}" checked='checked'/>
		                            </c:if>
		                            <%-- <c:if test="${(empty one.value) || (enumOne['enumValue'] != one.value)}"> --%>
		                            <c:if test="${enumOne['enumId'] != one.value}">
		                                <form:radiobutton path="dtoOne[${i.index}].value" label="${enumOne['enumName']}" value="${enumOne['enumId']}"/>
		                            </c:if>
		                        <form:hidden path="dtoOne[${i.index}].enumid" value="${enumOne['enumId']}"/>
		                        <form:hidden path="dtoOne[${i.index}].enumname" value="${enumOne['enumName']}"/>
		                        <form:hidden path="dtoOne[${i.index}].enumvalue" value="${enumOne['enumValue']}"/>
		                        </c:forEach>
		                       </c:if>
		                   </td>
						<td class="r200 center">
							<c:if test="${empty one.enumList}">
                                <form:input path="dtoOne[${i.index}].min" type="text" value="${one.minReferenceValue}" style="width:100px;"/>
                                -
                                <form:input path="dtoOne[${i.index}].max" type="text" value="${one.maxReferenceValue}" style="width:100px;"/>
							</c:if>
							<c:if test="${not empty one.enumList}"></c:if>
						</td>
				</tr>
				</c:if>
            </c:forEach>
		</tbody>
        </c:if>
	</table>
	
<!--	<table class="fmt03 margin-auto" id="3month" style="width:850px;">
            <c:if test="${not empty medicalKensaEntryInputForm.kensaListThree}">
		<thead>
			<tr>
		    	<th>項目</th>
		    	<th>値</th>
		        <th>基準値</th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="three" items="${medicalKensaEntryInputForm.kensaListThree}" varStatus="i">
			<tr>
				<td class="r200 center" >${three.observationDefinitionName}</td>
                                <form:hidden path="dtoThree[${i.index}].defid" value="${three.observationDefinitionId}"/>
                                <form:hidden path="dtoThree[${i.index}].defname" value="${three.observationDefinitionName}"/>
                                <form:hidden path="dtoThree[${i.index}].unitvalue" value="${three.unitValue}"/>
                                <td class="r250 center">
		        	<c:if test="${empty three.enumList}">
                                    <form:input path="dtoThree[${i.index}].value" type="text" value="${three.value}"/>${three.unitValue}
                                </c:if>
                                <c:if test="${not empty three.enumList}">
                                    <c:forEach var="enumThree" items="${three.enumList}">
                                    <form:radiobutton path="dtoThree[${i.index}].value" label='${enumThree["enumName"]}' value='${enumThree["enumValue"]}'/>
                                    <form:hidden path="dtoThree[${i.index}].enumid" value="${enumThree['enumName']}"/>
                                    <form:hidden path="dtoThree[${i.index}].enumname" value="${enumThree['enumName']}"/>
                                    <form:hidden path="dtoThree[${i.index}].enumvalue" value="${enumThree['enumValue']}"/>
                                    </c:forEach>
                                </c:if>
                                </td>
				<td class="r400 center">
                                <form:input path="dtoThree[${i.index}].min" type="text" value="${three.minReferenceValue}"/>
                                -
                                <form:input path="dtoThree[${i.index}].max" type="text" value="${three.maxReferenceValue}"/>
				</td>
			</tr>
                    </c:forEach>
		</tbody>
            </c:if>
	</table>
	
	<table class="fmt03 margin-auto" id="years" style="width:850px;">
            <c:if test="${not empty medicalKensaEntryInputForm.kensaListYears}">
		<thead>
			<tr>
		    	<th>項目</th>
		    	<th>値</th>
		        <th>基準値</th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="years" items="${medicalKensaEntryInputForm.kensaListYears}" varStatus="i">
			<tr>
				<td class="r200 center" >${years.observationDefinitionName}</td>
                                <form:hidden path="dtoYears[${i.index}].defid" value="${years.observationDefinitionId}"/>
                                <form:hidden path="dtoYears[${i.index}].defname" value="${years.observationDefinitionName}"/>
                                <form:hidden path="dtoYears[${i.index}].unitvalue" value="${years.unitValue}"/>
                                <td class="r250 center">
		        	<c:if test="${empty years.enumList}">
                                    <form:input path="dtoYears[${i.index}].value" type="text" value="${years.value}"/>${years.unitValue}
                                </c:if>
                                <c:if test="${not empty years.enumList}">
                                    <c:forEach var="enumYears" items="${years.enumList}">
                                    <form:radiobutton path="dtoYears[${i.index}].value" label='${enumYears["enumName"]}' value='${enumYears["enumValue"]}'/>
                                    <form:hidden path="dtoYears[${i.index}].enumid" value="${enumYears['enumName']}"/>
                                    <form:hidden path="dtoYears[${i.index}].enumname" value="${enumYears['enumName']}"/>
                                    <form:hidden path="dtoYears[${i.index}].enumvalue" value="${enumYears['enumValue']}"/>
                                    </c:forEach>
                                </c:if>
                                </td>
				<td class="r400 center">
                                <form:input path="dtoYears[${i.index}].min" type="text" value="${years.minReferenceValue}"/>
                                -
                                <form:input path="dtoYears[${i.index}].max" type="text" value="${years.maxReferenceValue}"/>
				</td>
			</tr>
                    </c:forEach>
		</tbody>
            </c:if>
	</table>
	
	<table class="fmt03 margin-auto" id="first" style="width:850px;">
            <c:if test="${not empty medicalKensaEntryInputForm.kensaListFirst}">
		<thead>
			<tr>
		    	<th>項目</th>
		    	<th>値</th>
		        <th>基準値</th>
		    </tr>
		</thead>
		<tbody>
			<c:forEach var="first" items="${medicalKensaEntryInputForm.kensaListFirst}" varStatus="i">
			<tr>
				<td class="r200 center" >${first.observationDefinitionName}</td>
                                <form:hidden path="dtoFirst[${i.index}].defid" value="${first.observationDefinitionId}"/>
                                <form:hidden path="dtoFirst[${i.index}].defname" value="${first.observationDefinitionName}"/>
                                <form:hidden path="dtoFirst[${i.index}].unitvalue" value="${first.unitValue}"/>
                                <td class="r250 center">
		        	<c:if test="${empty first.enumList}">
                                    <form:input path="dtoFirst[${i.index}].value" type="text" value="${first.value}"/>${first.unitValue}
                                </c:if>
                                <c:if test="${not empty first.enumList}">
                                    <c:forEach var="enumFirst" items="${first.enumList}">
                                    <form:radiobutton path="dtoFirst[${i.index}].value" label='${enumFirst["enumName"]}' value='${enumFirst["enumValue"]}'/>
                                    <form:hidden path="dtoFirst[${i.index}].enumid" value="${enumFirst['enumName']}"/>
                                    <form:hidden path="dtoFirst[${i.index}].enumname" value="${enumFirst['enumName']}"/>
                                    <form:hidden path="dtoFirst[${i.index}].enumvalue" value="${enumFirst['enumValue']}"/>
                                    </c:forEach>
                                </c:if>
                                </td>
				<td class="r400 center">
                                <form:input path="dtoFirst[${i.index}].min" type="text" value="${first.minReferenceValue}"/>
                                -
                                <form:input path="dtoFirst[${i.index}].max" type="text" value="${first.maxReferenceValue}"/>
				</td>
			</tr>
                    </c:forEach>
		</tbody>
            </c:if>
	</table>-->
        
	<a href="javascript:submitForm('return')" class="button button-blue" style="float:left;margin:10px"><span>戻る</span></a>
	<a href="javascript:confirmwin('confirm')" class="button button-blue" style="float:right;margin:10px"><span>確認</span></a>
        
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>

<form id="medicalKensaEntryConfirmForm" target="_self" action="./medicalKensaEntryConfirm" method="post">
    <input id="command" name="command" type="hidden" value="confirm"/>
</form>

<SCRIPT type="text/javascript">
    initPage();
</SCRIPT>