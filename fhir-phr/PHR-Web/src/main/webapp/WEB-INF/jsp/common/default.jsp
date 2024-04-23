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

        <c:if test="${not empty accountEntity}">
            <title>AMED-PHR 保険者View</title>
        </c:if>
        <c:if test="${empty accountEntity}">
            <title>AMED-PHR 医療機関View</title>
        </c:if>

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
        <script src="js/waitMethod.js" type="text/javascript" charset="UTF-8"></script>
        <script language="JavaScript">
            function setFocus() {
                var from = document.forms[0];
                var ctrls = from.elements;
                var ss = "";
                for (var i = 0; i < ctrls.length; i++) {
                    if (ctrls[i].type === "text" ||
                            ctrls[i].type === "radio" ||
                            ctrls[i].type === "checkbox" ||
                            ctrls[i].type === "file" ||
                            ctrls[i].type === "select-one" ||
                            ctrls[i].type === "select-multiple" ||
                            ctrls[i].type === "password") {
                        ctrls[i].focus();
                        break;
                    }
                }
            }

            function headerclose(ctrlid,id) {
                if(ctrlid.innerText === "▲"){
                    ctrlid.innerText = "▼";
                    id.style.display = "none";
                }else{
                    ctrlid.innerText = "▲";
                    id.style.display = "block";
                }
                setCookie('HEADER_CLOSE', ctrlid.innerText, '', '');
            }

            function headerReload() {
                var headStatus=getCookie('HEADER_CLOSE');
                if(headStatus){
                    if(headStatus === "▼"){
                        infobox.style.display = "none";
                        headerctrl.innerText = "▼";
                    }else{
                        infobox.style.display = "block";
                    }
                }else{
                    infobox.style.display = "block";
                }
            }
        </script>
    </head>

    <body>
        <div style="text-align:left; margin:0em auto 0; width:900px; ">
            <c:if test="${not empty accountEntity}">
                <div class="headerinfo">
                    <div class="topdiv" style="padding:5px;">
                        <a id="headerctrl" href="javascript:headerclose(headerctrl,infobox);" style="text-decoration:none;">▲</a>
                        <span style="float:right;"><c:out value="${accountEntity.name}"/> 様</span>
                    </div>
                    <div class="infobox" id="infobox">
                        <a href="./logout" class="button button-blue" style="float:left;margin:0 0 0 50px;"><span>ログアウト</span></a>
                        <a href="./passwordChange" class="button button-green" style="float:right;"><span>パスワード変更</span></a></br>
                        <span style="float:right;">最終ログイン日時：${lastLoginStr}</span>
                    </div>
                </div><!-- / header end -->
            </c:if>
            <c:if test="${empty accountEntity}">
                <div class="headerinfo">
                    <div class="topdiv" style="padding:5px;">
                        <a id="headerctrl" href="javascript:headerclose(headerctrl,infobox);" style="text-decoration:none;">▲</a>
                        <span style="float:right;"><c:out value="${medicalOrganizationEntity.medicalOrganizationName}"/> </span>
                    </div>
                    <div class="infobox" id="infobox">
                        <a href="./logout" class="button button-blue" style="float:left;margin:0 0 0 50px;"><span>ログアウト</span></a>
                        <a href="./passwordChange" class="button button-green" style="float:right;"><span>パスワード変更</span></a></br>
                    </div>

                </div><!-- / header end -->
            </c:if>
            <div id="container">
                <div id="contents">
                    <!-- コンテンツ ここから -->
                    <c:if test="${accountEntity.initPassword==false}">
                    <div class="menubox">
                        <div class="leftmenu">
                            <c:if test="${accountEntity.accoutTypeCd==2}">
                                <a href="./messageList" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                    <span>メッセージ一覧</span>
                                </a>
                            </c:if>
                            <a href="./alertSearch" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>アラート一覧</span>
                            </a>
                            <a href="./notice" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>お知らせ</span>
                            </a>
                            <c:if test="${accountEntity.accoutTypeCd==2}">
                                <a href="./targetingPatient" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                    <span>対象者情報</span>
                                </a>
                                <a href="./managementItemView" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                    <span>管理項目情報</span>
                                </a>
                                <a href="./managementItemSetting" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                    <span>管理項目設定</span>
                                </a>
                                <a href="./managementItemReminder" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                    <span>リマインダ設定</span>
                                </a>
                            </c:if>
                            <c:if test="${accountEntity.accoutTypeCd==1}">
                            <a href="./userManage" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>ユーザ管理</span>
                            </a>
                            <a href="./medicalSearch" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;"> 
                                <span>医療機関管理</span>
                            </a>
                            </c:if>
                            <a href="./specificMedicalCheakUpForm" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>特定健診登録</span>
                            </a>
                            <a href="./laboratoryResultUpload" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>検査結果登録</span>
                            </a>
                            <c:if test="${accountEntity.oneTimePassAuth==true}">
                            <a href="./oneTimePass" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>参照用パス発行</span>
                            </a>
                            </c:if>                            
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${empty accountEntity}">
                    <div class="menubox">
                        <div class="leftmenu">
                            <a href="./alertSearch" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>アラート一覧</span>
                            </a>
                            
                            <!-- 同意の有無で表示/非表示を制御 
                            <c:if test="">
                            </c:if>
                             -->
                            <a href="./targetingPatientList" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>対象者情報</span>
                            </a>
                            
                            <a href="./managementItemView" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>管理項目情報</span>
                            </a>
                            <a href="./managementItemSetting" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>管理項目設定</span>
                            </a>
                            <a href="./managementItemReminder" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>リマインダ設定</span>
                            </a>
                            <!-- 
                            <a href="./specificMedicalCheakUpForm" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>特定健診登録</span>
                            </a>
                             -->
                            <a href="./medicalKensaEntry" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>検査結果登録</span>
                            </a>
                            <a href="./studyItemInformation" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>同意内容登録</span>
                            </a>
                            <a href="./consentNotification" class="button button-menu" style="margin:2px 0px 0px 0px;width:122px;">
                                <span>同意通知一覧</span>
                            </a>
                        </div>
                    </div>
                    </c:if>
                    <tiles:insertAttribute name="body" />
                </div><!-- / contents end -->
                <div id="footer"><font style="float:right;font-weight:bold;color:#002182;">BuildVer:${buildVer}&nbsp;&nbsp;</font></div>
            </div><!-- / container end -->
        </div>
    <script language="JavaScript">
        setFocus();
        headerReload();
    </script>
    </body>
</html>
