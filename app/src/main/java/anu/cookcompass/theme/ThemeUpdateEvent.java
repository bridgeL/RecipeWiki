package anu.cookcompass.theme;

/**
 * @author u7752874, Xinlei Wen
 * @feature Data-format
 */
/**
 * An event used by EventBus to broadcast theme update. Activities will update its appearance
 * according to the information carried by this event object.
 */
public class ThemeUpdateEvent {
    String colorValue;

    /**
     * Default constructor
     */
    public ThemeUpdateEvent(){

    }

    /**
     * The constructor with a given color value.
     * @param colorValue The color used by the current theme. Should be a 6-digit hexadecimal RGB value.
     */
    public ThemeUpdateEvent(String colorValue){
        this.colorValue = colorValue;
    }

    public String getColorValue() {
        return colorValue;
    }
}
