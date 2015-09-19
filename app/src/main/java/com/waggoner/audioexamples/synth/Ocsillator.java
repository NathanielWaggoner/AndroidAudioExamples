package com.waggoner.audioexamples.synth;

/**
 *
 * Basic Ocsillator taken from //http://www.drdobbs.com/jvm/music-components-in-java-creating-oscill/230500178
 * with nescesary modifications for Android
 * Created by nathanielwaggoner on 9/18/15.
 *
 */
public class Ocsillator {
    // Instance data
    private WAVESHAPE waveshape;
    private long periodSamples;
    private long sampleNumber;
    public enum WAVESHAPE {
        SIN, SQU, SAW
    }

    /**
     * Basic Oscillator Class Constructor
     *
     * Default instance has SIN waveshape at 1000 Hz
     */
    public Ocsillator() {

        // Set defaults
        setOscWaveshape(WAVESHAPE.SIN);
        setFrequency(1000.0);
    }
    /**
     * Set waveshape of oscillator
     *
     * @param waveshape Determines the waveshape of this oscillator
     */
    public void setOscWaveshape(WAVESHAPE waveshape) {

        this.waveshape = waveshape;
    }

    /**
     * Set the frequency of the oscillator in Hz.
     *
     * @param frequency Frequency in Hz for this oscillator
     */
    public void setFrequency(double frequency) {

        periodSamples = (long)(SamplePlayer.SAMPLE_RATE / frequency);
    }

    /**
     * Return the next sample of the oscillator's waveform
     *
     * @return Next oscillator sample
     */
    protected double getSample() {

        double value;
        double x = sampleNumber / (double) periodSamples;

        switch (waveshape) {

            default:
            case SIN:
                value = Math.sin(2.0 * Math.PI * x);
                break;

            case SQU:
                if (sampleNumber < (periodSamples / 2)) {
                    value = 1.0;
                }  else  {
                    value = -1.0;
                }
                break;

            case SAW:
                value = 2.0 * (x - Math.floor(x + 0.5));
                break;
        }
        sampleNumber = (sampleNumber + 1) % periodSamples;
        return value;
    }

    /**
     * Get a buffer of oscillator samples
     *
     * @param buffer Array to fill with samples
     *
     * @return Count of bytes produced.
     */
    public int getSamples(short[] buffer) {
        for (int i = 0; i < SamplePlayer.SAMPLES_PER_BUFFER; i++) {
            double ds = getSample() * Short.MAX_VALUE;
            short ss = (short) Math.round(ds);
            buffer[i] = ss;
        }
        return SamplePlayer.SAMPLES_PER_BUFFER;
    }
}
