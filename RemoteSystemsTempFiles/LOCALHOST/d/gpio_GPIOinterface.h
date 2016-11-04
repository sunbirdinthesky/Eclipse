/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class gpio_GPIOinterface */

#ifndef _Included_gpio_GPIOinterface
#define _Included_gpio_GPIOinterface
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     gpio_GPIOinterface
 * Method:    getUserFlag
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_getUserFlag
  (JNIEnv *, jclass);

/*
 * Class:     gpio_GPIOinterface
 * Method:    gpio
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_gpio
  (JNIEnv *, jclass);

/*
 * Class:     gpio_GPIOinterface
 * Method:    stepCtrl
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_stepCtrl
  (JNIEnv *, jclass);

/*
 * Class:     gpio_GPIOinterface
 * Method:    resetFlag
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_gpio_GPIOinterface_resetFlag
  (JNIEnv *, jclass);

/*
 * Class:     gpio_GPIOinterface
 * Method:    getCardNumber
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_gpio_GPIOinterface_getCardNumber
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif