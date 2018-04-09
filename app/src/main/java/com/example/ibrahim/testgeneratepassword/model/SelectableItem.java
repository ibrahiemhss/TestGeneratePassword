package com.example.ibrahim.testgeneratepassword.model;

/**
 * Created by ibrahim on 09/04/18.
 */

public class SelectableItem extends Passwords{
    private boolean isSelected = false;


    public SelectableItem(Passwords item,boolean isSelected) {
        super(item.getId (),item.getName(),item.getPassword ());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
