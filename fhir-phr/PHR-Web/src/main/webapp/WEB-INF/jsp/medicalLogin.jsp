<%-- 
    Document   : insurerLogin
    Created on : 2016/08/23, 19:14:30
    Author     : KISNOTE011
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Language" content="ja"> 
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Script-Type" content="text/javascript">
        <meta http-equiv="Content-Style-Type" content="text/css">

        <title>AMED-PHR 医療機関ログイン</title>
        <link href="css/import.css" rel="stylesheet" type="text/css"/>
        <!--<link href="css/jquery.qtip.css" rel="stylesheet" type="text/css">-->
        <link href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css">
        <link href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css">
        <script src="js/common.js" type="text/javascript"></script>
        <script src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></script>
        <script src="js/jquery/jquery-ui.min.js" type="text/javascript"></script>
        <script src="js/jquery.datepicker/jquery.ui.datepicker-ja.js" type="text/javascript"></script>
        <script src="js/jquery.colorbox/jquery.colorbox-min.js" type="text/javascript"></script>
        <script type="text/javascript">
            window.onload = function() {
                document.medicalLoginForm.userId.focus();
                setCookie('SITE', '${login_kind}', '', '/');
            };
        </script>
    </head>
    <body>
        <div style="text-align:left; margin:0em auto 0; width:900px; ">
            <div class="headerinfo"></div>
            <div id="container">
                <div id="contents">
                    <form:form modelAttribute="medicalLoginForm" name ="medicalLoginForm" action="${action}">
                        <form:hidden path="command"/>
                        <form:hidden path="formCd"/>
                        <form:hidden path="formName"/>

                        <div class="section-title"><h3><fmt:message key="login.title"/></h3></div>
                        <div class="section-login">
                            <div class="tablestyle">
                                <table class="fmt01" style="margin:2em auto 0;width:650px">
                                    <colgroup><col style="width:30%" /><col style="width:70%" /></colgroup>
                                    <tr>
                                        <form:errors path="*" cssStyle="color:red; font-size:small"/>
                                    </tr>
                                    <tr>
                                        <th><span ><fmt:message key="login.medicalOrganizationCd"/></span></th>
                                        <td><form:input path="medicalOrganizationCd" cssClass="medicalOrganizationCd" maxLength="20" style="ime-mode:disabled;"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><span ><fmt:message key="login.loginId"/></span></th>
                                        <td><form:input path="userId" cssClass="personId" maxLength="20" style="ime-mode:disabled;"/>
                                        </td>
                                    </tr>
                                    <tr class="line">
                                        <th><span ><fmt:message key="login.password"/></span></th>
                                        <td><form:password path="password" cssClass="password" maxLength="20"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
   
                            <div class="btn_area clearfix" style="margin:1em auto 0;width:650px">
                                <div class="bt_clear btn3"><a href="javascript:clearFormAll();"><fmt:message key="login.clearButton"/></a></div>
                                <div class="bt_login btn3"><a href="javascript:submitForm('login');"><fmt:message key="login.loginButton"/></a></div>
                                <p class="title-attention">${info_text}<em>${info_mailadd}</em></p>
                                <div class="clear_message"></div><br /><br /><br />
                                <div class="login_message"></div>
                            </div>
                        </div>
                    </form:form>
                </div><!-- /contents -->
            </div><!-- /container -->

            <div id="footer">
            <!-- コピーライト -->
            </div><!-- /footer -->
        </div>
    </body>
</html>
