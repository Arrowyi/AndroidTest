#include <jni.h>
#include <string>
#include<iostream>



void addressAndVarTestDo(JNIEnv *env, jstring jtext, std::string& text)
{
//    std::string string("aa");
//    text = string;

    const char *p = env->GetStringUTFChars((jstring)jtext, 0);
    if (p)
    {
        text = p;
        env->ReleaseStringUTFChars((jstring)jtext, p);
        const char* q = text.c_str();
        std::cout<<q<<std::endl;
    }
}

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_gbd_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void
JNICALL
Java_com_example_gbd_myapplication_MainActivity_addressAndVarTest(
        JNIEnv *env,
        jobject /* this */, jstring text) {
    std::string testText;
    addressAndVarTestDo(env, text, testText);
    std::cout<<testText<<std::endl;
}






