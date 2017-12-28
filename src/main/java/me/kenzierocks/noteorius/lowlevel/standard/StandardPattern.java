package me.kenzierocks.noteorius.lowlevel.standard;

import me.kenzierocks.noteorius.lowlevel.NtOrNote;
import me.kenzierocks.noteorius.lowlevel.NtOrPattern;

public class StandardPattern extends StandardPositionTracker.OfSizedItemWithSize<NtOrPattern, NtOrNote> implements NtOrPattern {

    private final String label;

    public StandardPattern(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public NtOrPattern transpose(int steps) {
        // TODO
        return null;
    }

}
