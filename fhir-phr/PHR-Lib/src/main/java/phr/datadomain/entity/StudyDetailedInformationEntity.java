/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：研究詳細情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.*;

/**
 * 研究詳細情報のデータオブジェクトです。
 */
public class StudyDetailedInformationEntity extends StudyDetailedInformationEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StudyDetailedInformationEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public StudyDetailedInformationEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 新チェックリスト
     */
    private String newCheckList = null;
    
    
    public String getNewCheckList() {
        return newCheckList;
    }

    public void setNewCheckList(String newCheckList) {
        this.newCheckList = newCheckList;
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyDetailedInformationEntity
     */
    public static StudyDetailedInformationEntity setD(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyDetailedInformationEntity
     */
    public static StudyDetailedInformationEntity setD(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        StudyDetailedInformationEntity entity = new StudyDetailedInformationEntity();
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setCheckList(getString(dataRow, "CheckList"));
        entity.setStatus(getString(dataRow, "Status"));
        entity.setSortNo(getString(dataRow, "SortNo"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("SeqID       ：" + entity.getSeqId());
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("項目ID      ：" + entity.getSubjectId());
            logger.debug("確認項目    ：" + entity.getCheckList());
            logger.debug("ステータス  ：" + entity.getStatus());
            logger.debug("ソート番号  ：" + entity.getSortNo());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }
}
