<%-- 
    Document   : managementItemView
    Created on : 2016/12/20, 15:58:15
    Author     : KISNOTE011
--%>

<script type="text/javascript">
    function initPage() {
        var selectViewInitializeHandler = function (selectEmptyValue, selectViewValue) {
            var selectYearValue = $("select[name=selectYear] option:selected").val();
            var $selectView = $("select[name=selectView]");
            $selectView.find("*").remove();
            if (selectEmptyValue === "1") {
                $selectView.append($("<option>").attr("value", "").attr("label", "").attr("selected", "selected"));
            }
            $.each($.parseJSON($("#viewList").val())[selectYearValue], function (index, element) {
                var opt = $("<option>")
                        .attr("value", element.id)
                        .attr("label", element.label);
                if (selectEmptyValue !== "1" && selectViewValue === ("" + element.id)) {
                    opt.attr("selected", "selected");
                }
                $selectView.append(opt);
            });
        };
        (function () {
            selectViewInitializeHandler($("#initialDisplayFlag").val(), $("#selectViewInit").val());
        })();
        $("select[name=selectYear]").change(function () {
            $("#managementItemListTableBody").hide();
            selectViewInitializeHandler("1");
        });
        $("select[name=selectView]").change(function () {
            submitForm('search');
        });
    }
</script>

<form:form modelAttribute="managementItemViewForm" name="managementItemViewForm" action="${action}">
<input id="command" name="command" type="hidden" value=""/>
<input id="formCd" name="formCd" type="hidden" value=""/>
<input id="formName" name="formName" type="hidden" value=""/>
<input id="param1" name="param1" type="hidden" value=""/>
<input id="viewList" type="hidden" value="${fn:escapeXml(viewList)}" />
<input id="selectViewInit" type="hidden" value="${fn:escapeXml(managementItemViewForm.selectView)}" />
<input id="initialDisplayFlag" type="hidden" value="${initialDisplayFlag}" />

<div class="headinglv01"><h1>管理項目情報画面<span>設定されている管理項目の情報を表示します。</span></h1></div>

    <table border="1">
        <tr>
            <th height="25" style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">対象年度</th>
            <td style="width: 100px;text-align: center">
                <select name="selectYear">
		<c:forEach varStatus="staus" var="yearItem" items="${yearList}">
                    <option value="${yearItem.year}" label="${yearItem.year}年度" ${managementItemViewForm.selectYear == yearItem.year ? 'selected' : ''} />
	        </c:forEach>
		</select>
            </td>
            <th style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">対象View</th>
            <td style="width: 130px;text-align: center;">
                <select name="selectView">
                </select>
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
                        <th class="r250">項目</th>
                        <th class="r90">単位</th>
                        <th class="r90">登録値上限</th>
                        <th class="r90">登録値下限</th>
                        <th class="r300">リマインダー</th>
                    </tr>
                </thead>
                <tbody id="managementItemListTableBody">
                    <c:forEach varStatus="status" var="listItem" items="${managementItemList}">
                    <tr>
                        <td class="center">${status.count}</td>
                        <td class="left">${fn:escapeXml(listItem.displayName)}</td>
                        <td class="left">${fn:escapeXml(listItem.unitValue)}</td>
                        <td class="right">${fn:escapeXml(listItem.maxReferenceValue)}</td>
                        <td class="right">${fn:escapeXml(listItem.minReferenceValue)}</td>
                        <td class="left">${listItem.reminders}</td>
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
