<%-- 
    Document   : noticeNewCreate
    Created on : 2016/09/05, 12:06:25
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
    
    <title>新規お知らせ作成</title>

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
    function closeWin(){
        window.opener = "myself";
        window.close();
    }
    
    function checkWin(){
        var subject = document.forms.noticeNewCreateForm.subject.value;
        var text = document.forms.noticeNewCreateForm.text.value;
        var errMSGS = document.getElementById('errMSGS');
        var errMSGT = document.getElementById('errMSGT');
        var commitMSG = document.getElementById('commitMSG');
        if (subject==="" || subject===null) {
            if (text==="" || text===null) {
                errMSGS.style.display = '';
                errMSGT.style.display = '';
                commitMSG.style.display = 'none';
                resizeTo(500,540);
            } else {
                errMSGS.style.display = '';
                errMSGT.style.display = 'none';
                commitMSG.style.display = 'none';
                resizeTo(500,520);
            }
        } else {
            if (text==="" || text===null) {
                errMSGS.style.display = 'none';
                errMSGT.style.display = '';
                commitMSG.style.display = 'none';
                resizeTo(500,520);
            } else {
                document.forms.noticeNewCreateForm.subject.readOnly = true;
                document.forms.noticeNewCreateForm.text.readOnly = true;

                document.getElementById("check").style.display = "none";
                document.getElementById("close").style.display = "none";
                document.getElementById("insert").style.display = "";
                document.getElementById("return").style.display = "";

                commitMSG.style.display = '';
                errMSGS.style.display = 'none';
                errMSGT.style.display = 'none';
                resizeTo(500,520);
            }
        }
    }
    
    function returnWin(){
        document.forms.noticeNewCreateForm.subject.disabled = "";
        document.forms.noticeNewCreateForm.text.disabled = "";
        
        var subject = document.forms.noticeNewCreateForm.subject.value;
        var text = document.forms.noticeNewCreateForm.text.value;
        var errMSGT = document.getElementById('errMSGT');
        var commitMSG = document.getElementById('commitMSG');
        
        document.getElementById("check").style.display = "";
        document.getElementById("close").style.display = "";
        document.getElementById("insert").style.display = "none";
        document.getElementById("return").style.display = "none";
        
        commitMSG.style.display = 'none';
        errMSGT.style.display = 'none';
        
        resizeTo(500,480);
    }
    
    function insertWin(command) {
        var from = document.forms[0];
        from.target="_self";
        if (from.command != null) {
            from.command.value = command;
        }
        from.submit();
        
//      jAlert('メッセージを送信しました。','送信完了',function(){
//          window.close();
//      }); 
    }

    function complite(){
        var flg = ${noticeNewCreateForm.status};
        var conf = document.getElementById("newcreate");
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
    
<form:form modelAttribute="noticeNewCreateForm" name="noticeNewCreateForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
<body>
<div id="newcreate" style="" class="insert-section">
<HR>
${noticeNewCreateForm.insurerName}<br/>
${noticeNewCreateForm.accountName}
<HR>
<form>
<table class="green-header">
	<tr>
		<th class="r80 center">タイトル</th>
		<td class="r300 center"><form:input path="subject" cssClass="subject" id="subject" name="subject" style="width:280px;" type="text" maxlength="50"/></td>
	</tr>	
</table>
<HR>
<table class="green-header" >
	<tr>
		<th class="r80 center">内容</th>
	</tr>
	<tr>
		<td class="r380 center" colspan="2"><form:textarea path="text" Class="text" cols="45" rows="10" name="text" id="text"/></textarea>
		</td>
	</tr>
</table>

<div id="errMSGS" style="padding: 0.5em;display:none;">
    <span style="color:red;">題名を入力してください。</span>
</div>
<div id="errMSGT" style="padding: 0.5em;display:none;">
    <span style="color:red;">本文を入力してください。</span>
</div>
<div id="commitMSG" style="padding: 0.5em;display:none;">
    <span>この内容で送信しますか？</span>
</div>

<a href="javascript:checkWin();" class="button button-blue" name="check" id="check" style="display:'';"><span>確認</span></a>
<a href="javascript:closeWin();" class="button button-blue" name="close" id="close" style="display:'';"><span>閉じる</span></a>
<a href="javascript:insertWin('insert');" class="button button-blue" name="insert" id="insert" style="display:none;"><span>送信</span></a>
<a href="javascript:returnWin();" class="button button-blue" name="return" id="return" style="display:none;"><span>戻る</span></a>

</form>

</div>
<div id="complite" style="display:none">
    </br>
    </br>
    送信しました。
    </br>
    </br>
    <a href="javascript:closeWin();" class="button button-blue" name="close" id="close" style="display: '';"><span>閉じる</span></a>
</div>
</body>
</form:form>
<script>
     initPage();
</script>
</html>
