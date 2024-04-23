<%-- 
    Document   : alertPatientInfo
    Created on : 2016/09/14, 9:43:06
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
    
    <title>対象者情報</title>

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
</script>

<form:form modelAttribute="alertPatientInfoForm" name="alertPatientInfoForm" action="${action}">
    <form:hidden path="command"/>
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <input id="param1" name="param1" type="hidden" value=""/>
<body>
<div id="selectedPtInfo" style="display:'';">
		<table class="green-header">
    		<tbody>
			<tr>
				<th class="r100 center">PHR ID</th>
                                <td class="r150 center">${alertPatientInfoForm.phrid}</td>
			</tr>
			<tr>
				<th class="r100 center">氏名</th>
                                <td class="r150 center">${alertPatientInfoForm.name}</td>
			</tr>
			<tr>
				<th class="r100 center">カナ氏名</th>
                                <td class="r150 center">${alertPatientInfoForm.kananame}</td>
			</tr>
			<tr>
				<th class="r100 center">生年月日</th>
                                <td class="r150 center">${alertPatientInfoForm.birthday}</td>
			</tr>
			<tr>
				<th class="r100 center">性別</th>
                                <td class="r150 center">${alertPatientInfoForm.sex}</td>
			</tr>
			<tr>
				<th class="r100 center">住所</th>
				<td class="r150 center">
					${alertPatientInfoForm.address}
				</td>
			</tr>
			<tr>
				<th class="r100 center">電話番号</th>
                                <td class="r150 center">${alertPatientInfoForm.telnumber}</td>
			</tr>			
			</tbody>
		</table>
		<a href="javascript:closeMyself();" class="button button-blue" style="float:right;"><span>閉じる</span></a>
</div>
</body>
</form:form>

</html>