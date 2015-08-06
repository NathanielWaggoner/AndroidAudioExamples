# AndroidAudioExamples

Goals:

The goals of this project are pretty simple.  I'm going to demonstrate all of the Audio pathways I'm currently aware of (and can get implemented in time for this talk...) to demonstrate I/O.  I specifically want to highlight the varying performance characteristics of the audio approaches, as well as demonstrating their implemenation difficulty.

This will include Java and C versions.

Targets:

SoundPool
MediaPLayer
MediaRecorder
AudioTrack
AudioEffect
SuperPowered
OpenSLES

App Description:

I'll be writing a stupid simple sequencer with 2-4 channels, with some basic effects plug in capabiltiies. 

You'll be able to specify the media path for each channel.

You can manipulate tempo.

Technical Specs:

We use 16 bit pcm if at all possible.  If it's not we cry and abandon all hope.


