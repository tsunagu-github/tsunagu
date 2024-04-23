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
    
    function submitForm2(command, index) {
        var from = document.forms[0];
        if (from.command !== null) {
            from.command.value = command;
        }
        if (from.index !== null) {
            from.index.value = index;
        }
        from.submit();
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
    <form:hidden path="param1"/>
    <form:hidden path="param2"/>
<!--     <input id="param1" name="param1" type="hidden" value=""/> -->

<div id="ItemArea" style="padding: 2em;" align="right">
    <table>
	    <tr>
		    <td valign="bottom">
	            <a  href="javascript:submitForm('create');" class="button button-green" >
	                <span>新規作成</span>
	            </a>
			</td>
		</tr>
	</table>
    
</div>

<c:if test="${not empty studyItemInformationForm.studyItemInformationEntityList}">
<table class="fmt03 margin-auto" style="width:900px;">
    <thead>
        <tr>
            <th>研究名</th>
            <th>同意種別</th>
            <th>説明</th>
            <th>修正</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach varStatus="status" var="item" items="${studyItemInformationForm.studyItemInformationEntityList}">
        <tr>
            <td class="r200 center" >${item.studyName}</td>
            <td class="r50 center">
	            <c:choose>
	              <c:when test="${item.consentType == 1}">オプトイン</c:when>
	              <c:when test="${item.consentType == 2}">オプトアウト</c:when>
	            </c:choose>
            </td>
            <td class="r120 center">${item.subject}</td>
            <td class="r50 center" style="padding: 0.25em 0em;">
                <a href="javascript:submitForm('edit','${item.studyId}','${item.subjectId}','${item.subject}');" class="button button-blue" style="margin: 0; height:22px"><span style="padding: 0.2em 2em;">修正</span></a>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</c:if>
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