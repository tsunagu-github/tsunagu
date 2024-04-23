phrService.factory('lookupData', function ($http, $q) {
    return {
        /**
         * 性別CDのMap
         */
        sexCodeMap: function () {
            return {"M": "男性", "F": "女性"};
        },
        
        /**
         * 都道府県のList
         * @returns {Array}
         */
        prefectureList: function () {
            return [
                {cd: "01", name: "北海道"},
                {cd: "02", name: "青森県"},
                {cd: "03", name: "岩手県"},
                {cd: "04", name: "宮城県"},
                {cd: "05", name: "秋田県"},
                {cd: "06", name: "山形県"},
                {cd: "07", name: "福島県"},
                {cd: "08", name: "茨城県"},
                {cd: "09", name: "栃木県"},
                {cd: "10", name: "群馬県"},
                {cd: "11", name: "埼玉県"},
                {cd: "12", name: "千葉県"},
                {cd: "13", name: "東京都"},
                {cd: "14", name: "神奈川県"},
                {cd: "15", name: "新潟県"},
                {cd: "16", name: "富山県"},
                {cd: "17", name: "石川県"},
                {cd: "18", name: "福井県"},
                {cd: "19", name: "山梨県"},
                {cd: "20", name: "長野県"},
                {cd: "21", name: "岐阜県"},
                {cd: "22", name: "静岡県"},
                {cd: "23", name: "愛知県"},
                {cd: "24", name: "三重県"},
                {cd: "25", name: "滋賀県"},
                {cd: "26", name: "京都府"},
                {cd: "27", name: "大阪府"},
                {cd: "28", name: "兵庫県"},
                {cd: "29", name: "奈良県"},
                {cd: "30", name: "和歌山県"},
                {cd: "31", name: "鳥取県"},
                {cd: "32", name: "島根県"},
                {cd: "33", name: "岡山県"},
                {cd: "34", name: "広島県"},
                {cd: "35", name: "山口県"},
                {cd: "36", name: "徳島県"},
                {cd: "37", name: "香川県"},
                {cd: "38", name: "愛媛県"},
                {cd: "39", name: "高知県"},
                {cd: "40", name: "福岡県"},
                {cd: "41", name: "佐賀県"},
                {cd: "42", name: "長崎県"},
                {cd: "43", name: "熊本県"},
                {cd: "44", name: "大分県"},
                {cd: "45", name: "宮崎県"},
                {cd: "46", name: "鹿児島県"},
                {cd: "47", name: "沖縄県"}
            ];
        },

        /**
         * 剤型CDのList
         */
        dosageFormCDList: function () {
            return[
                {cd: "1", name: "内服"},
                {cd: "5", name: "外用"},
                {cd: "3", name: "頓服"},
                {cd: "4", name: "注射"},
                {cd: "2", name: "内滴"},
                {cd: "6", name: "浸煎"},
                {cd: "7", name: "湯薬"},
                {cd: "9", name: "材料"},
                {cd: "10", name: "その他"}
            ];
        },
        
        /**
         * 単位CDのList
         */
        mUnitCDList: function () {
            return [
                {cd: "錠", name: "錠"},
                {cd: "g", name: "g"},
                {cd: "ml", name: "ml"},
                {cd: "カプセル", name: "カプセル"},
                {cd: "包", name: "包"},
                {cd: "袋", name: "袋"},
                {cd: "筒", name: "筒"},
                {cd: "個", name: "個"},
                {cd: "本", name: "本"},
                {cd: "枚", name: "枚"},
                {cd: "シート", name: "シート"},
                {cd: "ブリスター", name: "ブリスター"},
                {cd: "単位", name: "単位"},
                {cd: "その他", name: "その他"}
            ];
        },
        /**
         * 検査結果値背景色MAP
         */
        clinicalTesValueColorMap: function () {
            var valueColorMap = {};
            valueColorMap["0"] = "#ccffcc";
            valueColorMap["1"] = "#ffff99";
            valueColorMap["2"] = "#ffd700";
            valueColorMap["3"] = "#ff0000";
            valueColorMap["4"] = "#ffffff";
            valueColorMap["9"] = "#ffffff";
            return valueColorMap;
        },
        /**
         * 検査結果日付前景色MAP
         */
        clinicalTesDateColorMap: function () {
            var dateColorMap = {};
            dateColorMap["false"] = "#000000";;
            dateColorMap["true"] = "#ff0000";
            return dateColorMap;
        },
        
        /**
         * 健診データ取得用　入力種別コード
         */
        inspectionTypeCDList: function () {
            return[
                {cd: "3", name: "健診"},
                {cd: "4", name: "問診"},
                {cd: "5", name: "診察"}
            ];
        },
        
        
    };
})
        ;