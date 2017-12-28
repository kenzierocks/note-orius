package me.kenzierocks.noteorius.lowlevel;

import javax.annotation.Nullable;

/**
 * Low-level note-orius entry point.
 */
public interface NtOrLowLevel {

    NtOrPattern newPattern(@Nullable String label);

    NtOrProject newProject(@Nullable String label);

}
