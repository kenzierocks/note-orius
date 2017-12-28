package me.kenzierocks.noteorius.lowlevel.standard;

import static me.kenzierocks.noteorius.midi.Midi.noteOff;
import static me.kenzierocks.noteorius.midi.Midi.noteOn;
import static me.kenzierocks.noteorius.midi.Midi.programChange;
import static me.kenzierocks.noteorius.midi.Midi.safeMidiRet;
import static me.kenzierocks.noteorius.midi.Midi.safeMidiRun;
import static me.kenzierocks.noteorius.midi.Midi.setTempo;
import static me.kenzierocks.noteorius.midi.Midi.trackName;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import me.kenzierocks.noteorius.fraction.Fraction;
import me.kenzierocks.noteorius.lowlevel.NtOrTiming;
import me.kenzierocks.noteorius.lowlevel.NtOrTrack;

class MidiEncoder {

    private static final int NO_INSTRUMENT = -1;

    private final int ticksPerBeat;
    private final Sequence sequence;
    private final int[] currentChannels = new int[16];
    {
        Arrays.fill(currentChannels, NO_INSTRUMENT);
    }
    private final Deque<Integer> channelUsageQueue = new LinkedList<>();

    public MidiEncoder(int maxDenom) {
        this.ticksPerBeat = maxDenom;
        this.sequence = safeMidiRet(() -> new Sequence(Sequence.PPQ, maxDenom));
    }

    private Track getOrCreateTrack(int track) {
        Track[] tracks = sequence.getTracks();
        if (tracks.length >= track) {
            return sequence.createTrack();
        }
        return tracks[track];
    }

    private int tickForPortionOfMeasure(Fraction portion) {
        // multiply fraction by 4, then get in ticks
        return portion.mul(4).numeratorIfDenominator(ticksPerBeat);
    }

    private MidiEvent timedEvent(MidiMessage message, Fraction time) {
        return new MidiEvent(message, tickForPortionOfMeasure(time));
    }

    public void name(String name) {
        safeMidiRun(() -> {
            getOrCreateTrack(0).add(new MidiEvent(trackName(name), 0));
        });
    }

    public void timingData(NtOrTiming timing) {
        Track timingTrack = getOrCreateTrack(0);
        timing.getItemMap().forEach((time, bpm) -> safeMidiRun(() -> {
            timingTrack.add(timedEvent(setTempo(bpm), time));
        }));
    }

    public void track(NtOrTrack track) {
        Track midiTrack = sequence.createTrack();
        safeMidiRun(() -> midiTrack.add(new MidiEvent(trackName(track.getLabel()), 0)));
        track.getItemMap().forEach((time, pattern) -> {
            pattern.getItemMap().forEach((patTime, note) -> {
                Fraction actualTime = time.add(patTime);
                int channel = mapInstrument(note.getInstrument(), midiTrack, actualTime);
                safeMidiRun(() -> {
                    midiTrack.add(timedEvent(noteOn(channel, note), actualTime));
                    midiTrack.add(timedEvent(noteOff(channel, note), actualTime.add(note.getLength())));
                });
            });
        });
    }

    private int mapInstrument(int instrument, Track track, Fraction time) {
        for (int i = 0; i < currentChannels.length; i++) {
            if (currentChannels[i] == instrument) {
                return useChannel(i, NO_INSTRUMENT, track, time);
            }
        }
        for (int i = 0; i < currentChannels.length; i++) {
            if (currentChannels[i] == NO_INSTRUMENT) {
                return useChannel(i, instrument, track, time);
            }
        }
        int nextFree = channelUsageQueue.getLast();
        return useChannel(nextFree, instrument, track, time);
    }

    private int useChannel(int channel, int newInstrument, Track track, Fraction time) {
        channelUsageQueue.removeFirstOccurrence(channel);
        channelUsageQueue.addFirst(channel);
        if (newInstrument != NO_INSTRUMENT) {
            safeMidiRun(() -> track.add(timedEvent(programChange(channel, newInstrument), time)));
            currentChannels[channel] = newInstrument;
        }
        return channel;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void save(OutputStream stream) throws IOException {
        // 1 -> midi type 1, multiple simultaneous tracks, timing data in first
        MidiSystem.write(sequence, 1, stream);
    }

}
