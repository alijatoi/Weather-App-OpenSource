#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_weatherappusingopenmeteo_presentation_fragments_SearchFragment_getApi(JNIEnv *env, jobject instance) {
return (*env)-> NewStringUTF(env, "Your Key");
}