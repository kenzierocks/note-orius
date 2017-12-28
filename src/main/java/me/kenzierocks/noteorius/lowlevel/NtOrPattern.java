package me.kenzierocks.noteorius.lowlevel;

/**
 * A pattern represents a set of notes. It can be repeated in a track to produce
 * the same sequence of notes, and certain modifications can be applied.
 * 
 * <p>
 * {@link PositionTracker} and {@link Sized} unit is measures.
 * </p>
 */
public interface NtOrPattern extends Labeled, Sized, PositionTracker.OfSizedItem<NtOrPattern, NtOrNote> {

    @Override
    default NtOrPattern __self() {
        return this;
    }

    /**
     * Transpose this pattern up {@code steps}.
     * 
     * @param steps
     *            - the number of steps to transpose
     * @return the new, transposed, pattern
     */
    NtOrPattern transpose(int steps);

}
