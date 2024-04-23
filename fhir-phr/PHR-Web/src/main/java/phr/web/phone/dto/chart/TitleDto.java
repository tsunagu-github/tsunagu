/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto.chart;

/**
 *
 * @author daisuke
 */
public class TitleDto {

    /**
     * 表示有無
     */
    private boolean display;

    /**
     * タイトルテキスト
     */
    private String text;

    /**
     * デフォルトコンストラクタ
     */
    public TitleDto() {
        display = true;
    }

    /**
     * コンストラクタ
     * @param text 
     */
    public TitleDto(String text) {
        display = true;
        this.text = text;
    }

    /**
     * 表示有無
     *
     * @return the display
     */
    public boolean isDisplay() {
        return display;
    }

    /**
     * 表示有無
     *
     * @param display the display to set
     */
    public void setDisplay(boolean display) {
        this.display = display;
    }

    /**
     * タイトルテキスト
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * タイトルテキスト
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
