<%-- 
    Document   : noticeDetail
    Created on : 2016/08/31, 10:35:02
    Author     : KISO-NOTE-005
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<fmt:setLocale value="${pageContext.request.locale.language}" />
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">
<head>
    <META http-equiv="Content-Language" content="ja"> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Script-Type" content="text/javascript" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    
    <title>お知らせ詳細画面</title>

    <link href="css/import.css" rel="stylesheet" type="text/css" />
    <LINK href="css/jquery.qtip.css" rel="stylesheet" type="text/css"/>
    <LINK href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"/> 
    <LINK href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css"/> 
    <link href="js/libs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
    <script src="js/common.js" type="text/javascript"></script>
    <SCRIPT src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT> 
    <SCRIPT src="js/jquery/jquery-ui.min.js" type="text/javascript"></SCRIPT>
    <SCRIPT src="js/jquery/jquery.ui.datepicker-ja.js" type="text/javascript"></SCRIPT>
    <SCRIPT src="js/jquery/jquery.colorbox-min.js" type="text/javascript"></SCRIPT>
    <script src="js/libs/jquery.alerts.js" type="text/javascript" charset="UTF-8"></script>
</head>
    
<script type="text/javascript">
    function closeMyself(){
        window.opener = "myself";
        window.close();
    }
    
    function deleteWin(command, param1) {
        jConfirm('削除したお知らせは戻すことができません。よろしいですか？', '削除確認', function(r) {
            if(r!==null && r===true){
                var from = document.forms[0];
                from.target="_self";
                if (from.command != null) {
                    from.command.value = command;
                }
                if (from.param1 != null) {
                    from.param1.value = param1;
                }
                from.submit();
            } else {
                return false;
            }
        });
    }
    
    function complite(){
        var flg = ${noticeDetailForm.status};
        var conf = document.getElementById("detail");
        var comp = document.getElementById("complite");
        if(flg){
            conf.style.display = "none";
            comp.style.display =  "";
        }
    }

    function initPage() {
        complite();
    }
</script>

<form:form modelAttribute="noticeDetailForm" name="noticeDetailForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
<body>
<div id="detail" style="" class="delete-section">
<HR>
${noticeDetailForm.insurerName}<br/>
${noticeDetailForm.accountName}
<HR>
<form>
<table class="green-header">
	<tr>
		<th class="r80 center">タイトル</th>
		<td class="r300 center" readOnly=true>${noticeDetailForm.noticeDetailList.subject}</td>
	</tr>	
</table>
<HR>
<table class="green-header" >
	<tr>
		<th class="r80 center">内容</th>
		
	</tr>
	<tr>
		<td class="r380 left" colspan="2">
                    <textarea cols=45 rows=10 readOnly=true Align="left">${noticeDetailForm.noticeDetailList.bodyText}</textarea>
		</td>
	</tr>
</table>
<a href= "javascript:deleteWin('delete','${noticeDetailForm.noticeDetailList.communicationId}');" class="button button-blue" name="delete" id="delete" ><span>削除</span></a>
<a href= "javascript:closeMyself();" class="button button-blue"><span>閉じる</span></a>

</form>

</div>
<div id="complite" style="display:none">
    </br>
    </br>
    削除しました。
    </br>
    </br>
    <a href="javascript:closeMyself();" class="button button-blue" name="close" id="close" style="display: '';"><span>閉じる</span></a>
</div>
</body>
</form:form>
<script>
     initPage();
</script>
</html>