package kis.inc.ssmix2storagemaker.Enums;

public enum ErrorIdEnum {
	/** E001 SearchType指定が対応した数値ではありません。 */
	No_SearchType
	/** E002 患者IDの指定がありません。 */
	,No_PatientId
	/** E003 指定されたデータ種別に対応していません */
	,No_DataType
	/** E004 標準化ストレージのルートパスが指定されていません。[{0}] */
	,No_STDPath
	/** E005 拡張ストレージのルートパスが指定されていません。[{0}] */
	,No_EXTPath
	/** E006 基準日が指定されていません。[{0}] */
	,No_BaseDate
	/** E007 基準日はYYYYMMDD形式で指定して下さい。[{0}] */
	,BaseDateFormat
	/** E008 検索方向が指定されていません。[{0}] */
	,No_SearchDirection
	/** E009 遡及回数が指定されていません。[{0}] */
	,No_Count
}
