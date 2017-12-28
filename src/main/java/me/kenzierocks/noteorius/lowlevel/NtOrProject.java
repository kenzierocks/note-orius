package me.kenzierocks.noteorius.lowlevel;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

/**
 * A project is a collection of tracks.
 */
public interface NtOrProject extends Labeled {

    NtOrTrack addTrack(@Nullable String label);

    ImmutableList<NtOrTrack> getTracks();
    
    NtOrTiming getTiming();

    /**
     * Write this project to the specified stream.
     * 
     * @param stream
     *            - the stream to write to
     * @throws IOException
     *             - I/O exceptions are propagated
     */
    void save(OutputStream stream) throws IOException;
    
    void play();

}
