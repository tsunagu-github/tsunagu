/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kis-note
 */
public enum MedicalRecordNoEnum {

	/**
	 * 仕様書：患者情報レコード
         * DB:DosageHeadtableを表すオブジェクト。
	 */
	DOSAGEHEAD("1"),

        /**
         * 仕様書：患者特記レコード
	 * DB:PatientSpecialInstructionを表すオブジェクト。
	 */
	SPINST("2"),

        /**
         * 仕様書：一般用医薬品レコード
	 * DB:NonPrescriptionDrugsを表すオブジェクト。
	 */
	NONPRESC("3"),
        
        /**
         * 仕様書：手帳メモレコード
	 * DB:DosageNoteを表すオブジェクト。
         */
	DOSAGENOTE("4"),

        /**
         * 仕様書：調剤等年月日レコード
	 * DB:Dosageを表すオブジェクト。
	 */
	DOSAGE("5"),

        /**
         * 仕様書：調剤機関レコード
	 * DB:DosageMedicalOrganization(DosageTypeCd＝1（調剤）)を表すオブジェクト。
	 */
	PHARMACY("11"),

        /**
         * 仕様書：薬剤師レコード
	 * DB:DosageDoctor(DosageTypeCd＝1（調剤）)を表すオブジェクト。
	 */
	PHARMACIST("15"),
        
        /**
         * 仕様書：処方医療機関レコード
	 * DB:DosageMedicalOrganization(DosageTypeCd＝2（処方）)を表すオブジェクト。
	 */
	MEDORGAN("51"),

        /**
         * 仕様書：処方医師レコード
	 * DB:DosageDoctor(DosageTypeCd＝2（処方）)を表すオブジェクト。
	 */
	DOCTOR("55"),

        /**
         * 仕様書：薬品レコード
	 * DB:DosegeMedicineを表すオブジェクト。
	 */
	MEDICINE("201"),

        /**
         * 仕様書：薬品補足レコード
	 * DBDosegeMedicineAdditionを表すオブジェクト。
	 */
	MEDADD("281"),
        
 
        /**
         * 仕様書：薬品服用注意レコード
	 * DB:DosegeMedicineAttentionを表すオブジェクト。
	 */
	MEDATTENT("291"),

        /**
         * 仕様書：用法レコード
	 * DB:DosageRecipeを表すオブジェクト。
	 */
	RECIPE("301"),

        /**
         * 仕様書：用法補足レコード
	 * DB:DosageRecipeAdditionを表すオブジェクト。
	 */
        //311
        RECIPEADD("311"),
        
        /**
         * 仕様書：処方服用注意レコード
	 * DB:DosageRecipeAttentionを表すオブジェクト。
	 */
	RECATTENT("391"),

        /**
         * 仕様書：服用注意レコード
	 * DB：DosageAttentionを表すオブジェクト。
	 */
	DOSAGEATTENT("401"),
 
        /**
         * 仕様書：医療機関等提供情報レコード
	 * DB:DosageOrganProvisionInfoを表すオブジェクト。
	 */
	ORGANPROVINFO("411"),

        /**
         * 仕様書：備考レコード
	 * DB:DosageRemarkを表すオブジェクト。
	 */
	REMARK("501"),
  
        /**
         * 仕様書：患者等記入レコード
	 * DB:DosagePatientInputを表すオブジェクト。
	 */
	PATINPUT("601"),
        
        /**
         * 仕様書：かかりつけ薬剤師レコード
	 * DB:Parmaceutistを表すオブジェクト。
         */
	PARMACEUTIST("701"),
	
        /**
         * 仕様書：分割制御レコード
         */
	SEPARATOR("911")        

        ;

	private final String id;
        
	private MedicalRecordNoEnum(String id)
	{
		this.id = id;
	}

	/**
	 * オブジェクトを一意に表すオブジェクトIDを取得します。
	 * 
	 * @return オブジェクトID
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 引数で指定された文字列（コード）を解析して対応するオブジェクトを返します。対応するオブジェクトが存在しない場合は null
	 * を返します。
	 *
	 * @param s コード
	 * @return 対応するオブジェクト
	 */
	public static MedicalRecordNoEnum parse(String s)
	{
		if (s.equals(DOSAGEHEAD.getId())) return DOSAGEHEAD;
		else if (s.equals(SPINST.getId())) return SPINST;
                else if (s.equals(NONPRESC.getId())) return NONPRESC;
                else if (s.equals(DOSAGENOTE.getId())) return DOSAGENOTE;
                else if (s.equals(DOSAGE.getId())) return DOSAGE;
                else if (s.equals(PHARMACY.getId())) return PHARMACY;
                else if (s.equals(PHARMACIST.getId())) return PHARMACIST;
                else if (s.equals(MEDORGAN.getId())) return MEDORGAN;
                else if (s.equals(DOCTOR.getId())) return DOCTOR;
                else if (s.equals(MEDICINE.getId())) return MEDICINE;
                else if (s.equals(MEDADD.getId())) return MEDADD;
                else if (s.equals(MEDATTENT.getId())) return MEDATTENT;
                else if (s.equals(RECIPE.getId())) return RECIPE;
                else if (s.equals(RECIPEADD.getId())) return RECIPEADD;
                else if (s.equals(RECATTENT.getId())) return RECATTENT;
                else if (s.equals(DOSAGEATTENT.getId())) return DOSAGEATTENT;
                else if (s.equals(ORGANPROVINFO.getId())) return ORGANPROVINFO;
                else if (s.equals(REMARK.getId())) return REMARK;
                else if (s.equals(PATINPUT.getId())) return PATINPUT;
                else if (s.equals(PARMACEUTIST.getId())) return PARMACEUTIST;
                else if (s.equals(SEPARATOR.getId())) return SEPARATOR;
		else return null;
	}
	/**
	 * リストを取得します
	 * @return
	 */
	public static List<MedicalRecordNoEnum> getList() {
		List<MedicalRecordNoEnum> list = new ArrayList();

		list.add(DOSAGEHEAD);
		list.add(SPINST);
		list.add(NONPRESC);
		list.add(DOSAGENOTE);
		list.add(DOSAGE);
		list.add(PHARMACY);
                list.add(PHARMACIST);
                list.add(MEDORGAN);
                list.add(DOCTOR);
                list.add(MEDICINE);
                list.add(MEDADD);
                list.add(MEDATTENT);
                list.add(RECIPE);
                list.add(RECIPEADD);
                list.add(RECATTENT);
                list.add(DOSAGEATTENT);
                list.add(ORGANPROVINFO);
                list.add(REMARK);
                list.add(PATINPUT);
                list.add(PARMACEUTIST);
                list.add(SEPARATOR);
		return list;
	}
	/**
	 * 引数で指定された文字列（コード）を解析して対応するオブジェクトを返します。
	 *
	 * @param s コード
	 * @return 対応するオブジェクト
	 */
	public Object parseObject(String s) {
		return parse(s);
	}    
}
