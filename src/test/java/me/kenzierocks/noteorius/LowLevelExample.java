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

package me.kenzierocks.noteorius;

import static me.kenzierocks.noteorius.fraction.Fraction.fraction;
import static me.kenzierocks.noteorius.lowlevel.NtOrNote.note;
import static me.kenzierocks.noteorius.lowlevel.NtOrPitchKey.A;
import static me.kenzierocks.noteorius.lowlevel.NtOrPitchKey.B;
import static me.kenzierocks.noteorius.lowlevel.NtOrPitchKey.C_SHARP;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import me.kenzierocks.noteorius.lowlevel.NtOrLowLevel;
import me.kenzierocks.noteorius.lowlevel.NtOrPattern;
import me.kenzierocks.noteorius.lowlevel.NtOrPitch;
import me.kenzierocks.noteorius.lowlevel.NtOrProject;
import me.kenzierocks.noteorius.lowlevel.NtOrTrack;
import me.kenzierocks.noteorius.lowlevel.standard.StandardLowLevel;

public class LowLevelExample {

    private static final NtOrLowLevel LL = StandardLowLevel.instance();

    public static void main(String[] args) throws Exception {
        NtOrProject project = LL.newProject("Example");
        project.getTiming().addItem(240);
        NtOrTrack track1 = project.addTrack("Track 1");

        NtOrPattern quarterA = neat(0);
        track1.addItemAndAdvance(quarterA);
        project.play();
        try (OutputStream stream = new BufferedOutputStream(Files.newOutputStream(Paths.get("llexample.mid")))) {
            project.save(stream);
        }
    }

    private static NtOrPattern neat(int instrument) {
        NtOrPattern p = LL.newPattern("Neat!");
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 8), instrument, NtOrPitch.pitch(A, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 4), instrument, NtOrPitch.pitch(C_SHARP, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 4), instrument, NtOrPitch.pitch(B, 4), 70));
        p.addItemAndAdvance(note(fraction(1, 4), instrument, NtOrPitch.pitch(A, 4), 70));
        return p;
    }
}
