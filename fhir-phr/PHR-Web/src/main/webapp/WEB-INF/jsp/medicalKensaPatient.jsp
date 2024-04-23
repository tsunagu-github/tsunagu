<%--
    Document   : medicalKensaPatient
    Created on : 2016/09/26, 9:16:00
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
    
    function openwin(command) {
        var windowName = '_self';
        var from = document.getElementById("medicalKensaEntryInputForm");
        from.target = windowName;
        //from.param1.value = param1;
        from.command.value = command;
        from.submit('command');
    }
    
</script>

<form:form modelAttribute="medicalKensaPatientForm" name ="medicalKensaPatientForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <input id="param2" name="param2" type="hidden" value=""/>

<div class="search-section">
	<table>
	 <tr>
	 <td valign="top">
        <table class="medicine-body" style="width:250px;float:left;">
        <tbody>
        	<tr>
        		<th>患者ID</th>
                        <td>${medicalKensaPatientForm.patientList.patientId}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>氏名（漢字）</th>
        		<td>${medicalKensaPatientForm.patientList.name}</td>
        	</tr>            
        	<tr>
        		<th>氏名（カナ）</th>
        		<td>${medicalKensaPatientForm.patientList.kana}</td>
        	</tr>

        </tbody>
        </table>
	 </td>
	 <td>
        <table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>生年月日</th>
        		<td>${medicalKensaPatientForm.patientList.birth}</td>
        	</tr>
        	<tr>
        		<th>性別</th>
        		<td>${medicalKensaPatientForm.patientList.sex}</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
	<table style="margin-left: auto;margin-right: 10px;margin-top: 10px;">
		<tr>
	       <td>
	           <a href="javascript:submitForm('create')" class="button button-blue"><span>新規登録</span></a>
	       </td>
     </tr>
    </table>
		<br/>
        
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>
<div class="search-section">
	<table>
	 <tr>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査期間</th>
        		<td>
	        		<form:input path="startDate" id="startDate" name="startDate" style="width:150px;" type="text" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
	 	～
	 </td>
	 <td>
        <table class="medicine-body" style="width:250px;float:left;">
        <tbody>
        	<tr>
        		<td>
	        		<form:input path="endDate" name="endDate" id="endDate" class="datepicker" style="width:150px;" type="text" maxlength="10"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
        <a href="javascript:submitForm('search')" class="button button-blue" style="float:right;"><span>検索</span></a>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>
<div id="patientList" style="padding: 2em;">
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <c:if test="${not empty medicalKensaPatientForm.observationEventList}">
	<table class="fmt03 margin-auto" style="width:850px;">
		<thead>
			<tr>
		    	<th>検査日</th>
		    	<!--  <th>検査登録期間</th>-->
		        <!--<th>登録者</th>-->
		        <th>登録日時</th>
		        <!--<th>最終更新者</th>-->
		        <th>最終更新日</th>
		        <th></th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="observation" items="${medicalKensaPatientForm.observationEventList}">
			<tr>
                            <td class="r100 center" >${observation["examinationdate"]}</td>
                            <!-- <td class="r100 center" >${observation["remindertype"]}</td> -->
                            <!--<td class="r80 center">${observation["creater"]}</td>-->
                            <td class="r140 center">${observation["createdatetime"]}</td>
                            <!--<td class="r80 center">${observation["updater"]}</td>-->
                            <td class="r140 center">${observation["updatadatetime"]}</td>
                            <td class="r80"><div class="btn4 margin-auto">
                            <a href="javascript:submitForm('update','${observation["observationeventid"]}','${observation["examinationdate"]}')">選択</a></div>
		        </td>
			</tr>
                    </c:forEach>
		</tbody>
            </table>
        </c:if>
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>
<!--
<form id="medicalKensaEntryInputForm" target="_self" action="./medicalKensaEntryInput" method="post">
    <input id="command" name="command" type="hidden" value="create"/>
</form>
-->