<%-- 
    Document   : notice
    Created on : 2016/08/30, 13:20:08
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
function search(){
    var target = document.getElementById('noticeForm');
    target.style.display = '';	
}

function showPopup(event) {
    target = document.getElementById("inline-content1");

    if (!event) var event = window.event;
    if (!event.pageX) px = event.clientX + document.body.scrollRight; else px = event.pageX;
    if (!event.pageY) py = event.clientY + document.body.scrollTop; else py = event.pageY;

    target.style.left = px-300 + "px";
    target.style.top = py+5 + "px";
    target.style.display = "block";
}

function hidePopup() {
    target = document.getElementById("inline-content1");
    target.style.display = "none";
}

function opennewwin() {
    var windowName = 'noticeNewCreate';
    var wind = window.open("./messageDummy.html", windowName,"width=500,height=400");
    var form = document.getElementById("noticeNewCreateForm");
    form.target = windowName;
    //form.command.value = command;
    form.submit();
    wind.focus();
}

function openwin(param1) {
    var windowName = 'noticeDetail';
    var wind = window.open("./messageDummy.html", windowName,"scrollbars=no, location=no, menubar=no, toolbar=no, width=500,height=400");
    var from = document.getElementById("noticeDetailForm");
    from.target = windowName;
    from.param1.value = param1;
    //form.command.value = command;
    from.submit();
    wind.focus();
}
</script>

<form:form modelAttribute="noticeForm" name ="noticeForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>

<div class="headinglv01"><h1>おしらせ一覧画面<span>おしらせの一覧を表示します。</span></h1></div>

<div class="search-section">
	<table>
	 <tr>
	 <td>
		<table class="medicine-body" style="width:300px;float:left;">
        <tbody>
        	<tr>
        		<th>対象期間</th>
        		<td>
	        		<form:input path ="startDate" id="startDate" name="startDate" style="width:150px;" type="text" maxlength="10" class="datepicker"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 <td>
	 	～
	 </td>
	 <td>
        <table class="medicine-body" style="width:230px;float:left;">
        <tbody>
        	<tr>
        		<td>
	        		<form:input path ="endDate" name="endDate" id="endDate" class="datepicker" type="text" maxlength="10"/>
        		</td>
        	</tr>
        </tbody>
        </table>
	 </td>
	 </tr>
	</table>
		<br/>
                <div class="button button-blue" style="float:right;"><a href="javascript:submitForm('search');"><span>検索</span></a></div>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
</div>
</form>
<div id="noticeList" style="padding: 1em;">
	
	<div class="btn4 margin-auto" style="padding: 1em;float:right;">
		<a href="javascript:opennewwin();"><span>新規作成</span></a>
	</div>
    
        <tr>
            <form:errors path="*" cssStyle="color:red; font-size:small"/>
        </tr>
    
	<c:if test="${not empty noticeForm.noticeList}">
	<table class="fmt03 margin-auto" style="padding: 2em;width:850px;">
		<thead>
			<tr>
		    	<th>送信日時</th>
		        <th>作成者</th>
		        <th>タイトル</th>
		        <th></th>
		    </tr>
		</thead>
		<tbody>
                    <c:forEach var="notice" items="${noticeForm.noticeList}">
			<tr class="unread">
				<td class="r100 center" >${notice.createDateTime}<br/></td>
				<td class="r150 center">${notice.senderName}</td>
		        <td class="r150 center">${notice.subject}</td>
		        <td class="r80"><div class="btn4 margin-auto">
                            <a href="javascript:openwin('${notice.communicationId}');">
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

<form id="noticeDetailForm" target="_self" action="./noticeDetail" method="post">
    <input id="command" name="command" type="hidden" value="move"/>
    <input id="param1" name="param1" type="hidden" value=""/>
</form>

<form id="noticeNewCreateForm" target="_self" action="./noticeNewCreate" method="post">
    <input id="command" name="command" type="hidden" value="create"/>
</form>