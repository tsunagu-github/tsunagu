/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

/**
 *
 * @author kis-note
 */
public class DiseaseDto {
    /*
    *　疾患名称
    */
    private String name;
    
    /*
    *　疾患ID
    */
    private String id;
    
    /*
    * 疾患タイプCd
    */
    private int typecd;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the typecd
     */
    public int getTypecd() {
        return typecd;
    }

    /**
     * @param typecd the typecd to set
     */
    public void setTypecd(int typecd) {
        this.typecd = typecd;
    }
    
}
