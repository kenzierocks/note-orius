package me.kenzierocks.noteorius.lowlevel;

import me.kenzierocks.noteorius.fraction.Fraction;

/**
 * Indicates that the object has a size. The size is represented as a fraction.
 */
public interface Sized {

    Fraction getLength();

}
