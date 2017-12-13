package ee.hitsa.ois.enums;

import java.time.DayOfWeek;

public enum Day {

    NADALAPAEV_E("E", DayOfWeek.MONDAY, "dayMon"),
    NADALAPAEV_T("T", DayOfWeek.TUESDAY, "dayTue"),
    NADALAPAEV_K("K", DayOfWeek.WEDNESDAY, "dayWed"),
    NADALAPAEV_N("N", DayOfWeek.THURSDAY, "dayThu"),
    NADALAPAEV_R("R", DayOfWeek.FRIDAY, "dayFri"),
    NADALAPAEV_L("L", DayOfWeek.SATURDAY, "daySat"),
    NADALAPAEV_P("P", DayOfWeek.SUNDAY, "daySun");

    private final String display;
    private final DayOfWeek dayOfWeek;
    private final String property;

    Day(String display, DayOfWeek dayOfWeek, String property) {
        this.display = display;
        this.dayOfWeek = dayOfWeek;
        this.property = property;
    }

    public String getDisplay() {
        return display;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getProperty() {
        return property;
    }
}
