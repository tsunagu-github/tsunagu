<%-- 
    Document   : managementItemSetting
    Created on : 2016/12/21, 19:43:36
    Author     : KISNOTE011
--%>

<script type="text/javascript">
    function initPage() {
        var renumber = function () {
            $(".numberLabelTextClass").each(function (index) {
                $(this).text(index + 1);
            });
        };
        $(".upListButton").click(function (event) {
            event.preventDefault();
            var $tr = $(this).closest("tr"),
                $prev = $tr.prev("tr");
            if ($prev.length) {
                $prev.before($tr);
                renumber();
            }
        });
        $(".downListButton").click(function (event) {
            event.preventDefault();
            var $tr = $(this).closest("tr"),
                $next = $tr.next("tr");
            if ($next.length) {
                $next.after($tr);
                renumber();
            }
        });
        $("#listMove").click(function (event) {
            event.preventDefault();

            var $moveNum = $("#moveNum"),
                moveNumVal = $moveNum.val(),
                $swapTargetRadioButtons = $(".swapTargetListClass").find("input[type=radio]"),
                $checkedSwapTargetRadioButtons = $swapTargetRadioButtons.filter(":checked"),
                errMessage = "";

            $moveNum.val("");

            if (!/^[0-9]{1,3}$/.test(moveNumVal)) {
                errMessage += "移動先を1以上" + $swapTargetRadioButtons.length + "以下の半角数字で指定してください。\r\n";
            } else if (0 === +moveNumVal) {
                errMessage += "移動先を1以上" + $swapTargetRadioButtons.length + "以下の半角数字で指定してください。\r\n";
            } else if ($swapTargetRadioButtons.length < +moveNumVal) {
                errMessage += "移動先を1以上" + $swapTargetRadioButtons.length + "以下の半角数字で指定してください。\r\n";
            }

            if ($checkedSwapTargetRadioButtons.length !== 1) {
                errMessage += "移動対象を指定してください。\r\n";
            }

            if (errMessage) {
                alert(errMessage);
                return;
            }

            if ($swapTargetRadioButtons[moveNumVal - 1] === $checkedSwapTargetRadioButtons[0]) {
                alert("移動元と移動先が同じです。\r\n");
                return;
            }

            if ($swapTargetRadioButtons.index($checkedSwapTargetRadioButtons[0]) < (moveNumVal - 1)) {
                $swapTargetRadioButtons.eq(moveNumVal - 1).closest("tr").after($checkedSwapTargetRadioButtons.closest("tr"));
            } else {
                $swapTargetRadioButtons.eq(moveNumVal - 1).closest("tr").before($checkedSwapTargetRadioButtons.closest("tr"));
            }

            renumber();
        });
        var saveToSessionStrage = function (key, value) {
            if ('sessionStorage' in window && window.sessionStorage !== null) {
                window.sessionStorage[key] = value;
            }
        };
        var loadFromSessionStrage = function (key) {
            if ('sessionStorage' in window && window.sessionStorage !== null) {
                return window.sessionStorage[key];
            } else {
                return undefined;
            }
        };
        var toggleChangeListOrderDisplayStateFunc = function (flag) {
            if (flag === "1") {
                $("#toggleChangeListOrderDisplayState").text("▼");
                $("#changeListOrderDiv").hide();
            } else {
                $("#toggleChangeListOrderDisplayState").text("▲");
                $("#changeListOrderDiv").show();
            }
        };
        (function () {
            toggleChangeListOrderDisplayStateFunc(
                    loadFromSessionStrage("phrwebManagementItemSettingChangeListOrderDisplayState"));
        })();
        $("#toggleChangeListOrderDisplayState").click(function (event) {
            event.preventDefault();
            if ($(this).text() === "▲") {
                toggleChangeListOrderDisplayStateFunc("1");
                saveToSessionStrage("phrwebManagementItemSettingChangeListOrderDisplayState", "1");
            } else {
                toggleChangeListOrderDisplayStateFunc("0");
                saveToSessionStrage("phrwebManagementItemSettingChangeListOrderDisplayState", "0");
            }
        });
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
            $("#complete").hide();
            $(".operationAreaClass").hide();
            $("#changeListOrderDiv").hide();
            $("#diseaseItemTable").hide();
            $("#diseaseItemTableEmpty").show();
            selectViewInitializeHandler("1");
        });
        $("select[name=selectView]").change(function () {
            submitForm('search');
        });
        (function () {
            if ($("#initialDisplayFlag").val() === "1") {
                $("#diseaseItemTableEmpty").show();
            } else {
                $("#diseaseItemTable").show();
            }
        })();
    }
</script>

<form:form modelAttribute="managementItemSettingForm" name="managementItemSettingForm" action="${action}">
<input id="command" name="command" type="hidden" value=""/>
<input id="formCd" name="formCd" type="hidden" value=""/>
<input id="formName" name="formName" type="hidden" value=""/>
<input id="param1" name="param1" type="hidden" value=""/>
<input id="viewList" type="hidden" value="${fn:escapeXml(viewList)}" />
<input id="selectViewInit" type="hidden" value="${fn:escapeXml(managementItemSettingForm.selectView)}" />
<input id="initialDisplayFlag" type="hidden" value="${initialDisplayFlag}" />

<div class="headinglv01"><h1>管理項目設定画面<span>管理項目の設定が行えます。</span></h1></div>

<c:if test="${saveCompleted}">
    <div id="complete" style="width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Blue;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
        <span>保存が完了しました。</span>
    </div>
</c:if>
    <table border="1">
        <tr>
            <th height="25" style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">対象年度</th>
            <td style="width: 100px;text-align: center">
                <select name="selectYear">
		<c:forEach varStatus="staus" var="yearItem" items="${yearList}">
                    <option value="${yearItem.year}" label="${yearItem.year}年度" ${managementItemSettingForm.selectYear == yearItem.year ? 'selected' : ''} />
	        </c:forEach>
		</select>
            </td>
            <th style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">対象View</th>
            <td style="width: 130px;text-align: center;">
                <select name="selectView">
		</select>
            </td>
            <c:if test="${initialDisplayFlag != '1'}">
            <th class="operationAreaClass" style="color: white;background-color: green;width: 100px;padding:0px 0px 0px 5px;">操作</th>
            <td class="operationAreaClass" style="width: 120px;text-align: center">
                <a href="javascript:submitForm('update');" class="button button-blue" id="entry" style="margin:0 0 0 0;">
                    <span>保存</span>
                </a>
                <c:if test="${not empty managementItemSettingForm.selectView}">
                <a id="toggleChangeListOrderDisplayState" href="#" style="text-decoration:none;">▲</a>
                </c:if>
            </td>
            </c:if>
        </tr>
    </table>
    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <div class="alarmItemListWide">
        <c:if test="${not empty managementItemSettingForm.selectView}">
        <div id="changeListOrderDiv" style="text-align: right;">
            <table>
                <tr>
                    <td style="text-align: right;" id="listMenu">
                        <input type="text" id="moveNum" maxlength="3" size="3"/>に
                        <a href="#" class="button button-blue" id="listMove" style="margin:0 0 0 0;"><span>移動</span></a>
                    </td>
                </tr>
            </table>
        </div>
        </c:if>
        <div class="scroll" style="height:400px;">
            <table class="fmt03 margin-auto" id="diseaseItemTable" style="display: none;">
                <thead>
                    <tr>
                        <c:if test="${not empty managementItemSettingForm.selectView}">
                        <th class="r70">移動選択</th>
                        </c:if>
                        <th class="r30">No.</th>
                        <th class="r200">項目</th>
                        <c:if test="${not empty managementItemSettingForm.selectView}">
                        <th class="r40">↑↓</th>
                        </c:if>
                        <c:forEach varStatus="status" var="diseaseTypeItem" items="${diseaseTypeList}">
                        <th class="r80">${fn:escapeXml(diseaseTypeItem.name)}</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach varStatus="status" var="observationDefinitionItem" items="${observationDefinitionList}">
                    <tr class="swapTargetListClass">
                        <c:if test="${not empty managementItemSettingForm.selectView}">
                        <td class="center"><input type="radio" value="" name="ok01" /></td>
                        </c:if>
                        <td class="center"><span class="numberLabelTextClass">${status.count}</span><input type="hidden" name="observationDefinitionOrder" value="${fn:escapeXml(observationDefinitionItem.observationDefinitionId)}" /></td>
                        <td>${fn:escapeXml(observationDefinitionItem.displayName)}</td>
                        <c:if test="${not empty managementItemSettingForm.selectView}">
                        <td class="center"><a href="#" class="upListButton">▲</a><a href="#" class="downListButton">▼</a></td>
                        </c:if>
                        <c:forEach varStatus="diseaseTypeStatus" var="diseaseTypeItem" items="${diseaseTypeList}">
                        <td class="center">
                            <c:if test="${diseaseTypeItem.diseaseTypeCd==''}">-</c:if>
                            <c:if test="${diseaseTypeItem.diseaseTypeCd!=''}">
                            <input type="checkbox" name="insurerDiseaseType" value="${fn:escapeXml(observationDefinitionItem.observationDefinitionId.concat('-').concat(diseaseTypeItem.diseaseTypeCd))}" ${insurerDiseaseTypeCheckedList.contains(observationDefinitionItem.observationDefinitionId.concat('-').concat(diseaseTypeItem.diseaseTypeCd)) ? 'checked' : ''} />
                            </c:if>
                        </td>
                        </c:forEach>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <table class="fmt03 margin-auto" id="diseaseItemTableEmpty" style="display: none;">
                <thead>
                    <tr>
                        <th class="r30">No.</th>
                        <th class="r200">項目</th>
                        <th class="r80">-</th>
                        <th class="r80">-</th>
                        <th class="r80">-</th>
                        <th class="r80">-</th>
                        <th class="r80">-</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
<SCRIPT type="text/javascript">
    initPage();
</SCRIPT>
</form:form>
