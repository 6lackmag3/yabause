package org.uoyabause.android;

import java.io.File;

import android.app.Activity;
import android.content.Context;

import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import android.os.Environment;
import android.util.Log;

class BiosFilter implements FilenameFilter {
    public boolean accept(File dir, String filename) {
        if (filename.endsWith(".bin")) return true;
        if (filename.endsWith(".rom")) return true;
        return false;
    }
}

class GameFilter implements FilenameFilter {
    public boolean accept(File dir, String filename) {
    	if (filename.endsWith(".img")) return true;
    	if (filename.endsWith(".IMG")) return true;
        if (filename.endsWith(".bin")) return true;
        if (filename.endsWith(".cue")) return true;
        if (filename.endsWith(".iso")) return true;
        if (filename.endsWith(".mds")) return true;
        if (filename.endsWith(".BIN")) return true;
        if (filename.endsWith(".CUE")) return true;
        if (filename.endsWith(".ISO")) return true;
        if (filename.endsWith(".MDS")) return true;        
        return false;
    }
}

class MemoryFilter implements FilenameFilter {
    public boolean accept(File dir, String filename) {
        if (filename.endsWith(".ram")) return true;
        return false;
    }
}

public class YabauseStorage {
    static private YabauseStorage instance = null;

    private File bios;
    private File games;
    private File memory;
    private File cartridge;
    private File state;
    private File screenshots;

    private YabauseStorage() {
        File yabroot = new File(Environment.getExternalStorageDirectory(), "yabause");
        if (! yabroot.exists()) yabroot.mkdir();

        bios = new File(yabroot, "bios");
        if (! bios.exists()) bios.mkdir();

        games = new File(yabroot, "games");
        if (! games.exists()) games.mkdir();

        memory = new File(yabroot, "memory");
        if (! memory.exists()) memory.mkdir();

        cartridge = new File(yabroot, "cartridge");
        if (! cartridge.exists()) cartridge.mkdir();
        
        state = new File(yabroot, "state");
        if (! state.exists()) state.mkdir();
        
        screenshots = new File(yabroot, "screenshots");
        if (! screenshots.exists()) screenshots.mkdir();        
    }

    static public YabauseStorage getStorage() {
        if (instance == null) {
            instance = new YabauseStorage();
        }

        return instance;
    }

    public String[] getBiosFiles() {
        String[] biosfiles = bios.list(new BiosFilter());
        return biosfiles;
    }

    public String getBiosPath(String biosfile) {
        return bios + File.separator + biosfile;
    }

    public String[] getGameFiles( String other_dir_string ) {
        String[] gamefiles = games.list(new GameFilter());
        
        Arrays.sort(gamefiles, new Comparator<String>() {
        	  @Override
        	    public int compare(String obj0, String obj1) {
        	        return obj0.compareTo(obj1);
        	    }
        	});
        
        String[] selfiles  = new String[]{other_dir_string}; 
        String[] allLists = new String[selfiles.length + gamefiles.length];
        System.arraycopy(selfiles, 0, allLists, 0, selfiles.length);
        System.arraycopy(gamefiles, 0, allLists, selfiles.length, gamefiles.length);        
        return allLists;
    }

    public String getGamePath(String gamefile) {
        return games + File.separator + gamefile;
    }

    public String getGamePath() {
        return games + File.separator;
    }
    
    public String[] getMemoryFiles() {
        String[] memoryfiles = memory.list(new MemoryFilter());
        return memoryfiles;
    }

    public String getMemoryPath(String memoryfile) {
        return memory + File.separator + memoryfile;
    }

    public String getCartridgePath(String cartridgefile) {
        return cartridge + File.separator + cartridgefile;
    }
    
    public String getStateSavePath() {
        return state + File.separator;
    }   
    
    public String getScreenshotPath() {
        return screenshots + File.separator;
    }        
}
