package me.kenzierocks.noteorius.lowlevel;

import com.google.common.collect.ImmutableSortedMap;

import me.kenzierocks.noteorius.fraction.Fraction;

/**
 * Represents something that tracks a position, and can have items added at
 * specific positions.
 * 
 * The position is tracked using a generic "unit", and can be divided into
 * arbitrary fractions.
 * 
 * @param <I>
 *            - the type of item that can be added
 */
public interface PositionTracker<SELF extends PositionTracker<SELF, I>, I> {

    // internalish
    SELF __self();

    /**
     * Adds an item at the current position.
     * 
     * @param item
     *            - the item to add
     * @return {@code this}
     */
    SELF addItem(I item);

    /**
     * Advance the position by the length.
     * 
     * @param length
     *            - the length to advance
     * @return {@code this}
     */
    default SELF advance(Fraction length) {
        return setPosition(getPosition().add(length));
    }

    /**
     * Set the position to {@code position}.
     * 
     * @param position
     *            - the new position
     * @return {@code this}
     */
    SELF setPosition(Fraction position);

    Fraction getPosition();

    /**
     * Gets all the items in the tracker, sorted by position.
     * 
     * @return the items in the tracker
     */
    ImmutableSortedMap<Fraction, I> getItemMap();

    // extensions to PT

    interface OfSizedItem<SELF extends OfSizedItem<SELF, I>, I extends Sized>
            extends PositionTracker<SELF, I> {

        default SELF addItemAndAdvance(I item) {
            return addItem(item).advance(item.getLength());
        }

    }

}
