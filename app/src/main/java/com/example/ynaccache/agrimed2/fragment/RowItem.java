package com.example.ynaccache.agrimed2.fragment;

/**
 * Created by y.naccache on 02/10/2017.
 */

public class RowItem {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String quant;
    private String  unite;

    public RowItem() {
    }

    public RowItem(String id, String title, String quant, String unite) {
        this.id= id;
        this.title = title;
        this.quant = quant;
        this.unite = unite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }
}
