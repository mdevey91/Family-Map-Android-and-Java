package SharedFiles;

/**
 * Created by devey on 4/19/17.
 */

public class RecycleModel {
    boolean switch_value;
    EventResult event;

    public RecycleModel(boolean switch_value, EventResult event) {
        this.switch_value = switch_value;
        this.event = event;
    }

    public boolean isSwitch_value() {
        return switch_value;
    }

    public EventResult getEvent() {
        return event;
    }

    public String getBoldText(){
        return event.getDescription() + " " + "Events";
    }
    public String getSmallText(){
        return "Filter By " + getBoldText();
    }
}
