package me.kenzierocks.noteorius.fraction;

import java.io.ObjectStreamException;

import com.google.auto.value.AutoValue;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import me.kenzierocks.noteorius.lowlevel.NtOrMath;

/**
 * A simple fractions class. Supports basic math on fractions.
 * 
 * <p>
 * <strong>Note:</strong> Passing a zero for a denominator (or in the case of
 * division, the numerator) will result in undefined behavior, most likely an
 * exception.
 * </p>
 */
@AutoValue
public abstract class Fraction extends Number implements Comparable<Fraction> {

    private static final long serialVersionUID = -1246984937758293930L;

    private static Table<Integer, Integer, Fraction> SINGLETON_TABLE = HashBasedTable.create();

    static {
        staticInit(0, 1);
        staticInit(1, 1);
        staticInit(2, 1);

        staticInit(1, 2);

        staticInit(1, 3);
        staticInit(2, 3);

        staticInit(1, 4);
        staticInit(3, 4);

        staticInit(1, 5);
        staticInit(2, 5);
        staticInit(3, 5);
        staticInit(4, 5);

        SINGLETON_TABLE = ImmutableTable.copyOf(SINGLETON_TABLE);
    }

    private static Fraction staticInit(int n, int d) {
        Fraction frac = of(n, d);
        SINGLETON_TABLE.put(n, d, frac);
        return frac;
    }

    private static final Fraction ZERO = of(0, 1);

    public static Fraction zero() {
        return ZERO;
    }

    private static final Fraction ONE = of(1, 1);

    public static Fraction one() {
        return ONE;
    }

    // statically importable method:
    /**
     * Statically importable version of {@link #of(int, int)}.
     * 
     * @see #of(int, int)
     */
    public static Fraction fraction(int numerator, int denominator) {
        return of(numerator, denominator);
    }

    /**
     * Returns a fraction, in simplest form, of the numerator and denominator
     * given.
     * 
     * <p>
     * Simplest form is the form with all common factors removed from the
     * numerator and denominator. For example, providing 5/10 would result in
     * 1/2.
     * </p>
     * 
     * @param numerator
     *            - the numerator of the fraction
     * @param denominator
     *            - the denominator of the fraction
     * @return the fraction
     */
    public static Fraction of(int numerator, int denominator) {
        int gcd = NtOrMath.gcd(numerator, denominator);
        int n = numerator / gcd;
        int d = denominator / gcd;
        Fraction singleton = singletonTable(n, d);
        if (singleton != null) {
            return singleton;
        }
        return new AutoValue_Fraction(n, d);
    }

    private static Fraction singletonTable(int numerator, int denominator) {
        return SINGLETON_TABLE.get(numerator, denominator);
    }

    public abstract int numerator();

    public final int top() {
        return numerator();
    }

    public abstract int denominator();

    public final int bot() {
        return denominator();
    }

    // Comparable implementation
    @Override
    public int compareTo(Fraction o) {
        if (o.denominator() == denominator()) {
            // simple numerator compare
            return Integer.compare(numerator(), o.numerator());
        }
        long thisByOther = numerator() * ((long) o.denominator());
        long otherByThis = o.numerator() * ((long) denominator());
        return Long.compare(thisByOther, otherByThis);
    }

    // Number values

    @Override
    public int intValue() {
        return top() / bot();
    }

    @Override
    public long longValue() {
        return top() / (long) bot();
    }

    @Override
    public float floatValue() {
        return top() / (float) bot();
    }

    @Override
    public double doubleValue() {
        return top() / (double) bot();
    }

    // math functions

    // #main
    public final Fraction add(int numerator, int denominator) {
        // how 2 add fractions
        // multiply both by to have D = LCM
        int lcm = NtOrMath.lcm(denominator(), denominator);
        int thisNewN = numerator() * (lcm / denominator());
        int otherNewN = numerator * (lcm / denominator);
        // add the numerators
        int sum = thisNewN + otherNewN;
        // complete!
        return of(sum, lcm);
    }

    // #overload
    public final Fraction add(int number) {
        return add(number, 1);
    }

    // #overload
    public final Fraction add(Fraction number) {
        return add(number.numerator(), number.denominator());
    }

    // #main
    public final Fraction sub(int numerator, int denominator) {
        return add(-numerator, denominator);
    }

    // #overload
    public final Fraction sub(int number) {
        return sub(number, 1);
    }

    // #overload
    public final Fraction sub(Fraction number) {
        return sub(number.numerator(), number.denominator());
    }

    // #main
    public final Fraction mul(int numerator, int denominator) {
        return of(numerator() * numerator, denominator() * denominator);
    }

    // #overload
    public final Fraction mul(int number) {
        return mul(number, 1);
    }

    // #overload
    public final Fraction mul(Fraction number) {
        return mul(number.numerator(), number.denominator());
    }

    // #main
    public final Fraction div(int numerator, int denominator) {
        return mul(denominator, numerator);
    }

    // #overload
    public final Fraction div(int number) {
        return div(number, 1);
    }

    // #overload
    public final Fraction div(Fraction number) {
        return div(number.numerator(), number.denominator());
    }

    /**
     * Calculates what the numerator would be if this fractions denominator was
     * instead {@code denominator}. If {@code denominator} is not a multiple of
     * {@link #denominator()}, then the result will not be precise.
     * 
     * @param denominator
     *            - the denominator to translate to
     * @return the correct numerator for the given denominator
     */
    public final int numeratorIfDenominator(int denominator) {
        return (numerator() * denominator) / denominator();
    }

    // Serializable implementation

    private Object readResolve() throws ObjectStreamException {
        Fraction replacement = singletonTable(numerator(), denominator());
        return replacement != null ? replacement : this;
    }

}
