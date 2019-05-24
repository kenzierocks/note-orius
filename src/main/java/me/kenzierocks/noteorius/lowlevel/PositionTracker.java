/*
 * This file is part of note-orius, licensed under the MIT License (MIT).
 *
 * Copyright (c) Kenzie Togami (kenzierocks) <https://kenzierocks.me>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
