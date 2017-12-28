package me.kenzierocks.noteorius.lowlevel;

/**
 * 
 * <p>
 * {@link PositionTracker} and {@link Sized} unit is measures.
 * </p>
 */
public interface NtOrTrack extends Labeled, Sized, PositionTracker.OfSizedItem<NtOrTrack, NtOrPattern> {

    @Override
    default NtOrTrack __self() {
        return this;
    }

}
