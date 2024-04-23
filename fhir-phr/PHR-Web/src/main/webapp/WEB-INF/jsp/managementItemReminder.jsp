<%-- 
    Document   : managementItemReminder
    Created on : 2016/12/22, 9:07:52
    Author     : KISNOTE011
--%>

<script type="text/javascript">
    function initPage() {
        $(".deleteBtn").click(function (event) {
            event.preventDefault();
            var $this = $(this);
            if (confirm($this.closest("tr").children("td").eq(2).text() + "を削除しますか？")) {
                submitForm('delete', $this.attr("href").substring(1));
            }
        });
    }
</script>

<form:form modelAttribute="managementItemReminderForm" name="managementItemReminderForm" action="${action}">
<input id="command" name="command" type="hidden" value=""/>
<input id="formCd" name="formCd" type="hidden" value=""/>
<input id="formName" name="formName" type="hidden" value=""/>
<input id="param1" name="reminderId" type="hidden" value=""/>

<div class="headinglv01"><h1>リマインダ設定画面<span>管理項目に対するリマインダの作成と作成されているリマインダの確認・更新が行えます。</span></h1></div>

<c:if test="${deleteCompleted}">
    <div id="deleteCompleted" style="width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Blue;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
        <span>削除が完了しました。</span>
    </div>
</c:if>
    <table border="1">
        <tr>
            <th height="25" style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">対象年度</th>
            <td style="width:170px;text-align: center">
                <select name="selectYear">
		<c:forEach varStatus="staus" var="yearItem" items="${yearList}">
                    <option value="${yearItem.year}" label="${yearItem.year}年度" ${managementItemReminderForm.selectYear == yearItem.year ? 'selected' : ''} />
	        </c:forEach>
		</select>
                <a href="javascript:submitForm('search');" class="button button-blue" id="entry" style="margin:0 0 0 0;">
                    <span>選択</span>
                </a>
            </td>
            <td style="text-align: center">
                <a href="javascript:submitForm('create');" class="button button-blue" id="entry" style="margin:0 0 0 0;width:90px;">
                    <span>新規作成</span>
                </a>
            </td>
        </tr>
    </table>
    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <div class="alarmItemListWide">
        <div class="scroll" style="height:400px;">
            <table class="fmt03 margin-auto">
                <thead>
                    <tr>
                        <th class="r30">No.</th>
                        <th class="r250">View名称</th>
                        <th class="r250">リマインダー名称</th>
                        <th class="r80">期間</th>
                        <th class="r40">通知</th>
                        <th class="r160">設定</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach varStatus="status" var="listItem" items="${reminderList}">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${fn:escapeXml(listItem.viewName)}</td>
                            <td>${fn:escapeXml(listItem.reminderTitle)}</td>
                            <td>${fn:escapeXml(listItem.reminderTypeName)}</td>
                            <td>${listItem.notificationFlg == 1 ? '○' : '×'}</td>
                            <td>
                                <a href="javascript:submitForm('move', '${fn:escapeXml(listItem.reminderTypeId)}');" class="button button-blue" style="margin:0 0 0 0;width:70px;"><span>詳細</span></a>
                                <a href="#${fn:escapeXml(listItem.reminderTypeId)}" class="button button-grey deleteBtn" style="margin:0 0 0 0;width:70px;"><span>削除</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
<SCRIPT type="text/javascript">
    initPage();
</SCRIPT>
</form:form>
