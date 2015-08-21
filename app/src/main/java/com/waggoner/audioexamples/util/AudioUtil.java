package com.waggoner.audioexamples.util;

/**
 * Created by nathanielwaggoner on 8/18/15.
 */
public class AudioUtil {


    public static byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }


    public static short[] bytesToShorts(byte[] sData) {
        int byteArrSize = sData.length;
        short[] shorts = new short[byteArrSize / 2];
        for (int i = 0; i < byteArrSize/2; i++) {
            shorts[i] = (short)(((sData[i*2+1]&0xFF)<<8) | (sData[i*2]&0xFF));
        }
        return shorts;
    }
}
