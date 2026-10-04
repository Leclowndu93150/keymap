package com.leclowndu93150.keymap.cross.services;

import java.io.File;

public interface IPlatformHelper {
    String loader();
    boolean modLoaded(String modid);
    boolean dev();
    File config(String file);
    IKeybindHelper keybindHelper();
    ITickHelper tickHelper();
}
