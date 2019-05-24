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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Stream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.google.common.collect.ImmutableList;

import me.kenzierocks.noteorius.fraction.Fraction;
import me.kenzierocks.noteorius.lowlevel.NtOrMath;
import me.kenzierocks.noteorius.lowlevel.NtOrProject;
import me.kenzierocks.noteorius.lowlevel.NtOrTiming;
import me.kenzierocks.noteorius.lowlevel.NtOrTrack;
import me.kenzierocks.noteorius.lowlevel.PositionTracker;

public class StandardProject implements NtOrProject {

    private final String label;
    private final ImmutableList.Builder<NtOrTrack> tracks = ImmutableList.builder();
    private transient ImmutableList<NtOrTrack> tracksCache;
    private final StandardTiming timing = new StandardTiming();

    public StandardProject(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public NtOrTrack addTrack(String label) {
        tracksCache = null;
        NtOrTrack track = new StandardTrack(label);
        tracks.add(track);
        return track;
    }

    @Override
    public ImmutableList<NtOrTrack> getTracks() {
        if (tracksCache == null) {
            tracksCache = tracks.build();
        }
        return tracksCache;
    }

    @Override
    public NtOrTiming getTiming() {
        return timing;
    }

    @Override
    public void save(OutputStream stream) throws IOException {
        MidiEncoder encoder = encodeProject();
        encoder.save(stream);
    }

    private MidiEncoder encodeProject() {
        MidiEncoder encoder = new MidiEncoder(calculateMaximumPrecision());
        encoder.timingData(timing);
        getTracks().forEach(encoder::track);
        return encoder;
    }

    private int calculateMaximumPrecision() {
        // LCM highest track denominator
        // and highest pattern denominator
        int trackHighest = highestDenominator(getTracks().stream().map(PositionTracker::getItemMap));
        int patternHighest = highestDenominator(getTracks().stream()
                .flatMap(t -> t.getItemMap().values().stream())
                .map(PositionTracker::getItemMap));
        return NtOrMath.lcm(trackHighest, patternHighest) * 4;
    }

    private int highestDenominator(Stream<Map<Fraction, ?>> trackers) {
        return trackers
                .flatMap(t -> t.keySet().stream())
                .mapToInt(Fraction::denominator)
                .max().orElse(1);
    }
    @Override
    public void play() {
        Sequence seq = encodeProject().getSequence();
        try (Sequencer player = MidiSystem.getSequencer()) {
            player.open();
            player.setSequence(seq);
            player.start();
            // we pause here.
            while (player.isRunning()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
            // let it flush out the last audio
            Thread.sleep(500);
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
