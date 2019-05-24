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
