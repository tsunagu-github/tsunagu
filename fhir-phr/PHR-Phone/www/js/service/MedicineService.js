//お薬情報取得
phrService.factory('medicineListService', function($http, $q,patientManagementService){

    return {
        /**
         * おくすり情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getList: function (successAction,baseDate) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetMedicineList";
            //sendObject.action = "mock-medicine";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.baseDate = phrJs.formatDate(baseDate, "yyyy/MM/dd");
            webAction.setSuccessAction(successAction);
 
            webAction.action($http, $q, sendObject);
        }
    
    };
})


//編集・登録用お薬フォーマット作成
phrService.factory('editMedicineSettingService', function(){
    logger.debug("medicineservice Start");

    //var editMedicine = null;
    return {
        /**
         * 新規調剤情報を返却する
         * @returns 
         */
    geteditMedicine: function(item){
             var editMedicine = new Object();
            //var itm =new Object();
           // if(item=="new"){
                editMedicine.medType = "";
                editMedicine.isNew = true;
                var today = new Date();
                editMedicine.dosageId = "";
                editMedicine.seq = "";
                editMedicine.dosageDate=today;
                editMedicine.pharmacy="";
                editMedicine.pharmacistName="";
                editMedicine.medicalOrganization="";
                editMedicine.departmentName="";
                editMedicine.doctorName="";
                editMedicine.dosageRemark="";
                editMedicine.medicines= [{
                    recordCreatorType:"2",
                    recipeNo:"",
                    medicineSeq:"",
                    medicineName:"",
                    dosageForm:"1",
                    usageName:"",
                    dosageQuantity:"",
                    dosageUnit:"日分",
                    amount:"",
                    unitName:"",
                    additional:"",
                    dosageForm_v:"true",
                    quantityTitle:"数量（日数）",
                    amountTitle:"１日量（用量）" ,
                    isdelete:false
                }];
                editMedicine.nonpre=[{
                    medicineName:"",
                    medicineSeq:"",
                    startDate:"",
                    endDate:"",
                    isdelete:false
                }];

 //           }

            logger.debug(editMedicine);
            return editMedicine;
        },
        /**
         * 調剤情報を設定する(新規対応作成中)
         * @param 
         * @returns 
         */
        setEditMedicine_new: function(item) {
             var editMedicine = new Object();
            //dosage.type = value.type;//市販薬(nonpre)か調剤薬(presc)か
                editMedicine.dosageId = item.dosageId;
                editMedicine.seq = item.dosageDataList[0].seq;
                editMedicine.medType = "";
                editMedicine.isNew = false;
                var dateStr = item.dosageDate;
                var dateStr2 = Date.parse(dateStr);
                var dateDate = new Date(dateStr2);

                editMedicine.dosageDate=dateDate;
                editMedicine.pharmacy=item.pharmacy;
                editMedicine.pharmacistName=item.pharmacistName;
                editMedicine.medicalOrganization=item.dosageDataList[0].medicalOrganization;
                editMedicine.departmentName=item.dosageDataList[0].departmentName;
                editMedicine.doctorName=item.dosageDataList[0].doctorName;
                editMedicine.dosageRemark=item.dosageRemark[0];
                editMedicine.medicines= [];
                editMedicine.nonpre=[];
                
                    for(var i=0;i<item.dosageDataList[0].recipeList.length;i++){
                        for(var k=0;k<item.dosageDataList[0].recipeList[i].medicines.length;k++){
//                            var typeflg=item.medicineList[i].medicineType;
                            
                            var typeflg=item.dosageDataList[0].medicineType;
                            logger.debug("typeflg:" + typeflg);
                            if(typeflg=="2"){//"2"){//調剤薬
                                var dfKey= item.dosageDataList[0].recipeList[i].dosageForm;
                                var dfview;
                                var qTitle;
                                var aTitle;
                                if(dfKey == "1"){//内服
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "2"){//内滴
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "3"){//頓服                                  
                                    qTitle="投与回数";
                                    dfview=true;
                                    aTitle="１回量（用量）";
                                }else if(dfKey == "4"){//注射
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "5"){//外用
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "6"){//浸煎
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "7"){//湯薬
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "9"){//材料
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "10"){//その他
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                };
                                var quantStr = item.dosageDataList[0].recipeList[i].dosageQuantity;
                                var quantInt = quantStr *1;
                                var amountStr = item.dosageDataList[0].recipeList[i].medicines[k].amount
                                var amountInt = amountStr*1;
                               var addMedicine = {
                                    recordCreatorType:item.recordCreatorType,
                                    recipeNo:item.dosageDataList[0].recipeList[i].recipeNo,
                                    medicineSeq:item.dosageDataList[0].recipeList[i].medicines[k].medicineSeq,
                                    medicineName:item.dosageDataList[0].recipeList[i].medicines[k].medicineName,
                                    usageName:item.dosageDataList[0].recipeList[i].usageName,
                                    dosageQuantity:quantInt,
                                    dosageUnit:item.dosageDataList[0].recipeList[i].dosageUnit,
                                    amount:amountInt,
                                    unitName:item.dosageDataList[0].recipeList[i].medicines[k].unitName,
                                    additional:item.dosageDataList[0].recipeList[i].medicines[k].additional,
                                    dosageForm:dfKey,
                                    dosageForm_v:dfview,
                                    quantityTitle:qTitle,
                                    amountTitle:aTitle,
                                    isdelete:false
                                };
                                editMedicine.medicines.push(addMedicine);
                            }else{//1：市販薬
                                var sdateStr = item.dosageDataList[0].recipeList[i].medicines[k].startDate;
                                var edateStr = item.dosageDataList[0].recipeList[i].medicines[k].endDate;
                                var sdateStr2 = Date.parse(sdateStr);
                                var edateStr2 = Date.parse(edateStr);
                                var sdateDate = new Date(sdateStr2);
                                var edateDate = new Date(edateStr2);
                                var addMedicine = {
                                        medicineName:item.dosageDataList[0].recipeList[i].medicines[k].medicineName,
                                        medicineSeq:item.dosageDataList[0].recipeList[i].medicines[k].medicineSeq,
                                        startDate:sdateDate,
                                        endDate:edateDate,
                                        isdelete:false
                                    };
                                editMedicine.nonpre.push(addMedicine);
                            }
                        }
                    }
            logger.debug(editMedicine);
            return editMedicine;
        },
        /**
         * 調剤情報を設定する
         * @param 
         * @returns 
         */
        setEditMedicine: function(item) {
             var editMedicine = new Object();
            //dosage.type = value.type;//市販薬(nonpre)か調剤薬(presc)か

                editMedicine.dosageId = item.dosageId;
                editMedicine.seq = item.seq;
                editMedicine.medType = "";
                editMedicine.isNew = false;
                var dateStr = item.dosageDate;
                var dateStr2 = Date.parse(dateStr);
                var dateDate = new Date(dateStr2);

                editMedicine.dosageDate=dateDate;
                editMedicine.pharmacy=item.pharmacy;
                editMedicine.pharmacistName=item.pharmacistName;
                editMedicine.medicalOrganization=item.medicalOrganization;
                editMedicine.departmentName=item.departmentName;
                editMedicine.doctorName=item.doctorName;
                editMedicine.dosageRemark=item.dosageRemark;
                editMedicine.medicines= [];
                editMedicine.nonpre=[];
                
                    for(var i=0;i<item.medicineList.length;i++){
                        for(var k=0;k<item.medicineList[i].medicines.length;k++){
//                            var typeflg=item.medicineList[i].medicineType;
                            
                            var typeflg=item.medicineList[i].medicineType;
                            logger.debug("typeflg:" + typeflg);
                            if(typeflg=="2"){//"2"){//調剤薬
                                var dfKey= item.medicineList[i].dosageForm;
                                var dfview;
                                var qTitle;
                                var aTitle;
                                if(dfKey == "1"){//内服
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "2"){//内滴
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "3"){//頓服                                  
                                    qTitle="投与回数";
                                    dfview=true;
                                    aTitle="１回量（用量）";
                                }else if(dfKey == "4"){//注射
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "5"){//外用
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "6"){//浸煎
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "7"){//湯薬
                                    qTitle="数量（日数）";
                                    dfview=true;
                                    aTitle="１日量（用量）";
                                }else if(dfKey == "9"){//材料
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                }else if(dfKey == "10"){//その他
                                    qTitle="";
                                    dfview=false;
                                    aTitle="全量（用量）";
                                };
                                var quantStr = item.medicineList[i].dosageQuantity;
                                var quantInt = quantStr *1;
                                var amountStr = item.medicineList[i].medicines[k].amount
                                var amountInt = amountStr*1;
                                var addtext = null;                  
                               var addMedicine = {
                                    recordCreatorType:item.medicineList[i].recordCreatorType,
                                    recipeNo:item.medicineList[i].recipeNo,
                                    medicineSeq:item.medicineList[i].medicines[k].medicineSeq,
                                    medicineName:item.medicineList[i].medicines[k].medicineName,
                                    usageName:item.medicineList[i].usageName,
                                    dosageQuantity:quantInt,
                                    dosageUnit:item.medicineList[i].dosageUnit,
                                    amount:amountInt,
                                    unitName:item.medicineList[i].medicines[k].unitName,
                                    additional:item.medicineList[i].medicines[k].additional,
                                    dosageForm:dfKey,
                                    dosageForm_v:dfview,
                                    quantityTitle:qTitle,
                                    amountTitle:aTitle,
                                    isdelete:false
                                };
                                editMedicine.medicines.push(addMedicine);
                            }else{//1：市販薬
                                var sdateStr = item.medicineList[i].medicines[k].startDate;
                                var edateStr = item.medicineList[i].medicines[k].endDate;
                                var sdateStr2 = Date.parse(sdateStr);
                                var edateStr2 = Date.parse(edateStr);
                                var sdateDate = new Date(sdateStr2);
                                var edateDate = new Date(edateStr2);
                                var addMedicine = {
                                        medicineName:item.medicineList[i].medicines[k].medicineName,
                                        medicineSeq:item.medicineList[i].medicines[k].medicineSeq,
                                        startDate:sdateDate,
                                        endDate:edateDate,
                                        isdelete:false
                                    };
                                editMedicine.nonpre.push(addMedicine);
                            }
                        }
                    }
            logger.debug(editMedicine);
            return editMedicine;
        },
        /**
         * 新規薬剤データを取得する。
         * @returns 
         */
        addNewMedicine: function() {
            var addMedicine = {
                    recordCreatorType:"2",
                    recipeNo:"",
                    medicineSeq:"",
                    medicineName:"",
                    dosageForm:"1",
                    usageName:"",
                    dosageQuantity:"",
                    dosageUnit:"日分",
                    amount:"",
                    unitName:"",
                    additional:"",
                    dosageForm_v:"true",
                    quantityTitle:"数量（日数）",
                    amountTitle:"１日量（用量）",
                    isdelete:false
                };  
            return addMedicine;
        },        
        addNewNonpre: function() {
            var addMedicine = {
                    medicineName:"",
                    medicineSeq:"",
                    startDate:"",
                    endDate:"",
                    isdelete:false
                };            
            return addMedicine;
        }
        
    };
})

//お薬情報削除
phrService.factory('medicineDeleteService', function($http, $q,patientManagementService){

    return {
        /**
         * おくすり情報を返却する
         * @returns {services_L22.patient|Object}
         */
        doDelete: function (successAction,dosageId,seq) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "DeleteMedicine";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.dosageId = dosageId;
//            sendObject.seq = seq;
            logger.debug(sendObject);
            webAction.setSuccessAction(successAction);
 
            webAction.action($http, $q, sendObject);
        }
    };
})
//お薬患者入力情報登録
phrService.factory('medicinePatientInputService', function($http, $q,patientManagementService){

    return {
        /**
         * 患者メモ登録
         * @returns {services_L22.patient|Object}
         */
        doPatientInput: function (successAction,dosageId,patientInputList) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "MedicinePatientInput";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.dosageId = dosageId;
            sendObject.patientInputList = patientInputList;
            logger.debug(sendObject);
            webAction.setSuccessAction(successAction);
 
            webAction.action($http, $q, sendObject);
        }        
    
    };
})

//お薬情報登録
phrService.factory('medicineUpdateService', function($http, $q,patientManagementService){

    return {
        /**
         * おくすり情報を返却する
         * @returns {services_L22.patient|Object}
         */
        doUpdate: function (successAction,entryItem) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "UpdateMedicine";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.entryItem = entryItem;
            logger.debug(sendObject.entryItem);
            webAction.setSuccessAction(successAction);
 
            webAction.action($http, $q, sendObject);
        }
    
    };
})

//お薬情報インポート
phrService.factory('medicineImportService', function($http, $q,patientManagementService){

    return {
        /**
         * おくすり情報を返却する
         * @returns {services_L22.patient|Object}
         */
        doUpdate: function (successAction,importText) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "ImportMedicine";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.importText = importText;
            webAction.setSuccessAction(successAction);
 
            webAction.action($http, $q, sendObject);
        }
    
    };
})