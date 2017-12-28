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
