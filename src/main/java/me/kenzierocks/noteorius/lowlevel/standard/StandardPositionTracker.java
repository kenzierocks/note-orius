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

package me.kenzierocks.noteorius.lowlevel.standard;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.ImmutableSortedMap;

import me.kenzierocks.noteorius.fraction.Fraction;
import me.kenzierocks.noteorius.lowlevel.PositionTracker;
import me.kenzierocks.noteorius.lowlevel.Sized;

public abstract class StandardPositionTracker<SELF extends PositionTracker<SELF, I>, I>
        implements PositionTracker<SELF, I> {

    private final SortedMap<Fraction, I> items = new TreeMap<>();
    private Fraction position = Fraction.zero();

    @Override
    public SELF addItem(I item) {
        items.put(position, item);
        return __self();
    }

    @Override
    public SELF setPosition(Fraction position) {
        this.position = position;
        return __self();
    }

    @Override
    public Fraction getPosition() {
        return position;
    }

    @Override
    public ImmutableSortedMap<Fraction, I> getItemMap() {
        return ImmutableSortedMap.copyOfSorted(items);
    }

    // extensions to PT

    public abstract static class OfSizedItem<SELF extends PositionTracker.OfSizedItem<SELF, I>, I extends Sized>
            extends StandardPositionTracker<SELF, I>
            implements PositionTracker.OfSizedItem<SELF, I> {

        @Override
        public SELF addItem(I item) {
            SELF self = super.addItem(item);
            postAddItem(item);
            return self;
        }

        protected void postAddItem(I item) {
        }

    }

    public abstract static class OfSizedItemWithSize<SELF extends PositionTracker.OfSizedItem<SELF, I> & Sized, I extends Sized>
            extends OfSizedItem<SELF, I>
            implements Sized {

        private Fraction length = Fraction.zero();

        @Override
        protected void postAddItem(I item) {
            super.postAddItem(item);
            length = length.add(item.getLength());
        }

        @Override
        public Fraction getLength() {
            return length;
        }
    }
}
