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
