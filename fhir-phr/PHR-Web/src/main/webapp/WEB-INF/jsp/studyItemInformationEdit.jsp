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
    
    function confirmwin(command) {
        jConfirm('登録してもよろしいですか？', '登録確認', function(r) {
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
    
    function init(){
        displayPassword();
    }
</script>

<form:form modelAttribute="studyItemInformationForm" name ="studyItemInformationForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>

<form:errors path="*" cssStyle="color:red; font-size:small"/>
<div id="ItemArea" style="padding: 2em;">
    
        <%-- <form:errors path="*" cssStyle="color:red; font-size:small"/> --%>
    
    <table class="fmt03 margin-auto" style="width:500px;"><!--id="1month"-->
        <%-- <c:if test="${not empty medicalKensaEntryInputForm.kensaListOne}"> --%>
        <tbody>
            <tr>
                <th align="left"><span class="esse">研究名</span></th>
                <td style="width: 100px;text-align: center">
                    <c:if test="${studyItemInformationForm.editFlg == false}">
	                    <select name="studyName">
	                        <c:forEach varStatus="staus" var="Item" items="${studyItemInformationForm.studyInformationEntityList}">
	                                <option value="${Item.studyName}" label="${Item.studyName}" ${studyItemInformationForm.studyName == Item.studyName ? 'selected' : ''} />
	                        </c:forEach>
	                    </select>
                    </c:if>
                    <c:if test="${studyItemInformationForm.editFlg == true}">
                        ${studyItemInformationForm.studyName}
                    </c:if>
                </td>
            </tr>
            <tr>
                <th><span class="esse">同意種別</span></th>
                <td>
                    <c:if test="${studyItemInformationForm.editFlg == false}">
		                <form:radiobutton id="consentType" path="consentType" style="ime-mode:disabled;" value="1" label="オプトイン" checked="checked" onclick="displayPassword()"/>
		                <form:radiobutton id="consentType" path="consentType" style="ime-mode:disabled;" value="2" label="オプトアウト" onclick="displayPassword()"/>
                    </c:if>
                    <c:if test="${studyItemInformationForm.editFlg == true}">
                        <c:choose>
							<c:when test="${studyItemInformationForm.consentType == 1}">オプトイン</c:when>
							<c:when test="${studyItemInformationForm.consentType == 2}">オプトアウト</c:when>
		                </c:choose>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th><span class="esse">説明</span></th>
                <td>
                    <c:if test="${studyItemInformationForm.editFlg == false}">
                        <form:input id="subject" path="subject" style="ime-mode:disabled;" value="" size="30"/>
                    </c:if>
                    <c:if test="${studyItemInformationForm.editFlg == true}">
                        ${studyItemInformationForm.subject}
                    </c:if>
                </td>
            </tr>
            <tr>
                <th><span class="esse">詳細説明サイトURL</span></th>
                <td>
                        <form:input id="url" path="url" style="ime-mode:disabled;" value="" size="30"/>
                </td>
            </tr>
            <c:if test="${studyItemInformationForm.consentType == 1}">
	            <tr id="check" style="display:;">
	                <th><span class="esse">チェックリスト</span></th>
	                <td>
	                        <form:textarea id="checkList" path="checkList" style="ime-mode:disabled; resize: vertical; width: 233px; height: 184px;" value="" size="30" />
	                </td>
	            </tr>
            </c:if>
            </tbody>
        </table>
        
    <a href="javascript:submitForm('return')" class="button button-blue" style="float:left;margin:10px"><span>戻る</span></a>
    <a href="javascript:confirmwin('confirm')" class="button button-blue" style="float:right;margin:10px"><span>登録</span></a>
    
</div>

<div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
</form:form>
<script type="text/javascript">
init();

function displayPassword(){
    var flg =document.getElementById("consentType").checked;

    if (flg) {
        document.getElementById("check").style.display = "";
    }else{
        document.getElementById("check").style.display = "none";
    }
}
</script>