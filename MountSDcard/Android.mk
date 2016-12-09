LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
# Module name should match apk name to be installed
LOCAL_PACKAGE_NAME := MountSdcard
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_CERTIFICATE := platform
#LOCAL_MULTILIB :=32
LOCAL_PRIVILEGED_MODULE := true
LOCAL_MODULE_PATH := $(TARGET_OUT_VENDOR_APPS)
include $(BUILD_PACKAGE)
