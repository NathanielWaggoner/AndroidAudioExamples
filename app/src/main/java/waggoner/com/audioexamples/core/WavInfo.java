package waggoner.com.audioexamples.core;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

// credit to Radiodef http://stackoverflow.com/questions/19991405/how-can-i-detect-whether-a-wav-file-has-a-44-or-46-byte-header

/**
 * Created by nathanielwaggoner on 8/10/15.
 */
public class WavInfo {

    public static int dataOffset;
    public static int dataLength;
    public static void parseWave(InputStream in)
            throws IOException {
        int skipTotal = 0;
        boolean shouldUpdateDataLength= false;
        try {
            dataOffset = 12;
            dataLength=0;
            byte[] bytes = new byte[4];
            // read first 4 bytes
            // should be RIFF descriptor
            if (in.read(bytes) < 0) {
                return;
            }
            printDescriptor(bytes);
            // first subchunk will always be at byte 12
            // there is no other dependable constant
            in.skip(8);
            for (; ; ) {
                // read each chunk descriptor
                if (in.read(bytes) < 0) {
                    break;
                }
                printDescriptor(bytes);
                if(new String(bytes,"US-ASCII").equals("data")) {
                    dataOffset=dataOffset+skipTotal;
                    shouldUpdateDataLength = true;
                    Log.e("XapPTest","DataOffset "+ dataOffset);
                }
                // read chunk length
                if (in.read(bytes) < 0) {
                    break;
                }
                // skip the length of this chunk
                // next bytes should be another descriptor or EOF
                int skipAmt = (bytes[0] & 0xFF)
                        | (bytes[1] & 0xFF) << 8
                        | (bytes[2] & 0xFF) << 16
                        | (bytes[3] & 0xFF) << 24;
                skipTotal+= skipAmt;
                if(shouldUpdateDataLength) {
                    dataLength=skipAmt;
                    shouldUpdateDataLength = false;
                    Log.e("XapPTest","DataLength: "+dataLength);
                }

                in.skip(skipAmt);
            }
            System.out.println("end of file");

        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    static void printDescriptor(byte[] bytes)
            throws IOException {
        Log.e("XappTest", "found '" + new String(bytes, "US-ASCII") + "' descriptor");
    }
}
