<%-- 
    Document   : specificMedicalCheakUpForm
    Created on : 2016/08/25, 14:31:10
    Author     : kis.o-note-002
--%>
<!DOCTYPE html>

        <script type="text/javascript">
            // ページ読み込み時、医療機関CDにフォーカス
            window.onload = function() {
                document.medicalSearch.medicalCd.focus();    
            };

            function search(){
                var target = document.getElementById('medicalSearchList');
                //target.style.display = 'block';
            }

            function submitSearch(command) {
                var from = document.forms[0];
//                from.target="_self";
                if (from.command != null) {
                        from.command.value = command;
                }
                from.submit();
                search();
            }
            
            function submitDelete(command) {
                document.getElementById("deleteButton").disabled="true";
            }

            function submitParam(command , param1) {
                var form = document.forms[0];
                form.target="_self";
                if (form.command != null) {
                        form.command.value = command;
                }
                if (form.param1 != null) {
                    form.param1.value = param1;
                }
                form.submit();
                search();
            }
        </script>
</head>
<body>
    <form:form modelAttribute="medicalSearchForm" name ="medicalSearchForm" action="${action}" enctype="multipart/form-data">
        <form:hidden path="clickedPage" />
        <form:hidden path="totalNum"/>
        <form:hidden path="currentPage"/>
        <form:hidden path="index" />
        <input id="command" name="command" type="hidden" value=""/>
        <input id="formCd" name="formCd" type="hidden" value=""/>
        <input id="formName" name="formName" type="hidden" value=""/>
        <input id="param1" name="param1" type="hidden" value=""/>
        
        <div class="headinglv01"><h1>医療機関管理画面<span>医療機関の一覧を表示します。</span></h1></div>

    <div class="search-section" style="position: relative">
	<table>
	 <tr>
            <td>
               <table class="medicine-body" style="width:600px;float:left;">
                <colgroup><col width="20%"/><col width="80%"/></colgroup>
                   <tbody>
                        <tr>
                            <th>医療機関CD</th>
                            <td class="r300" >
                                <form:input path="userIdStr" cssClass="userIdStr" id="medicalCd" name="medicalCd" value="${userIdStr}" size="50" maxlength="10" style="ime-mode:inactive;"/>
                            </td>
                        </tr>
                        <tr><th>医療機関名称</th>
                            <td class="r300" >
                                <form:input path="userNameStr" cssClass="userNameStr" id="medicalName" name="medicalName" value="${userNameStr}" size="50" maxlength="50" style="ime-mode:inactive;"/>                            
                            </td>
                        </tr>
                   </tbody>
               </table>
            </td>
            <div style="position: absolute;bottom: 0;right: 5em" >
                <a href= "javascript:submitForm('search');" class="button button-blue"><span>検索</span></a>
                <a href= "javascript:submitForm('insert');" class="button button-green"><span>新規作成</span></a>
            </div>
	 </tr>
	</table>
        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
    </div>

        <div style="clear:both;position:relative;margin:0 0 0 1px;height:5px;"></div>
        </div>
        <div>
            <table>
                <tr>
                    <form:errors path="*" cssStyle="color:red; font-size:small"/>
                </tr>
            </table>
        </div>
        <div style="width:800px; margin:auto">
            <div>
            <c:if test="${medicalSearchForm.prePage}">
                <a href="javascript:submitFormPaging('clickedPage',${medicalSearchForm.prePageNo});"><div class="paging"><span>..</span></div></a>
            </c:if>
            <c:forEach varStatus="status" var="i" items="${medicalSearchForm.page}">
    		<c:if test="${medicalSearchForm.clickedPage != i}">
                    <a href="javascript:submitFormPaging('clickedPage',${i});"><div class="paging"><span>${i}</span></div></a>
        	</c:if>
		<c:if test="${medicalSearchForm.clickedPage == i}">
                        <div class="pagingNow"><span>${i}</span></div>
		</c:if>
            </c:forEach>
            <c:if test="${medicalSearchForm.nextPage}">
                <a href="javascript:submitFormPaging('clickedPage',${medicalSearchForm.nextPageNo});"><div class="paging"><span>..</span></div></a>
            </c:if>
            <c:if test="${not empty medicalSearchList}">
                <div class="pagecount" style="float: right;"><span font-size:15px>検索件数：<fmt:formatNumber value="${medicalSearchForm.totalNum}" pattern="###,###,###" />件</span></div>    	
            </c:if>
            </div>
            <c:if test="${not empty medicalSearchList}">
            <table class="fmt03 margin-auto" style="width:800px">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>医療機関CD</th>
                        <th>医療機関名称</th>
                        <th>修正</th>
<!--
                        <th>削除</th>
-->
                    </tr>
                </thead>
                <body>
                    <c:forEach var="t" varStatus="status" items="${medicalSearchList}">
                        <tr>
                            <td class="r30 center">
                                <c:out value="${medicalSearchForm.clickedPage*20 - 20 + status.count}"/>
                            </td>
                            <td class="r180 center">
                                <c:out value="${t.getUserIdStr()}"/>
                            </td>
                            <td class="r180 center">
                                <c:out value="${t.getUserNameStr()}"/>
                            </td>
                            <td class="r90 center">
                                <div class="btn4 margin-auto">
                                    <a href= "javascript:submitParam('update','${t.getUserIdStr()}');">修正</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </body>
            </table>
            </c:if>
        </div>
    </form:form>
<!--
    <div id="footer"></div> 
-->
</body>

