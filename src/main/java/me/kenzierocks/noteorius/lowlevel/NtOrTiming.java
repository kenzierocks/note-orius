package me.kenzierocks.noteorius.lowlevel;

import me.kenzierocks.noteorius.fraction.Fraction;

/**
 * Timing data for a project. Set the tempo by moving the position to the
 * appropriate spot, and calling {@link #addItem(Integer)} with a tempo in BPM.
 * There is an initial tempo set at the start ({@link Fraction#of Fraction.of(0,
 * 1)}) of 120 BPM.
 * 
 * <p>
 * {@link PositionTracker} units are in measures.
 * </p>
 */
public interface NtOrTiming extends PositionTracker<NtOrTiming, Integer> {

    @Override
    default NtOrTiming __self() {
        return this;
    }

}
