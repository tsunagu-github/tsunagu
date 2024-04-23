<%-- 
    Document   : laboratoryResultForm
    Created on : 2016/08/25, 14:31:10
    Author     : kis.o-note-002
--%>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
            // ページ読み込み時の処理
            window.onload = function() {
                document.laboratoryResultForm.file_button.focus(); 

            };

            function download(){
                var target = document.getElementById('laboratoryResultForm');
                target.style.display = '';
            }

            
            // 登録処理
            function insertButtonClick(){
                document.getElementById("resultArea").textContent="";
                var fileName = new String(document.getElementById("fileCsv").value);
                var type = fileName.split('.');
                if(fileName.length===0){       // ファイル未選択の時
                    document.getElementById("resultArea").textContent="ファイルを選択して下さい。";
                    //return false;
                } else if(type[type.length-1].toLowerCase()!=='zip'){   // xml or zipファイル以外の時
                    document.getElementById("resultArea").textContent="zip形式以外のファイルは読み込めません。";    
                    //return false;
                } else {
                    document.getElementById("resultArea").textContent="";
                    jConfirm("検査結果の登録処理を開始します。","登録処理実施確認",function(r){
                        if(r){
                            var title = "検査結果の登録処理中です。";
                            waitMethod(title);
                            submitForm("upload");
                        }
                    });
                }
            }

            // ダウンロード処理
            function downloadButtonClick(){
                jConfirm("ダウンロード処理を開始します。","登録処理実施確認",function(r){
                    if(r){
                        submitForm("download");
                    }
                });
            }

            // ダウンロード処理
            function updateButtonClick(){
                jConfirm("特定健診の更新処理を行いますが、更新したデータは復元できませんがよろしいですか？","更新処理実施確認",function(r){
                    if(r){
                        var title = "特定健診の更新処理中です。";
                        waitMethod(title);
                        submitForm("update",$("#param1").val());
                    }
                });
            }
    </script>
</head>
<body>
    <form:form modelAttribute="laboratoryResultForm" name ="laboratoryResultForm" action="${action}" enctype="multipart/form-data">
        <form:hidden path="command"/>
        <form:hidden path="formCd"/>
        <form:hidden path="formName"/>
        <form:hidden path="param1"/>
        
        <div class="headinglv01"><h1>検査結果登録画面<span>検査結果の登録を行います。</span></h1></div>

        <div class="search-section">
            <input type="hidden" id="downloadBtnCtl" value="${downloadBtnCtl}">   <!-- ダウンロードボタンのコントロール -->
            
            <div id="waitEntry" style="display:none;">
                <br>
                <table width="280" height="150" border="1" bordercolor="#808080">
                <tr>
                    <th align="center">
                        <span id="waitEntryTitle">特定健診の登録中です</span><br>
                        <img src="img/wait.gif">
                    </th>
                </tr>
                </table>
                <br>
            </div>

            <table class="" style="margin:0;auto:0;width:650px">
                <tbody>
                    <tr>
                        <td>
                            <!-- ファイル選択ダイアログの表示対応 -->
                            <input type="file" id="file" name="file" style="display:none;" onchange="$('#fileCsv').val($('#file')[0].files[0].name)">
                            <form:input path="fileCsv" cssClass="fileCsv" size="50" />
                            <input type="button" id="file_button"value="参照.." onClick="$('#file').click();"> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="button button-blue" style="float:right;"><a href="javascript:insertButtonClick();"><span>登録</span></a></div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="uploadStatusInfo" >
            <!-- 登録結果エリア start-->
            <div class="scroll" style="height:300px;">
                <table id="resultArea">
                    <tr>
                        <td>
                            <c:forEach var="t" items="${laboratoryResultForm.insertResulrArea}">
                                <c:out value="${t}"/>
                                <br>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
                <c:if test="${not empty laboratoryResultForm.observationEntryDto}">
                <c:if test="${laboratoryResultForm.observationEntryDto[0].updateFlg==false}">
                <table id="selectArea" class="fmt03 margin-auto">
                    <thead>
                        <tr id="selectArea_head">
                            <th></th>
                            <th>PHR_ID</th>
                            <th>健診日</th>
                            <th>ファイル</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" varStatus="status" items="${laboratoryResultForm.observationEntryDto}">
                            <c:if test="${dto.sameFlg==true}">
                            <tr>
                                <td class="r30 center"><form:checkbox path="observationEntryDto[${status.index}].updateFlg" value="0"></form:checkbox></td>
                                <td class="r60 left" id="${dto.observationEventEntity.PHR_ID}" name="${dto.observationEventEntity.PHR_ID}">${dto.observationEventEntity.PHR_ID}</td>
                                <td class="r60 left" id="${dto.examinationDate}" name="${dto.examinationDate}">${dto.examinationDate}</td>
                                <td class="r60 left" id="${dto.samePath}" name="${dto.samePath}">${dto.samePath}</td>
                            </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
                </c:if>
                </c:if>
            </div>
 
            <!-- 登録結果エリア end -->
            <c:if test="${laboratoryResultForm.updateFlg}">
                <div id="upbtn" class="button button-orange" style="float:right;"><a href="javascript:updateButtonClick();"><span>更新</span></a></div>
            </c:if>

            <c:if test="${laboratoryResultForm.downloadBtnCtl}">
                <div id="dlbtn" class="button button-blue" style="float:right;"><a href="javascript:downloadButtonClick();"><span>ダウンロード</span></a></div>
            </c:if>

        </div>
    </form:form>
    <div id="footer"></div> <!-- フッター（コピーライト） -->
</body>
<script type="text/javascript">
    function initPage(){
    }
    
    initPage();
</script>            
</html>
