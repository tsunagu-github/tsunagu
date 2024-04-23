<%-- 
    Document   : alertSearch
    Created on : 2016/08/25, 11:10:17
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
    
    $( "#popup" ).position({
        my: "right top",
        at: "left bottom+5",
        of: "#position"
    });
    
    $('#popup').dialog({
            autoOpen: false,
            closeOnEscape: false,
            modal: false,
            width: 310,
            height: 280
    });
</script>

<script type="text/javascript">
    function search(){
	var target = document.getElementById('alertList');
	target.style.display = '';	
    }
    
    function showPopup(command, param1) {
        var form = document.getElementById("alertSearchForm");
        if (form.command != null) {
            form.command.value = command;
        }
        if (form.param1 != null) {
            form.param1.value = param1;
        }
        form.submit();

        target = document.getElementById("selectedPtInfo");
        if (!command) var command = window.event;
        if (!command.pageX) px = command.clientX + document.body.scrollRight; else px = command.pageX;
        if (!command.pageY) py = command.clientY + document.body.scrollTop; else py = command.pageY;

        target.style.left = px-300 + "px";
        target.style.top = py+5 + "px";
        target.style.display = "block";
    }
    function hidePopup() {
        target = document.getElementById("selectedPtInfo");
        target.style.display = "none";
    }
    
    function oneTimePass(){
        jPrompt('ワンタイムパスワードを入力してください', '', 'ワンタイムパスワード入力', function(r) {
            if(r==='' || r===null){
                var obDoc = document;
                var errMSG = obDoc.getElementById('errMSG');
                var canMSG = obDoc.getElementById('canMSG');
                if(r===''){
                    //エラーメッセージ
                    errMSG.style.display = '';
                    canMSG.style.display = 'none';
                }else{
                    //キャンセルメッセージ
                    errMSG.style.display = 'none';
                    canMSG.style.display = '';
                }
            }else{
                openPopUpPatientWindow(r);
            }
        });
    }
    
    
//    function openPopUpPatientWindow(r) {
//        var windowName = 'PatientPopupView';
//        var wind = window.open("./targetingPatientInfoDummy.html", windowName,"width=1000,height=700");
//        var form = document.getElementById("targetPopUpForm");
//        form.target = windowName;
//        var onePW = document.getElementById("_onePW");
//        onePW.value= r;
//        form.submit();
//        wind.focus();
//    }
    
    function getDate(separator) {
        var d = new Date();
 
        return [
            d.getFullYear(),
            ('0' + (d.getMonth() + 1)).slice(-2),
            ('0' + d.getDate()).slice(-2)
        ].join(separator);
    }
    
    function openwin(param1) {
        var windowName = 'alertPatientInfo';
        var wind = window.open('./messageDummy.html', windowName, 'width=310, height=280');
        var form = document.getElementById("alertPatientInfoForm");
        
        form.target = windowName;
        form.param1.value = param1;
        form.submit();
        wind.focus();
    }
</script>

<form:form modelAttribute="alertSearchForm" name ="alertSearchForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
    <input id="param2" name="param2" type="hidden" value=""/>

<div class="headinglv01"><h1>アラート一覧画面<span>アラートの一覧を表示します。</span></h1></div>

<div class="search-section">
	<table>
	 <tr>
	 <td>
        <table class="medicine-body" style="width:250px;float:left;">
        <tbody>
        	<tr>
        		<th>PHR ID</th>
        		<td>
                                <form:input path="phrId" cssClass="phrId" id="phrId" name="phrId" style="ime-mode:disabled;width:150px;" type="text" maxlength="14" />
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>検査期間</th>
        		<td>
                            <form:input path="startDate" id="startDate" name="startDate" style="width:150px;" type="text" maxlength="10" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
	 	～
	 </td>
	 <td>
        <table class="medicine-body" style="width:200px;float:left;">
        <tbody>
        	<tr>
        		<td>
                            <form:input path="endDate" id="endDate" name="endDate" style="width:150px;" type="text" maxlength="10" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
                <div class="button button-blue" style="float:right;"><a href="javascript:submitForm('search');"><span>検索</span></a></div>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>
                        
<div id="alertList" style="padding: 2em;">
    <div id="errMSG" style="padding: 1em;display:none;">
        <span style="color:red;">ワンタイムパスワードは入力必須です。</span>
    </div>
    <div id="canMSG" style="padding: 1em;display:none;">
        <span>ワンタイムパスワードの入力がキャンセルされました。</span>
    </div>
    <tr>
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    </tr>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
        <c:if test="${not empty alertSearchForm.alertList}">
	<table class="fmt03 margin-auto" style="width:850px;">
		<thead>
			<tr>
		    	<th>検査日</th>
		        <th>対象者</th>
		        <th>画面名称</th>
		        <th>アラート項目</th>
		        <th>対象者情報</th>
		        <th></th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="alert" items="${alertSearchForm.alertList}">
                        <tr>
                            <td class="r100 center">${alert.displayDate}</td>
                            <td class="r100 center">${alert.familyName}　${alert.givenName}</td>
                            <td class="r100 center">${alert.viewName}</td>
                            <td class="r100 center">${alert.displayName}</td>
                            <td class="r80 center">
                            <div id="popup">
                                <div class="btn4 margin-auto" id="position">
                                    <a href= "javascript:openwin('${alert.pHR_ID}');">確認</a>
                                </div>
                            </div>
                            </td>
                            <td class="r80">
                                <div class="btn4 margin-auto">
                                    <a href="javascript:openPopUpPatientWindow('${insurerNo}','${alert.pHR_ID}')">選択</a>
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
<!-- 
<form id="targetPopUpForm" target="_self" action="./targetingPatient" method="post">
    <input id="command" name="command" type="hidden" value="searchOneTimePW"/>
    <input type="hidden" id="_onePW" name="_onePW" value="">
    <input type="hidden" id="_insurerCd" name="insurerCd" value="">
</form>
 -->
<form id="alertPatientInfoForm" target="_self" action="./alertPatientInfo" method="post">
    <input id="command" name="command" type="hidden" value="move"/>
    <input id="param1" name="param1" type="hidden" value=""/>
</form>
    
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
