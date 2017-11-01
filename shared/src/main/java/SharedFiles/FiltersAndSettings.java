package SharedFiles;

import java.util.HashMap;

/**
 * Created by devey on 4/18/17.
 */

public class FiltersAndSettings {

    private static final FiltersAndSettings ourInstance = new FiltersAndSettings();

    private String life_story_lines_color;
    private String family_tree_line_color;
    private String spouse_line_color;

    private boolean life_story_bool;
    private boolean family_tree_bool;
    private boolean spouse_bool;

    private boolean father_side_bool;
    private boolean mother_side_bool;
    private boolean female_bool;
    private boolean male_bool;


    private HashMap<String, Boolean> mFilters = new HashMap<>();
    private HashMap<String, Integer> mColorsMap = new HashMap<>();
    private String map_type;
//    private String[] colors = {"#000000","#ffffff" , "#4286f4", "#ff0000", "#ff9400", "#fff200", "#00ff50", "#e900ff", "#ffc1c1"};
    //    colors of array: black, white, blue, red, orange, yellow, green, purple, pink
    public static FiltersAndSettings getInstance() {
        return ourInstance;
    }

    private FiltersAndSettings() {
        setColors();
        life_story_bool = true;
        family_tree_bool = true;
        spouse_bool = true;

        father_side_bool = true;
        mother_side_bool = true;
        female_bool = true;
        male_bool = true;

        map_type = "Normal";
        life_story_lines_color = "Red";
        family_tree_line_color = "Blue";
        spouse_line_color = "Yellow";
    }

    public void setColors(){
        mColorsMap.put("Black", -16777216);
        mColorsMap.put("White",-1);
        mColorsMap.put("Blue", -16776961);
        mColorsMap.put("Red", -65536);
        mColorsMap.put("Yellow", -256);
        mColorsMap.put("Green", -16711936);
    }

    public void setMapType(String map_type)
    {
        this.map_type = map_type;
    }
    public String getMapType()
    {
        return map_type;
    }

    public String getLifeStoryLinesColor() {
        return life_story_lines_color;
    }

    public void setLifeStoryLinesColor(String life_story_lines_color) {
        this.life_story_lines_color = life_story_lines_color;
    }

    public String getFamilyTreeLineColor() {
        return family_tree_line_color;
    }

    public void setFamilyTreeLineColor(String family_tree_line_color) {
        this.family_tree_line_color = family_tree_line_color;
    }

    public String getSpouseLineColor() {
        return spouse_line_color;
    }

    public void setSpouseLineColor(String spouse_line_color) {
        this.spouse_line_color = spouse_line_color;
    }

    public int getIntColorFromMap(String color_name)
    {
        return mColorsMap.get(color_name);
    }

    public boolean isLifeStoryBool() {
        return life_story_bool;
    }

    public void setLifeStoryBool(boolean life_story_bool) {
        this.life_story_bool = life_story_bool;
    }

    public boolean isFamilyTreeBool() {
        return family_tree_bool;
    }

    public void setFamilyTreeBool(boolean family_tree_bool) {
        this.family_tree_bool = family_tree_bool;
    }

    public boolean isSpouseBool() {
        return spouse_bool;
    }

    public void setSpouseBool(boolean spouse_bool) {
        this.spouse_bool = spouse_bool;
    }

    public HashMap<String, Boolean> getFilters() {
        return mFilters;
    }
    public boolean getBoolFromEventType(String event_type){
        return mFilters.get(event_type);
    }
    public void setFilter(boolean isChecked, String event_type){
        mFilters.put(event_type, isChecked);
    }

    public boolean isFatherSideBool() {
        return father_side_bool;
    }

    public void setFatherSideBool(boolean father_side_bool) {
        this.father_side_bool = father_side_bool;
    }

    public boolean isMotherSideBool() {
        return mother_side_bool;
    }

    public void setMotherSideBool(boolean mother_side_bool) {
        this.mother_side_bool = mother_side_bool;
    }

    public boolean isFemaleBool() {
        return female_bool;
    }

    public void setFemaleBool(boolean female_bool) {
        this.female_bool = female_bool;
    }

    public boolean isMaleBool() {
        return male_bool;
    }

    public void setMaleBool(boolean male_bool) {
        this.male_bool = male_bool;
    }
}
