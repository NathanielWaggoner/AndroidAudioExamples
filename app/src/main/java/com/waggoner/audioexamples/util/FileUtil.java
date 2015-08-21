package com.waggoner.audioexamples.util;

/**
 * Created by nathanielwaggoner on 8/20/15.
 */
public class FileUtil {

    public static String generateFileName() {
        return "audio_example_recording_"+System.currentTimeMillis();
    }
}
