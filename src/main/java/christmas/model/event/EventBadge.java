package christmas.model.event;

import java.util.Arrays;

public enum EventBadge {
    STAR("별", -9999, -5000),
    TREE("트리", -19999, -10000),
    SANTA("산타", Integer.MIN_VALUE, -20000),
    NONE("없음", 0, 0);
    private final String name;
    private final int min;
    private final int max;

    EventBadge(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public static EventBadge getBadge(int totalBenefit) {
        return Arrays.stream(EventBadge.values())
                .filter(eventBadge -> eventBadge.isInRange(totalBenefit))
                .findAny()
                .orElse(NONE);
    }

    private boolean isInRange(int totalBenefit) {
        return min <= totalBenefit && totalBenefit <= max;
    }

    public String getName() {
        return name;
    }
}
