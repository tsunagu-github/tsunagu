<%-- 
    Document   : message
    Created on : 2016/08/25, 9:24:51
    Author     : kis.o-note-003
--%>

<script>
$(function(){
    $(".datepicker").datepicker({
        showOn: "button",
        buttonImage: "img/icon/calendar.png",
        buttonText: "",
        buttonImageOnly: true
    });
});

$(function() {
    $(".tabbtn a").on('click', function(e) {
        e.preventDefault();
        var target = $(this).attr('href');
        if (! $(target).length) return false;
        $('.tab', $(this).closest('.tabbtn')).removeClass('active');
        $(this).closest('.tab').addClass('active');
        $('.content', $(target).closest('.tabcontents')).removeClass('active');
        $(target).addClass('active');
    });
});

$(function() {
    $(".inline").colorbox({
        inline:true,
        rel:'group',
        loop:false,
        maxWidth:'100%',
        maxHeight:'100%',
        opacity: 0.0
    });
});

</script>

<script type="text/javascript">
function search(){
    var target = document.getElementById('messageList');
    target.style.display = '';
}

function submitSearch(command) {
	var from = document.forms[0];
	from.target="_self";
	if (from.command != null) {
		from.command.value = command;
	}
	from.submit();
}

function openWindow(cid, status, rflg) {
    var windowName = 'MessageSelect';
    var wind = window.open('./messageDummy.html',
    windowName,
    'scrollbars=no, location=no, menubar=no, toolbar=no, width=518, height=550');
    var form = document.getElementById("MessageSelectForm");
    form.target = windowName;
    form.cid.value = cid.toString();
    form.status.value = status.toString();
    form.flg.value = rflg;
    form.submit();
    
    if(status != 1 && flg){
        var read  = document.getElementById(cid);
        var readflg  = document.getElementById(cid+"_flg");

        read.style.display = "none";    
        readflg.style.display = "";
    }
    
    wind.focus();
}
</script>

<form:form modelAttribute="messageListForm" name="messageListForm" action="${action}">
<form id="targetMessageView" target="_self" action="./messageList" method="post">
<input id="command" name="command" type="hidden" value=""/>
<input id="formCd" name="formCd" type="hidden" value=""/>
<input id="formName" name="formName" type="hidden" value=""/>
<input id="param1" name="param1" type="hidden" value=""/>

<div class="headinglv01"><h1>メッセージ一覧画面<span>メッセージの一覧を表示します。</span></h1></div>

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
        		<th>対象期間</th>
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
        <table class="medicine-body" style="width:300px;float:left;">
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
	<br/>
        <form:checkbox path="readFlg" id="readFlg" name="readFlg"/><label for="readFlg">未読のみ</label>
        <div class="button button-blue" style="float:right;"><a href="javascript:submitSearch('search');"><span>検索</span></a></div>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
        </div>
        <div id="messageList" style="padding: 2em;display:block;">
            <tr>
                <form:errors path="*" cssStyle="color:red; font-size:small"/>
            </tr>
            <c:if test="${not empty messageListForm.messageList}">
                <table class="fmt03 margin-auto" style="width:850px;">
                    <thead>
			<tr>
		    	<th>送信日時</th>
		        <th>作成者</th>
		        <th>タイトル</th>
		        <th>未読/既読</th>
		        <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="message" items="${messageListForm.messageList}">
                            <tr>
                                <td class="r100 center" >${message.createDateTime}</td>
                                <td class="r150 center"><c:if test="${message.senderStatus == 3}">
                                         ${message.sendPHRID}
                                    </c:if>
                                    <c:if test="${message.senderStatus == 2}">
                                         ${message.sendMedicalOrganizationCd}
                                    </c:if>
                                    <c:if test="${message.senderStatus == 1}">
                                         ${message.sendInsurerNo}
                                    </c:if>
                                    </br>
                                    ${message.senderName}</td>
                                <td class="r150 center">${message.subject}</td>
                                <td class="r100 center">
                                    <c:if test="${message.senderStatus == 1}">
                                            <span >送信済み</span>
                                    </c:if>
                                    <c:if test="${message.senderStatus != 1}">
                                        <c:if test="${message.readFlg}" var="flg" />
                                        <c:if test="${!flg}" >
                                            <span class="midoku-info" id="${message.communicationId}">未読</span>
                                            <span id="${message.communicationId}_flg" style="display: none">既読</span>
                                        </c:if>
                                        <c:if test="${flg}" >
                                            <span >既読</span>
                                        </c:if>
                                    </c:if>
                                </td>
                                <td class="r80">
                                    <div class="btn4 margin-auto">
                                        <a href="javascript:openWindow('${message.communicationId}',${message.senderStatus},${message.readFlg});">選択</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
    </form>
</form:form>
<form  id="MessageSelectForm" target="_self" action="./messageSelect" method="post">
	<input id="command" name="command" type="hidden" value="messageSelect"/>
	<input id="cid" name="cid" type="hidden" value=""/>
        <input id="status" name="status" type="hidden" value=""/>
        <input id="flg" name="flg" type="hidden" value=""/>
</form>
