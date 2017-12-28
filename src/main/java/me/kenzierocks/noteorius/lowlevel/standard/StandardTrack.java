package me.kenzierocks.noteorius.lowlevel.standard;

import me.kenzierocks.noteorius.lowlevel.NtOrPattern;
import me.kenzierocks.noteorius.lowlevel.NtOrTrack;

public class StandardTrack extends StandardPositionTracker.OfSizedItemWithSize<NtOrTrack, NtOrPattern> implements NtOrTrack {

    private final String label;

    public StandardTrack(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
