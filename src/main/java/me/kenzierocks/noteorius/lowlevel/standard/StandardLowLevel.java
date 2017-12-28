package me.kenzierocks.noteorius.lowlevel.standard;

import me.kenzierocks.noteorius.lowlevel.NtOrLowLevel;
import me.kenzierocks.noteorius.lowlevel.NtOrPattern;
import me.kenzierocks.noteorius.lowlevel.NtOrProject;

public class StandardLowLevel implements NtOrLowLevel {

    private static final StandardLowLevel INSTANCE = new StandardLowLevel();

    public static StandardLowLevel instance() {
        return INSTANCE;
    }

    private StandardLowLevel() {
    }

    @Override
    public NtOrPattern newPattern(String label) {
        return new StandardPattern(label);
    }

    @Override
    public NtOrProject newProject(String label) {
        return new StandardProject(label);
    }

}
