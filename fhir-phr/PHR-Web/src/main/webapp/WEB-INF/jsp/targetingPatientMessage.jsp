<%-- 
    Document   : messageSelect
    Created on : 2016/08/26, 10:51:48
    Author     : kis.o-note-003
--%>
<html>
<head>
<META http-equiv="Content-Language" content="ja"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />

<link href="css/import.css" rel="stylesheet" type="text/css" />
<LINK href="css/jquery.qtip.css" rel="stylesheet" type="text/css">
<LINK href="css/jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"> 
<LINK href="css/jquery.colorbox/colorbox.css" rel="stylesheet" type="text/css"> 
<script src="js/common.js" type="text/javascript"></script>
<SCRIPT src="js/jquery/jquery-1.12.3.min.js" type="text/javascript"></SCRIPT> 
<SCRIPT src="js/jquery/jquery-ui.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.ui.datepicker-ja.js" type="text/javascript"></SCRIPT>
<SCRIPT src="js/jquery/jquery.colorbox-min.js" type="text/javascript"></SCRIPT>
</head>

<script language="javascript" type="text/javascript">
$(function() {
    $(".inline").colorbox({
    inline:true,
    rel:'group',
    loop:false,
    maxWidth:'100%',
    maxHeight:'100%',
    opacity: 0.0
  });
});

    function showPopup2() {
      var target = document.getElementById("selecthospital");
		target.style.position = "absolute";
      //if (!event) var event = window.event;
      //if (!event.pageX) px = event.clientX + document.body.scrollRight; else px = event.pageX;
      //if (!event.pageY) py = event.clientY + document.body.scrollTop; else py = event.pageY;
		
		var img_x = document.documentElement.clientWidth;
		var img_y = document.documentElement.clientHeight;
		
		//表示位置を中央に調節（ブラウザ画面サイズの半分－画像サイズの半分）//*7
		img_x = (img_x / 10) ;//- (img_w / 2);
		img_y = (img_y / 20) ;//- (img_h / 2);

		//画面のスクロール量を加算
		img_x += document.documentElement.scrollLeft || document.body.scrollLeft;
		img_y += document.documentElement.scrollTop || document.body.scrollTop;

        target.style.left = img_x + "px";//px-300 + "px";
        target.style.top = img_y + "px";//py+5 + "px";
        target.style.display = "block";
    }
    
    function hidePopup() {
      target = document.getElementById("selecthospital");
      target.style.display = "none";
    }
                  
    function messageClose(){
        window.opener = "myself";
        window.close();
    }
    
    function showHospital(){
    	var hospitals = document.getElementsByName("hospitallist");
    	var target = document.getElementById("targetHospital");
    	var hospitallist="";
    	for(var i=0,l=hospitals.length; l>i; i++){
    		if(hospitals[i].checked){
    			hospitallist = hospitallist+ hospitalMap.get(hospitals[i].value) +"<br/>";
    		}
    	}
    	target.innerHTML = hospitallist;
    	hidePopup();
    }

    function submitForm2(command){

        var hospitals = document.getElementsByName("hospitallist");
    	var hospitallist="";
    	for(var i=0,l=hospitals.length; l>i; i++){
    		if(hospitals[i].checked){
    			hospitallist = hospitallist+ hospitals[i].value +",";
    		}
    	}
        
        submitForm(command , hospitallist);
    }
 </script>

<form:form modelAttribute="targetingPatientMessageForm" name="targetingPatientMessageForm" action="${action}">
<form:hidden path="command"/>
<form:hidden path="formCd"/>
<form:hidden path="formName"/>
<form:hidden path="param1"/>
<body>
<div class="message-list" style="height:590px;">
    <div class="scroll" style="width:500px;height:600px;">

        <!-- メッセージ返信画面 -->
	<div id="returnwindow">
            <p>メッセージ</p>
            <HR/>
             <p>${medicalMessageReplayForm.name}<br/></p>
            <HR/>
            <table class="green-header">
                <c:if test="${typeflg}">
                <tr>
                    <th class="r100 center">宛先（PHR ID）</th>
                    <td class="r300 center">${targetingPatientMessageForm.phrid}</td>
                </tr>	
                </c:if>
                <c:if test="${!typeflg}">
                <tr>
                    <th class="r100 center">宛先（患者ID）</th>
                    <td class="r300 center">${targetingPatientMessageForm.phrid}</td>
                </tr>	
                </c:if>

                <tr>
                    <th class="r100 center">氏名</th>
                    <td class="r300 center">${targetingPatientMessageForm.name}</td>
		</tr>	
		</table>
		<table class="green-header">
                    <c:if test="${targetingPatientMessageForm.typeflg}">
                    <tr>
                        <th class="r100 center">宛先（医療機関）</th>
                        <td class="r300 center">
                            <div id="targetHospital">
                            </div>
                            <div class="btn4 margin-auto" style="float: right;"><a href="javascript:showPopup2();" name="hospital" id="hospital" style="display:'';">選択</a>
                            </div>
			</td>
                    </tr>
                    </c:if>
                    <c:if test="${!targetingPatientMessageForm.typeflg}" >
                    <tr>
                        <th></th>
                        <td>
                            <form:checkbox path="insureflg" value="true" />保険者に送る
                        </td>
                    </tr>
                    </c:if>
                    <tr>
                        <th class="r100 center">タイトル</th>
                        <td class="r300 center">
                            <form:input name="subject" id="subject" path="subject" Class="subject" type="text" maxlength="50" style="width:300px;" value="" />
                        </td>
                    </tr>
		</table>
  		<HR/>
                <tr>
                    <form:errors path="*" cssStyle="color:red; font-size:small"/>
                </tr>
        	<table class="green-header" >
                    <tr>
                        <th class="r80 center">内容</th>
                    </tr>
                    <tr>
                        <td class="r380 center" colspan="2">
                            <form:textarea name="bodyText" id="bodyText" path="bodytext" Class="bodyText" cols="45" rows="10"/>
                        </td>
                    </tr>
		</table>
		<a href="javascript:messageClose();" class="button button-blue" name="close" id="close" style="display: '';"><span>閉じる</span></a>
                <a href="javascript:submitForm2('confirm');" class="button button-orange" name="resist" id="resist" ><span>確認</span></a>
            </div>
        </div>
    </div>
    <div id="selecthospital" style="border: 1px solid #c0c0c0;background-color:#FFFFFF;display: none;">
        <p style="color:#00FFFF;font-weight: bold;">医療機関選択</p>
        <table class="fmt03" style="margin:2em auto 1em;width:450px;" >
            <thead>
            <tr>
                <th></th><th>医療機関コード</th><th>医療機関名称</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach varStatus="status" var="medical" items="${targetingPatientMessageForm.medicalList}">
                <tr>
                    <td class="r50 center"><input id="${medical.medicalOrganizationCd}" name="hospitallist" type="checkbox" value="${medical.medicalOrganizationCd}"/></td>
                    <td class="r150 center">${medical.medicalOrganizationCd}</td>
                    <td class="r200 center">${medical.medicalOrganizationName}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <table style="float:right;">
            <tr>
                <td><a href="javascript:showHospital();" class="button button-blue"><span>選択</span></a></td>
                <td><a href="javascript:hidePopup();" class="button button-blue" ><span>閉じる</span></a></td>
            </tr>
        </table>
    </div>
                        
</body>
</form:form>
<script>
    var hospitalMap = new Map();

    <c:forEach varStatus="status" var="medical" items="${targetingPatientMessageForm.medicalList}">
        hospitalMap.set( "${medical.medicalOrganizationCd}" , "${medical.medicalOrganizationName}" );
    </c:forEach>

    var targethospital = [];
    <c:forEach varStatus="status" var="hospital" items="${targetingPatientMessageForm.hospitallist}">
        targethospital.push( "${hospital.medicalOrganizationCd}");
    </c:forEach>
    
    function initPage(){

    	var target = document.getElementById("targetHospital");
        var hospitallist = "";
        if(targethospital.length > 0){
            for(var i=0; i < targethospital.length; i++){
                hospitallist = hospitallist+ hospitalMap.get(targethospital[i]) +"<br/>";
                var hospital = document.getElementById(targethospital[i]);
                hospital.checked = true;
            }
        }else{
            hospitallist = "指定なし";
        }
    	target.innerHTML = hospitallist;
     }
     
    initPage();
</script>
</html>