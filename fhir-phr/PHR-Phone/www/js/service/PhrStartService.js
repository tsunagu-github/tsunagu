phrService.factory('phrStartService', function ($http, $q, lookupData, patientManagementService) {

    var takeOver = null;
    var rules = null;
    var patient = null;

    return {
        createTakeOver: function () {
            if (takeOver === null) {
                takeOver = new Object();
                takeOver.phrId = "";
                takeOver.takeOverCode = "";
                takeOver.takeOverPassword = "";
            }
            return takeOver;
        },
        getTakeOver: function ( successAction ) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "EntryTakeOverCode";
            sendObject.phrId = takeOver.phrId;
            sendObject.takeOverPassword = takeOver.takeOverPassword;
            sendObject.takeOverCode = takeOver.takeOverCode;

            logger.debug( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);

        },
        setTakeOver: function (phrId, takeOverCode, takeOverPassword ) {
            if (phrId !== null) {
                takeOver.phrId = phrId;
            }
            if (takeOverCode !== null) {
                takeOver.takeOverCode = takeOverCode;
            }
            if (takeOverPassword !== null) {
                takeOver.takeOverPassword = takeOverPassword;
            }
        },
        getRules: function () {
            if (rules === null) {
                rules = new Object();

                rules.message0 = (function() {/*
TSUNAGU PHR Phoneアプリケーション利用規約

本利用規約は、国立大学法人九州大学（以下、「当大学」といいます。）が「ユースケース・ベースのPHRサービスによるOpen FHIRと電子カルテの連携を目指すクラウド型医療連携プラットフォーム構築研究」（以下、「本研究」といいます。）のために提供する「TSUNAGU PHR」と称するアプリケーション（以下、「本アプリケーション」といいます。）の利用条件を定めるものです。ユーザーは、本利用規約に従い、本アプリケーションを利用するものとします。
本アプリケーションとは、本研究において携帯端末で各種健康データを自己管理できる仕組みを提供するプログラムをいいます。

第1条（ユーザー）
1.本利用規約は、本アプリケーションを自己の端末にダウンロードした者（以下、「ユーザー」といいます。）に適用されます。

第２条 (同意)
1.ユーザーは、本アプリケーションを自己の端末にダウンロードした時点で、本利用規約に同意したものとみなされます。本利用規約にご同意いただけない場合、ユーザーは、本アプリケーションをご利用いただくことはできません。
2.当大学は、本利用規約に同意したユーザー（ユーザが未成年者である場合には、親権者など法定代理人の同意を得た上で本利用規約に同意する必要があります。）に対して、本アプリケーションをユーザーの端末上においてのみ使用することのできる、非独占的かつ譲渡不能の本アプリケーションの使用を許諾します（以下、「本使用許諾」といいます。）。
3.本使用許諾は、ユーザーが本利用規約に従い本アプリケーションを利用するための権利の実施を許諾するものであり、本アプリケーション及び本アプリケーションにより提供される一切のコンテンツにかかる著作権を含む知的財産権等について、権利の譲渡及び実施許諾等を意味するものではありません。

第3条 (利用料)
1.本アプリケーションの利用料は無償とします。

第4条（本アプリケーションの内容及び本利用規約などの変更）
1.当大学は、ユーザーの承諾を得ることなく、いつでも、本アプリケーションの内容、名称並びに本利用規約の内容を変更することができるものとします。なお、本利用規約の内容の変更がなされた場合、本アプリケーションの利用に関して生じる一切の事項は、当該利用時点の本利用規約の内容を適用して解釈されるものとします。
2.当大学は、前項に定める変更を行う場合、本アプリケーション提供サイトへの掲載、その他当大学が適当と判断する方法にてユーザーに告知するものとします。
3.前項に基づき、変更を告知した日から当大学が定める期間（定めがない場合は告知の日から1週間）以降も引き続き本アプリケーションを利用するユーザーは、当該変更に同意したものと看做されます。

第5条（利用環境の整備）
1.ユーザーは、本アプリケーションを利用するために必要なあらゆる機器、ソフトウエア及び通信手段を自己の責任と費用において準備するものとします。
2.当大学は前項に定めるユーザーの利用環境について一切関与せず、また一切の責任を負いません。

第6条（個人情報）
1.ユーザーが当大学へ本アプリケーションに関連した問い合わせ等の連絡を行うことを必要とする場合、本アプリケーション提供サイト上において指定する連絡先に、当大学の指定する方法にて連絡するものとします。当大学が当該問い合わせ対応を行う際に、ユーザーから受領した電子メールアドレスを含む個人情報について、当大学は、「九州大学医系地区部局観察研究倫理審査員会（許可番号　22073-02）」に従い、管理します。

第7条 (情報取得)
1.ユーザーは、本アプリケーションが次の各号に定める情報を取得すること及び「同意説明文書、同意書（インフォームド・コンセント）」に記載されている事項に異議がないことについてあらかじめ承諾の上、本アプリケーションを使用するものとします。
(1)利用者属性（氏名、カナ氏名、性別、生年月日、年齢、郵便番号、都道府県、住所・建物（任意）、電話番号、メールアドレス、職員コード）
(2)病院情報（病名、来院日、薬剤情報、検体検査結果情報、診療情報提供書、退院サマリ）
(3)マイナポータルから連携した特定健診情報
(4)PHRアプリで収集する情報（身長、体重、血圧、血糖、脳卒中患者主観状況報告情報、脳卒中問診情報、動的同意情報）
(5)COVID-19関連情報（PCR等検査証明、ワクチン接種証明書）
(6)本アプリケーションのダウンロード履歴及び利用履歴
2.また、ユーザーは、当大学が、前項の各号の情報を次の各号に定める目的で使用することに同意します。
(1)九州大学医系地区部局観察研究倫理審査員会（許可番号　22073-02）
(2)電子カルテと個人のスマートフォンアプリケーションであるPHRの連携・相互運用性・データ保存性の確保・有効性・ユーザビリティの向上。
(3)疾患特異的ユースケースと疾患非特異ユースケースの機能による、個人の健康情報の管理。
(4)動的同意取得機能による、本研究への参加や本アプリケーションの利用について動的に同意と拒否の登録。
(5)九州大学医系地区部局観察研究倫理審査（許可番号　22073-02）に基づき、収集した情報の調査、分析資料としての利用。

第8条（ユーザーの責任）
1.ユーザーは、自己の責任において本アプリケーションを利用するものとします。
2.ユーザーが本アプリケーションの利用により第三者に対して損害を与えた場合、ユーザーは自己の責任でこれを解決し、当大学にいかなる損害も生ぜしめないものとします。

第9条（禁止事項）
1.ユーザーは、本アプリケーションの利用にあたって、以下の各号の行為又はそのおそれがある行為を行ってはならないものとします。
(1)当大学もしくは第三者の著作権を含む一切の知的財産権を侵害する行為。
(2)当大学もしくは第三者の財産、プライバシーもしくは肖像権を侵害する行為。
(3)当大学もしくは第三者を不当に差別もしくは誹謗中傷し、第三者への不当な差別を助長し、又はその名誉もしくは信用を毀損する行為。
(4)本アプリケーションを複製、修正、変更、翻案、改変し、またはリバースエンジニアリング、逆コンパイルまたは逆アセンブルする行為
(5)本アプリケーションを違法な目的で利用する行為。
(6)誤情報や有害なコンピュータプログラム等を送信または計算する行為。
(7)本アプリケーション、その他当大学の事業運営に支障をきたすおそれのある行為。
(8)当大学の事前承認を得ることなく、本アプリケーションを通じたまたは本アプリケーションに関連する営利を目的とする行為。
(9)法令、本利用規約若しくは公序良俗に反する行為又はこれに類する行為。
(10)その他前各号に該当するおそれのある行為又はこれに類する行為。
(11)本アプリケーションの利用に際し、本利用規約とは別に第三者の規約への同意が必要である場合には、当該第三者の規約に違反する行為。
(12)その他、当大学が不適切と判断する行為。
2.ユーザーが前項に定める禁止事項に違反したと認めた場合、当大学は、当該違反ユーザーに本アプリケーション利用の拒否、停止、本利用規約の解除等の当大学が必要と判断した措置をとる場合があります。なお、当大学は、本項に定める措置によりユーザーに損害が生じた場合でも、当大学に故意また重過失がある場合を除き、一切の責任を負わないものとします。また、ユーザーの禁止事項違反により、当大学が損害を被った場合、ユーザーは当大学に対して当該損害を賠償するものとします。

第10条（知的財産権等）
1.本アプリケーションを構成する素材（文字・写真・映像・音声等）に関する知的財産権及び所有権等の一切の権利は、当大学又は当該権利を保有する第三者に帰属します。

第11条（本アプリケーションの提供の中断）
1.当大学は、以下の各号に該当する場合、本アプリケーションの全部又は一部の提供を中断することができます。当大学は、当該事由に起因してユーザー又は第三者に損害が発生した場合であっても、一切の責任を負わないものとします。
(1)定期的又は緊急に本アプリケーション提供のためのコンピュータシステムの保守・点検を行う場合。
(2)火災・停電、天災地変等の非常事態により本アプリケーションの提供が不能又は困難となった場合。
(3)戦争、内乱、暴動、騒擾、労働争議等により、本アプリケーションの提供が不能又は困難になった場合。
(4)本アプリケーション提供のためのコンピュータシステムに不要又はコンピュータウイスルの感染などが発生し、もしくは第三者からの不正アクセスが行われたことにより、本アプリケーションを提供できない場合。
(5)法令などに基づく措置により本アプリケーションが提供できない場合。
(6)その他、当大学がやむを得ないと判断した場合。

第12条（本アプリケーションの提供の終了）
1.当大学は、ユーザーに対して何らの責任を負うことなくして、いつでも本アプリケーションの提供を終了することができるものとします。

第13条（権利譲渡）
1.ユーザーは、あらかじめ当大学の書面による承諾がない限り、本利用規約上の地位および本利用規約に基づく権利または義務の全部または一部を、第三者に譲渡してはならないものとします。
2.当大学は、本アプリケーションの全部または一部を、当大学の裁量により、第三者に譲渡することができ、その場合、譲渡された権利の範囲内で、ユーザーのアカウントを含む、本アプリケーションに係るユーザーの権利が譲渡先に移転するものとします。

第14条（免責）
1.当大学は、以下の事項に関し、その一切責任を問いません。
(1)ユーザーが本アプリケーションの利用により第三者に対して損害を与えた場合
(2)ユーザーが本アプリケーションを利用できなかった場合、または本アプリケーションを利用に関し当大学に責のない事由により損害を被った場合
2.明示、黙示を問わず、ユーザーまたは第三者に対して、本アプリケーションの内容及び本アプリケーションを通じて得られる情報の完全性、正確性、確実性、有用性、暇疵がないこと、誤りがないこと等について、当大学はいかなる責任も負いません。
3.本アプリケーションに掲載されている情報等を利用することにより、ユーザーの端末等に損害が生じた場合（ウイルス感染した場合等を含む。）について、当大学に故意また重過失がある場合を除き、当大学はいかなる責任も負いません。
4.本アプリケーション利用に際して使用するユーザーID及びパスワードの盗用、不正使用そたの事情により生じた損害について、当大学はいかなる責任も負いません。
5.当大学は、本アプリケーションが全ての情報端末に対応していることを保証するものではありません。また、本アプリケーションの利用に供する情報端末のＯＳのバージョンアップ等に伴い、本アプリケーションの動作に不具合が生じる可能性があることについて、ユーザーは、あらかじめ了承するものとします。なお、かかる不具合が生じた場合に当大学が行うプログラムの修正等により、当該不具合が解消されることを保証することはありません。
6.ユーザーは、AppStore、GooglePlay等のサービスストアの利用規約および運用方針の変更等に伴い、本アプリケーションの一部又は全部の利用が制限される可能性があることをあらかじめ了承するものとします。
7.当大学は、本アプリケーションを利用したことにより直接的または間接的にユーザーに発生した損害について、いかなる責任も負いません。当大学の過失による債務不履行または不法行為によりユーザーに生じた損害の賠償は、現実に発生した直接かつ通常の損害に限るものとします。

第15条（本使用許諾の効力）
1.ユーザーが本利用規約の条項のいずれかに違反した場合は、当大学は、自己の判断に基づき、何らの催告することなく本使用許諾を解除し終了させることができるものとします。
2.本使用許諾が終了した場合には、ユーザーは、いかなる理由においても本アプリケーションを使用することはできません。ユーザーは、本アプリケーションの使用を直ちに中止するとともに、自己の端末上の本アプリケーションを速やかに削除するものとします。

第16条（分離可能性）
1.本利用規約のいずれかの条項が、消費者契約法その他の法令等により、無効又は執行不能とされた場合でもあっても、本利用規約の他の条項は、継続して完全な効力を有するものとします。

第17条（準拠法及び裁判管轄）
1.本利用規約の成立、効力、解釈及び履行については日本法に準拠するものとします。
2.本アプリケーションの利用並びに本利用規約に関する紛争のうち、ユーザーと当大学間で訴訟の必要が生じた場合、福岡地方裁判所を第一審の専属的合意管轄裁判所とします。

2022年9月26日　施行

                */}).toString().match(/\/\*([^]*)\*\//)[1];
            }
            return rules;
        },
        getPatient: function () {
            if (patient === null) {
                patient = patientManagementService.getViewPatient();
            }
            return patient;
        },
        createPatient :function (patient) {
            patientManagementService.setPatient(patient);
            patientManagementService.createPatient();
        },
        setPatientPhrId: function (value) {
            patient.phrId = value;
        },
        setPatientZipCode: function (patient) {
            patientManagementService.setZipCode(patient);
        },
        setPatientViewPrefecture: function (patient) {
            patientManagementService.setPrefectureName(patient);
        },
        setBirthDate: function (patient) {
            patientManagementService.setBirthDate(patient);
        }
    };

});
