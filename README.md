# AndroidAudioExamples

Goals:

The goals of this project are pretty simple.  I'm going to demonstrate all of the Audio pathway (that I can get implemented in time for this talk...) to demonstrate I/O.  I specifically want to highlight the varying performance characteristics of the audio approaches, as well as demonstrating their implemenation difficulty.

This will include Java and C versions.

Targets:

SoundPool

MediaPLayer

MediaRecorder

AudioTrack

AudioEffect

SuperPowered - obligatory liscense note:  AndroidAudioExamples has been Superpowered!

OpenSLES

PD - maybe....

App Description:

I'll be writing a stupid simple mixer with 2-4 channels, with some basic effects plug in capabiltiies. You shouldn't look at this as a model of efficiency or mixing audio, but instead as an opprotunity to use various pathaways simultatneously and experieince their different performance characterists.

The basic dumb first example is going to be a "drum kit" model, but hopefully I can throw together a flexible enough architecture that I can put a sequencer in there and some other fun examples.

You'll be able to specify the media path for each channel of Audio.



