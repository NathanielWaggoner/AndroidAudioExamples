#include "SuperpoweredExample.h"
#include "SuperpoweredSimple.h"
#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <android/log.h>

static void playerEventCallbackA(void *clientData, SuperpoweredAdvancedAudioPlayerEvent event,
                                 void *value) {
    if (event == SuperpoweredAdvancedAudioPlayerEvent_LoadSuccess) {
        SuperpoweredAdvancedAudioPlayer *playerA = *((SuperpoweredAdvancedAudioPlayer **) clientData);
    }; if(event == SuperpoweredAdvancedAudioPlayerEvent_EOF) {
        __android_log_print(ANDROID_LOG_VERBOSE, "XapPTest", "EOF %f", value);
        playerA->pause(0,0);
        playerA->seek(0);
    } if(event == SuperpoweredAdvancedAudioPlayerEvent_DurationChanged) {
        __android_log_print(ANDROID_LOG_VERBOSE, "XapPTest", "DurationChanged %f", value);
    } if(event == SuperpoweredAdvancedAudioPlayerEvent_JogParameter) {
        __android_log_print(ANDROID_LOG_VERBOSE, "XapPTest", "JogParameter %f", value);
    } if( event == SuperpoweredAdvancedAudioPlayerEvent_LoadSuccess) {
        __android_log_print(ANDROID_LOG_VERBOSE, "XapPTest", "loadSuccess %f", value);

    }

}

static bool audioProcessing(void *clientdata, short int *audioIO, int numberOfSamples,
                            int samplerate) {
    return ((SuperpoweredExample *) clientdata)->process(audioIO, numberOfSamples);
}

SuperpoweredExample::SuperpoweredExample(const char *path, int *params) : activeFx(0),
                                                                          crossValue(0.0f),
                                                                          volB(0.0f),
                                                                          volA(1.0f * headroom) {
    pthread_mutex_init(&mutex,
                       NULL); // This will keep our player volumes and playback states in sync.
    unsigned int samplerate = params[2], buffersize = params[3];
    stereoBuffer = (float *) memalign(16, (buffersize + 16) * sizeof(float) * 2 );
    playerA = new SuperpoweredAdvancedAudioPlayer(&playerA, playerEventCallbackA, samplerate, 0);
    playerA->open(path, params[0], params[1]);
    audioSystem = new SuperpoweredAndroidAudioIO(samplerate, buffersize, false, true,
                                                 audioProcessing, this, 0);
}

SuperpoweredExample::~SuperpoweredExample() {
    delete playerA;
    delete audioSystem;
    free(stereoBuffer);
    pthread_mutex_destroy(&mutex);
}

void SuperpoweredExample::onPlayPause(bool play) {
    pthread_mutex_lock(&mutex);
    if (playerA->playing) {
        playerA->pause();
        playerA->seek(0);
    }
    playerA->play(false);
    pthread_mutex_unlock(&mutex);
}

bool SuperpoweredExample::process(short int *output, unsigned int numberOfSamples) {
    pthread_mutex_lock(&mutex);
    bool silence = !playerA->process(stereoBuffer, false, numberOfSamples, volA, 0.0, -1);
    pthread_mutex_unlock(&mutex);
    // The stereoBuffer is ready now, let's put the finished audio into the requested buffers.
    if (!silence) SuperpoweredFloatToShortInt(stereoBuffer, output, numberOfSamples);
    return !silence;
}

extern "C" {
JNIEXPORT void Java_com_waggoner_audioexamples_sources_SuperPoweredSource_SuperpoweredExample(
        JNIEnv *javaEnvironment, jobject self, jstring apkPath, jlongArray offsetAndLength);
JNIEXPORT void Java_com_waggoner_audioexamples_sources_SuperPoweredSource_onPlayPause(
        JNIEnv *javaEnvironment, jobject self, jboolean play);
}
static SuperpoweredExample *example = NULL;
// Android is not passing more than 2 custom parameters, so we had to pack file offsets and lengths into an array.
JNIEXPORT void Java_com_waggoner_audioexamples_sources_SuperPoweredSource_SuperpoweredExample(
        JNIEnv *javaEnvironment, jobject self, jstring apkPath, jlongArray params) {
    // Convert the input jlong array to a regular int array.
    jlong *longParams = javaEnvironment->GetLongArrayElements(params, JNI_FALSE);
    int arr[6];
    for (int n = 0; n < 4; n++) arr[n] = longParams[n];
    javaEnvironment->ReleaseLongArrayElements(params, longParams, JNI_ABORT);

    const char *path = javaEnvironment->GetStringUTFChars(apkPath, JNI_FALSE);
    example = new SuperpoweredExample(path, arr);
    javaEnvironment->ReleaseStringUTFChars(apkPath, path);

}

JNIEXPORT void Java_com_waggoner_audioexamples_sources_SuperPoweredSource_onPlayPause(
        JNIEnv *javaEnvironment, jobject self, jboolean play) {
    example->onPlayPause(play);
}
