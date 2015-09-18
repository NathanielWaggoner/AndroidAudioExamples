## point make at ourselves
LOCAL_PATH := $(call my-dir)
## path for the superpowered libraries which we care about
SUPERPOWERED_PATH := ../../../../superpowered/Superpowered
## clear out the global make file parsing vars (LOCAL_
include $(CLEAR_VARS)
## first module we want to declare
LOCAL_MODULE := Superpowered
## architecture based sources
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
	LOCAL_SRC_FILES := $(SUPERPOWERED_PATH)/libSuperpoweredARM.a
else
	ifeq ($(TARGET_ARCH_ABI),arm64-v8a)
		LOCAL_SRC_FILES := $(SUPERPOWERED_PATH)/libSuperpoweredARM64.a
	else
		LOCAL_SRC_FILES := $(SUPERPOWERED_PATH)/libSuperpoweredX86.a
	endif
endif
## point at a make file for static libraries - does all the stuff we need to bundle this guy in
include $(PREBUILT_STATIC_LIBRARY)
## clear that global space again
include $(CLEAR_VARS)
## name for our module that includes our sources
LOCAL_MODULE := SuperpoweredExample  
## declare our source files - these are our cpp/c files
LOCAL_SRC_FILES := \
    SuperpoweredExample.cpp \
    $(SUPERPOWERED_PATH)/SuperpoweredAndroidAudioIO.cpp
LOCAL_C_INCLUDES += $(SUPERPOWERED_PATH)
## linking the android libraries - note openSLES...
LOCAL_LDLIBS := -llog -landroid -lOpenSLES
## include the superpowered static library in our final lib
LOCAL_STATIC_LIBRARIES := Superpowered
## i have no idea
LOCAL_CFLAGS = -O3
## do the build shared library
include $(BUILD_SHARED_LIBRARY)
