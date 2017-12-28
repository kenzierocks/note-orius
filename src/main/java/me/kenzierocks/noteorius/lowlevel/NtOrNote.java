package me.kenzierocks.noteorius.lowlevel;

import com.google.auto.value.AutoValue;

import me.kenzierocks.noteorius.fraction.Fraction;

/**
 * 
 * <p>
 * {@link Sized} unit is measures.
 * </p>
 */
@AutoValue
public abstract class NtOrNote implements Sized {

    public static NtOrNote note(Fraction length, int instrument, NtOrPitch pitch, int volume) {
        return new AutoValue_NtOrNote(length, instrument, pitch, volume);
    }

    NtOrNote() {
    }

    public abstract int getInstrument();

    public abstract NtOrPitch getPitch();

    public abstract int getVolume();

}
