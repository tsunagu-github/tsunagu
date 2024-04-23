<%-- 
    Document   : managementItemReminderDetail
    Created on : 2016/12/22, 10:10:12
    Author     : KISNOTE011
--%>

<script type="text/javascript">
    function initPage() {

        var initReminderMessageShowState = function(v) {
            if (v === "0") {
                $(".reminderMessageNotFirst").show();
                $(".reminderMessagePeriodClass").prop("disabled", false);
                $(".reminderMessageMonthClass").prop("disabled", true);
                $("#itemListTable").css("visibility", "visible");
            } else {
                $(".reminderMessageNotFirst").hide();
                $(".reminderMessagePeriodClass").prop("disabled", true);
                $(".reminderMessageMonthClass").prop("disabled", false);
                $("#itemListTable").css("visibility", "hidden");
            }
        };
        initReminderMessageShowState($(".sendTypeRadio").first().prop("checked") ? "0" : "1");
        $(".sendTypeRadio").click(function () {
            var $this = $(this);
            initReminderMessageShowState($this.val());
        });

        $("#diseaseTypeCheckAll").click(function () {
            var $this = $(this),
                $siblings = $this.siblings("input[type=checkbox]");
            $siblings.prop("checked", $this.prop("checked"));
        });

        var initAllCheckBox = function () {
            var $items = $("#diseaseTypeCheckAll").siblings("input[type=checkbox]");
            $("#diseaseTypeCheckAll").prop("checked", $items.length === $items.filter(":checked").length);
        };
        initAllCheckBox();
        $("#diseaseTypeCheckAll").siblings("input[type=checkbox]").click(function () {
            initAllCheckBox();
        });

        (function() {
            if (!$("#editModeFlag").val()) {
                if ($("#initialDisplayFlag").val()) {

                    $("#ViewNameDisplayModeId").hide();

                    var $form = $("#managementItemReminderDetailForm");
                    $form.find("input").not(".formActionControlItemClass").prop("disabled", true);
                    $form.find("textarea").prop("disabled", true);
                    $form.find("select").not("[name=viewId]").prop("disabled", true);

                    $("#checkBtn").hide();
                    $("#registerBtn").hide();
                    $("#updateBtn").hide();
                    $("#editBtn").hide();
                    $("#abortBtn").hide();

                } else {

                    $("#ViewNameRegisterModeId").hide();

                    $("#updateBtn").hide();
                    $("#editBtn").hide();
                    $("#abortBtn").hide();

                }
            } else if ($("#initialDisplayFlag").val()) {

                $("#ViewNameRegisterModeId").hide();

                var $form = $("#managementItemReminderDetailForm");
                $form.find("input").not(".formActionControlItemClass").prop("disabled", true);
                $form.find("textarea").prop("disabled", true);
                $form.find("select").prop("disabled", true);

                $("#checkBtn").hide();
                $("#registerBtn").hide();
                $("#updateBtn").hide();
                $("#abortBtn").hide();

            } else {

                $("#ViewNameRegisterModeId").hide();

                $("#registerBtn").hide();
                $("#editBtn").hide();
            }
        })();

        $("[name=viewId]").change(function () {
            submitForm('selectViewId');
        });

        $("#checkBtn a").click(function (event) {
            event.preventDefault();
            submitForm('check');
        });

        $("#registerBtn a").click(function (event) {
            event.preventDefault();
            submitForm('insert');
        });

        $("#updateBtn a").click(function (event) {
            event.preventDefault();
            submitForm('update');
        });

        $("#editBtn a").click(function (event) {
            event.preventDefault();

            var $form = $("#managementItemReminderDetailForm");
            $form.find("input").prop("disabled", false);
            $form.find("textarea").prop("disabled", false);
            $form.find("select").prop("disabled", false);

            initReminderMessageShowState($(".sendTypeRadio").first().prop("checked") ? "0" : "1");

            $("#checkBtn").show();
            $("#updateBtn").show();
            $("#editBtn").hide();
            $("#abortBtn").show();
        });

        $("#abortBtn a").click(function (event) {
            event.preventDefault();
            submitForm('abort');
        });

        $("#returnBtn a").click(function (event) {
            event.preventDefault();
            submitForm('return');
        });

        $("form[name=managementItemReminderDetailForm] textarea").change(function () {
            var $this = $(this);
            if (+$this.attr("maxlength") < $this.val().length) {
                $this.val($this.val().substring(0, +$this.attr("maxlength")));
            }
        });
    }
</script>

<form:form modelAttribute="managementItemReminderDetailForm" name="managementItemReminderDetailForm" action="${action}">
<input id="command" name="command" type="hidden" value="" class="formActionControlItemClass" />
<input id="formCd" name="formCd" type="hidden" value="" class="formActionControlItemClass" />
<input id="formName" name="formName" type="hidden" value="" class="formActionControlItemClass" />
<input id="param1" name="param1" type="hidden" value="" class="formActionControlItemClass" />
<input id="editModeFlag" value="${editModeFlag}" type="hidden" />
<input id="initialDisplayFlag" value="${initialDisplayFlag}" type="hidden" />

<div class="headinglv01"><h1>リマインダ設定詳細画面<span>リマインダ設定の詳細情報を作成・確認・更新が行えます。</span></h1></div>
<c:if test="${saveCompleted}">
    <div id="complete" style="width:540px;padding:3px;margin:0 0 5px 0;line-height:25px;color: Blue;font-weight:bold;	font-size:medium;background-color:#ffffb2;border-radius:10px;">
        <span>保存が完了しました。</span>
    </div>
</c:if>
    <div style="float:right">
        <table >
            <tr>
                <th style="color: white;background-color: green;width: 70px;padding:0px 0px 0px 5px;">操作</th>
                <td>
                    <table>
                        <tr>
                            <td id="checkBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>点検</span>
                                </a>
                            </td>
                            <td id="registerBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>保存</span>
                                </a>
                            </td>
                            <td id="updateBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>保存</span>
                                </a>
                            </td>
                            <td id="editBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>編集</span>
                                </a>
                            </td>
                            <td id="abortBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>中止</span>
                                </a>
                            </td>
                            <td id="returnBtn" style="text-align: center">
                                <a href="#" class="button button-blue" style="margin:0 0 0 0;width:70px;">
                                    <span>戻る</span>
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <form:errors path="*" cssStyle="color:red; font-size:small"/>
    <div class="alarmItemListWide">
        <div id="common" class="content active">
            <table border="1" style="float:left;margin:1em auto 0;width:550px">
                <tbody>
                    <tr style="height:30px" id="ViewNameDisplayModeId">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">View名称</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">${fn:escapeXml(viewName)}</td>
                    </tr>
                    <tr style="height:30px" id="ViewNameRegisterModeId">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">View名称</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <form:select path="viewId">
                                <option value="" label="" />
                            <c:forEach varStatus="status" var="viewItem" items="${viewList}">
                                <option value="${fn:escapeXml(viewItem.id)}" label="${fn:escapeXml(viewItem.name)}" ${managementItemReminderDetailForm.viewId == viewItem.id ? 'selected' : ''} />
                            </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <tr style="height:30px">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">リマインダータイトル</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <form:input path="reminderTitle" style="width:400px;" type="text" maxlength="50" />
                        </td>
                    </tr>
                    <tr style="height:30px;">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">期間</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <form:select path="reminderTypeCd">
                            <c:forEach varStatus="status" var="reminderTypeItem" items="${reminderTypeList}">
                                <option value="${fn:escapeXml(reminderTypeItem.reminderTypeCd)}" label="${fn:escapeXml(reminderTypeItem.name)}" ${managementItemReminderDetailForm.reminderTypeCd == reminderTypeItem.reminderTypeCd ? 'selected' : ''} />
                            </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <c:forEach varStatus="reminderMessageStatus" var="reminderMessage" items="${managementItemReminderDetailForm.reminderMessageDtoList}">
                    <tr style="height:30px;" class="${reminderMessageStatus.index == 0 ? '' : 'reminderMessageNotFirst'}">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">${reminderMessageStatus.index == 0 ? '' : reminderMessageStatus.index == 1 ? '(再)' : '(再'.concat(reminderMessageStatus.index).concat(')')}メッセージ見出し</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <input name="reminderMessageDtoList[${reminderMessageStatus.index}].title" value="${fn:escapeXml(reminderMessage.title)}" style="width:400px;" type="text" maxlength="50" />
                        </td>
                    </tr>
                    <tr style="height:30px" class="${reminderMessageStatus.index == 0 ? '' : 'reminderMessageNotFirst'}">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">${reminderMessageStatus.index == 0 ? '' : reminderMessageStatus.index == 1 ? '(再)' : '(再'.concat(reminderMessageStatus.index).concat(')')}期間${reminderMessageStatus.index == 0 ? 'または月' : ''}</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <input type="radio" name="reminderMessageDtoList[${reminderMessageStatus.index}].sendType" id="sendType${reminderMessageStatus.index}_0" value="0" ${reminderMessage.sendType == '0' ? 'checked' : ''} ${reminderMessageStatus.index == 0 ? '' : 'style="visibility:hidden;"'} class="sendTypeRadio" />&nbsp;<label for="sendType${reminderMessageStatus.index}_0" ${reminderMessageStatus.index == 0 ? '' : 'style="visibility:hidden;"'}>期間</label>
                            <input type="text" name="reminderMessageDtoList[${reminderMessageStatus.index}].sendPeriod" value="${fn:escapeXml(reminderMessage.sendPeriod)}" style="width:30px;" maxlength="2" class="reminderMessagePeriodClass" /><span>日　</span>
                            <input type="radio" name="reminderMessageDtoList[${reminderMessageStatus.index}].sendType" id="sendType${reminderMessageStatus.index}_1" value="1" ${reminderMessage.sendType == '1' ? 'checked' : ''} ${reminderMessageStatus.index == 0 ? '' : 'style="visibility:hidden;"'} class="sendTypeRadio" />&nbsp;<label for="sendType${reminderMessageStatus.index}_1" ${reminderMessageStatus.index == 0 ? '' : 'style="visibility:hidden;"'}>特定月</label>
                            <select name="reminderMessageDtoList[${reminderMessageStatus.index}].sendMonth" ${reminderMessageStatus.index == 0 ? '' : 'style="visibility:hidden;"'} class="reminderMessageMonthClass">
                                <option value="" label="" ${'' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="1" label="1月" ${'1' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="2" label="2月" ${'2' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="3" label="3月" ${'3' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="4" label="4月" ${'4' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="5" label="5月" ${'5' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="6" label="6月" ${'6' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="7" label="7月" ${'7' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="8" label="8月" ${'8' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="9" label="9月" ${'9' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="10" label="10月" ${'10' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="11" label="11月" ${'11' == reminderMessage.sendMonth ? 'selected' : ''} />
                                <option value="12" label="12月" ${'12' == reminderMessage.sendMonth ? 'selected' : ''} />
                            </select>
                        </td>
                    </tr>
                    <tr style="height:30px" class="${reminderMessageStatus.index == 0 ? '' : 'reminderMessageNotFirst'}">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">${reminderMessageStatus.index == 0 ? '' : reminderMessageStatus.index == 1 ? '(再)' : '(再'.concat(reminderMessageStatus.index).concat(')')}メッセージ</th>
                        <td class="r250 left" style="padding-left: 10px;padding-right: 5px;color: black;background-color: white;">
                            <textarea name="reminderMessageDtoList[${reminderMessageStatus.index}].sendMessage" style="width : 400px" col="100" rows="3" maxlength="250">${fn:escapeXml(reminderMessage.sendMessage)}</textarea>
                        </td>
                    </tr>
                    </c:forEach>
                    <tr style="height:30px">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">通知</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <div>
                                <input type="checkbox" ${managementItemReminderDetailForm.notificationFlg == '1' ? 'checked' : ''} id="notificationFlg" value="1" name="notificationFlg" />
                                <label for="notificationFlg">通知する</label>
                            </div>
                        </td>
                    </tr>
                    <tr style="height:30px">
                        <th class="r150 left" style="padding-left: 10px;color: white;background-color: green;">疾病種別</th>
                        <td class="r250 left" style="padding-left: 10px;color: black;background-color: white;">
                            <div id="diseaseType"> 
                                <input type="checkbox" id="diseaseTypeCheckAll" /> 
                                <label for="diseaseTypeCheckAll">全て</label>
                                <c:forEach varStatus="status" var="diseaseItem" items="${diseaseTypeList}">
                                    <input type="checkbox" ${managementItemReminderDetailForm.diseaseTypeCd.contains(diseaseItem.diseaseTypeCd.toString()) ? 'checked' : ''} id="diseaseTypeCheck${status.index}" value="${fn:escapeXml(diseaseItem.diseaseTypeCd)}" name="diseaseTypeCd" />
                                    <label for="diseaseTypeCheck${status.index}">${fn:escapeXml(diseaseItem.name)}</label>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table> <!-- kokomade left contents -->

            <table id="itemListTable" border="1" style="float:right;margin:1em auto 0;width:300px;">
                <tr style="height: 30px; color: white; background-color: green;">
                    <th width="10%" style="border-right: none;"></td>
                    <th width="90%" style="text-align: left; border-left: none; border-right: none; padding-left: 3px;">項目名</td>
                </tr>
                <c:forEach varStatus="status" var="observationDefinitionItem" items="${observationDefinitionList}">
                <tr style="height: 30px; color: black; background-color: white;">
                    <td width="10%" style="border-right: none;">
                        <input type="checkbox" ${managementItemReminderDetailForm.observationDefinitionId.contains(observationDefinitionItem.observationDefinitionId) ? 'checked' : ''} id="observationDefinitionCheck${status.index}" value="${fn:escapeXml(observationDefinitionItem.observationDefinitionId)}" name="observationDefinitionId" style="float: right;" />
                    </td>
                    <td width="90%" style="text-align: left; border-left: none; border-right: none; padding-left: 3px;">
                        <label for="observationDefinitionCheck${status.index}">${fn:escapeXml(observationDefinitionItem.observationDefinitionName)}</label>
                    </td>
                </tr>
                </c:forEach>
            </table><!-- kokomade right contents -->
        </div>
    </div>
    <div style="clear:both;position:relative;margin:0 0 0 1em;height:15px;"></div>
<SCRIPT type="text/javascript">
    initPage();
</SCRIPT>
</form:form>
