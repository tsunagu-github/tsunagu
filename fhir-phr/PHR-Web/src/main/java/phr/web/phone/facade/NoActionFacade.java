/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アクション定義不正の処理クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import phr.web.phone.dto.ResponseBaseDto;

/**
 *
 * @author daisuke
 */
public class NoActionFacade extends PhoneFacade {

    /**
     * 処理を開始する
     *
     * @param json
     * @return
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        ResponseBaseDto dto = new ResponseBaseDto();

        dto.setStatus(ResponseBaseDto.ERROR);
        dto.setMessage("Actionの定義が不正です。");

        return dto;
    }
}
