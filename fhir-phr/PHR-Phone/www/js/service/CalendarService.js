//カレンダー作成
phrService.factory('calendarService', function(){
    logger.debug("CalendarService Start");

   return { 
    getheader:function() {
        var header = ["日", "月", "火", "水", "木", "金", "土"];
        logger.debug(header);
        return header;
    },

    getcalendar:function(wantDate) {
        logger.debug("getCalendar");
        var date = wantDate;
        // 年を取得
        var year = date.getFullYear();        
        // 月を取得
        var month = date.getMonth();
        // 今日の日を取得
        //var today = date.getDate();
        
        var week = [0, 1, 2, 3, 4, 5, 6];
        var monthdays = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        // うるう年の計算
        if (((year % 4) == 0 && (year % 100) != 0) || (year % 400) == 0){
            monthdays[1] = 29;
        }

        var carendar = [];   // カレンダーデータ
        var day;             // ループ用
        var index = 1;       // 日付

        for(var i = 0; i < 6; i++) {
            var tableLine = [];   // 1週間分のデータ
            var endflg = false;

            for(var wknum=0; wknum<7; wknum++){    
                date.setDate(index); 
                day = date.getDay();
                // 1段目：　前月の分を空白にする
                if (i == 0 && day > wknum) {
                    tableLine[wknum] = {dd: "", color: "none"};
                }
                // 最終日の段： 次の月の分を空白にする
                else if (index > monthdays[month]) {
                    if(i==5 && wknum==0){
                        endflg = true;
                        break;
                    }
                    tableLine[week[wknum]] = {dd: "" };//, color: "none"};
                    index++;
                }
                // 日にちを設定する
                else {
                    tableLine[week[day]] = {dd: date.getDate(), dosageMedicines:[], dosageMedicineicon:false};
                    // tableLine[week[day]] = {dd: date.getDate(), color: date.getDate() == today ? "today" : "none",dosageMedicines:[],dosageMedicineicon:false};
                    index++;
                }
            }
            carendar.push(tableLine);
            if(endflg){
                carendar.pop();
            }
        }      
    return carendar;

    },
    setMedicine:function(calendarList,mlist,showDate) {
        logger.debug("おくすりセット開始");
        
        // 年を取得
        var year = showDate.getFullYear();
        // 月を取得
        var month = showDate.getMonth();
        
        var monthMedicines = [];
        //対象月の薬剤データに絞ったリストを作成
        for(var i=0;i<mlist.length;i++){
            
            var mFullDate = new Date(mlist[i].dosageDate);
            var mYear = mFullDate.getFullYear();
            var mMonth = mFullDate.getMonth();
            if(year==mYear && month==mMonth){
                monthMedicines.push(mlist[i]);
            }
        }
        if(monthMedicines.length > 0){
            for(var i = 0; i < calendarList.length; i++){
                var wk = new Object();
                wk = calendarList[i];
                for(var ml=0;ml<monthMedicines.length;ml++){
                    var dosageMedicine = monthMedicines[ml];
                    var mDate = new Date(dosageMedicine.dosageDate);
                    for(var k = 0;k<7;k++){
                        if(wk[k].dd == mDate.getDate()){
                           wk[k].dosageMedicineicon = true;
                           wk[k].dosageMedicines.push(dosageMedicine);
                        }

                    }
                }
                calendarList.splice(i,1,wk);
            }
        }
        return calendarList;
    }    
};
})
