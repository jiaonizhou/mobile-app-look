package mobile.mycloset;

/**
 * Created by Cindy on 5/18/16.
 */
public class Cloth {
    private int clothID;
    private String warmth;
    private String category;
    private String type;
    private  String picfile;

    public int getClothID(){return clothID;}
    public String getWarmth(){return  warmth;}
    public String getCategory(){return category;}
    public String getType(){return type;}
    public String getPicfile(){return picfile;}

    public void setClothID(int clothID){this.clothID = clothID;}
    public void setWarmth(String warmth){this.warmth = warmth;}
    public void setCategory(String category){this.category = category;}
    public void setType(String type){this.type = type;}
    public void setPicfile(String picfile){this.picfile = picfile;}
}
