<!DOCTYPE　html>
<form:form modelAttribute="utilizationConsentForm" name ="utilizationConsentForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <form:hidden path="param1"/>
    <form:hidden path="param2"/>
    <form:hidden path="clickedPage"/>
    <script type="text/javascript">
    // 送信対象チェック数
    var checkCount = 0;

    function checkAll() {
        let element = document.getElementById('checkAllBox');
        let checkAllFlg = element.checked;
        let checkBox = document.getElementsByName("checkFlg");
        checkCount = 0;

        if(checkBox.length > 0){
            for(i = 0; i < checkBox.length; i++) {
                checkBox[i].checked = checkAllFlg;
                if(checkAllFlg === true){
                    checkCount = checkCount + 1;
                }
            }
        }
        this.changeSendBtnColor();
    }

    function sendReminder(command) {
        let sendUnregisteredAnswerIndex = '';
        let checkBox = document.getElementsByName("checkFlg");
        if(checkBox.length > 0){
            for(i = 0; i < checkBox.length; i++) {
                if(checkBox[i].checked === true){
                    if(sendUnregisteredAnswerIndex != ''){
                        sendUnregisteredAnswerIndex += ',' + checkBox[i].value;
                    } else {
                        sendUnregisteredAnswerIndex = checkBox[i].value;
                    }
                }
            }
            if(sendUnregisteredAnswerIndex !== ''){
                jConfirm('同意通知を送信します。よろしいですか？', '確認', function(r) {
                    if(r!==null && r===true){
                        var form = document.forms[0];
                        form.target="_self";
                        if (form.command != null) {
                            form.command.value = command;
                        }
                        if (form.param1 != null) {
                            form.param1.value = sendUnregisteredAnswerIndex;
                        }
                        form.submit();
                    } else {
                        return false;
                    }
                });
                return;
            }
        }
        alert('同意通知送信対象にチェックしてください。')
    }

    function checkCheckBoox(checked) {
        if(checked === true){
            checkCount = checkCount + 1;
        } else {
            checkCount = checkCount - 1;
        }
        this.changeSendBtnColor();
    };

    function changeSendBtnColor() {
        var target = document.getElementById("sendBtn")
        if(checkCount > 0){
            target.href = "javascript:sendReminder('sendReminder');";
            target.classList.remove("button-grey");
            target.classList.add("button-blue");
        } else {
            target.removeAttribute('href')
            target.classList.remove("button-blue");
            target.classList.add("button-grey");
        }
    };
    
    function isCheck() {
        var arr_checkBoxes = document.getElementsByClassName("checkBoxes");
        var count = 0;
        for (var i = 0; i < arr_checkBoxes.length; i++) {
            if (arr_checkBoxes[i].checked) {
                count++;
            }
        }
        if (count > 0) {
            return true;
        } else {
            window.alert("状態を1つ以上選択してください。");
            return false;
        }
    }
    </script>
    <br/>
    <div class="headinglv01"><h1>同意通知一覧画面<span>同意通知を送信する患者を選択してください。</span></h1></div>
    <div style="text-align:left; margin:0em auto 0; width:900px;">
        <div class="search-section" style="position: relative">
            <table style="width:100%;">
                <tr>
                    <td>
                        <table class="fmt02" style="width:450px;">
                        <colgroup><col width="25%"/><col width="65%"/></colgroup> 
                            <tbody>
                                <tr>
                                    <th align="left">研究名</th>
                                    <td style="width: 100px;text-align: center">
                                        <select name="studyName">
                                            <c:forEach varStatus="staus" var="Item" items="${utilizationConsentForm.studyInformationEntityList}">
                                                    <option value="${Item.studyName}" label="${Item.studyName}" ${utilizationConsentForm.studyName == Item.studyName ? 'selected' : ''} />
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th align="left">状態</th>
                                    <td>
                                        <form:checkbox class="checkBoxes" name="responseStatusList" path="responseStatusList" value="3"/><span>&nbsp;未回答&nbsp;</span>
                                        <form:checkbox class="checkBoxes" name="responseStatusList" path="responseStatusList" value="1"/><span>&nbsp;同意&nbsp;</span>
                                        <form:checkbox class="checkBoxes" name="responseStatusList" path="responseStatusList" value="2"/><span>&nbsp;非同意&nbsp;</span>
                                        <form:checkbox class="checkBoxes" name="responseStatusList" path="responseStatusList" value="4"/><span>&nbsp;保留&nbsp;</span>
                                    </td>
                                </tr>
                             </tbody>
                         </table>
                    </td>
                    <td>
                        <a href="javascript:submitForm('search');" class="button button-blue" onClick="return isCheck()"><span>検索</span></a>
                    </td>
                </tr>
            </table>
            <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
        </div>
<div id="patientList" style="padding: 2em;display:'';">
    <tr>
        <form:errors path="*" cssStyle="color:red; font-size:small"/>
    </tr>
    
    <c:if test="${not empty utilizationConsentForm.patientInfo}">
        <table class="fmt03 margin-auto" style="width:850px;margin-top: 10px;">
            <thead>
                <tr>
                    <th>PHR-ID</th>
                    <th>氏名(漢字)</th>
                    <th>生年月日</th>
                    <th>研究名</th>
                    <th>同意種別</th>
                    <th>状態</th>
                    <th>通知日</th>
                    <th class="r50 center"><input type="checkbox" id="checkAllBox" name="checkAllBox" onchange="javascript:checkAll()"/>&nbsp;通知</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach varStatus="status" var="item" items="${utilizationConsentForm.patientInfo}">
                    <tr>
                        <td class="r70 center" >${item.PHR_ID}</td>
                        <td class="r80 center" >${item.familyName} ${item.givenName}</td>
                        <td class="r50 center">${item.birthStr}</td>
                        <td class="r300 center">${item.studyName}</td>
                        <td class="r51 center">
                            <c:if test="${item.consentType == '1'}">オプトイン</c:if>
                            <c:if test="${item.consentType == '2'}">オプトアウト</c:if>
                        </td>
                        <td class="r48 center">
                            <c:if test="${item.responseStatus == '1'}">同意</c:if>
                            <c:if test="${item.responseStatus == '2'}">非同意</c:if>
                            <c:if test="${item.responseStatus == '3'}">未回答</c:if>
                            <c:if test="${item.responseStatus == '4'}">保留</c:if>
                        </td>
                        <td class="r50 center">${item.notificationStr}</td>
                        <td class="r20 center" >
                                    <input type="checkbox" id=checkFlg name="checkFlg" path="checkFlg" value="${item.PHR_ID}&${item.studyId}&${item.subjectId}" onchange="javascript:checkCheckBoox(this.checked)" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
     </c:if>
</div>
        <div style="text-align:right;background-color:white;position: relative">
            <a tabindex="-1" class="button button-grey" id="sendBtn"><span>送信</span></a>
        </div>
    </div>
</form:form>
