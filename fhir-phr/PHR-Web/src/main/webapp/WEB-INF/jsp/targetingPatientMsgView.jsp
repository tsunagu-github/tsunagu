<%-- 
    Document   : targetingPatientMsgView
    Created on : 2016/08/31, 17:03:13
    Author     : KISNOTE011
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
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">
    <head>
        <META http-equiv="Content-Language" content="ja"> 
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Content-Script-Type" content="text/javascript" />
        <meta http-equiv="Content-Style-Type" content="text/css" />
        <title>AMED-PHR</title>
        <link href="css/import.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript">
        </script>
    </head>

    <body>
        <div style="text-align:left; margin:2em auto 0; width:900px; ">
<form:form modelAttribute="targetingPatientMsgViewForm" action="${action}">
    <form:hidden path="command" />
    <form:hidden path="formCd"/>
    <form:hidden path="formName"/>
    <div class="section" style="max-width:800px;">
         <div class="result_box" id="resultList1" style="display:block">
            <div><form:errors path="*" cssStyle="color:red; font-size:small"/></div>
            <div class="errorMsg"><span id="targetingPatientMsgViewForm.itemErrors" style="font-weight:normal;font-size:small;">${targetingPatientMsgViewForm.information}</span></div>
        </div>
    </div>
</form:form>
        </div>
    </body>
</html>