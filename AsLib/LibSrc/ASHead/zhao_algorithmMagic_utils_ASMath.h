/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class zhao_algorithmMagic_utils_ASMath */

#ifndef _Included_zhao_algorithmMagic_utils_ASMath
#define _Included_zhao_algorithmMagic_utils_ASMath
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    avg_C
 * Signature: (I[D)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_avg_1C__I_3D
        (JNIEnv *, jclass, jint, jdoubleArray);

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    avg_C
 * Signature: (I[I)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_avg_1C__I_3I
        (JNIEnv *, jclass, jint, jintArray);

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    CrossMultiplication_C
 * Signature: (II[II[I[I)V
 */
JNIEXPORT void JNICALL Java_zhao_algorithmMagic_utils_ASMath_CrossMultiplication_1C__II_3II_3I_3I
        (JNIEnv *, jclass, jint, jint, jintArray, jint, jintArray, jintArray);

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    CrossMultiplication_C
 * Signature: (II[DI[D[D)V
 */
JNIEXPORT void JNICALL Java_zhao_algorithmMagic_utils_ASMath_CrossMultiplication_1C__II_3DI_3D_3D
        (JNIEnv *, jclass, jint, jint, jdoubleArray, jint, jdoubleArray, jdoubleArray);

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    correlationCoefficient_C
 * Signature: ([I[II)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_correlationCoefficient_1C___3I_3II
        (JNIEnv *, jclass, jintArray, jintArray, jint);

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    correlationCoefficient_C
 * Signature: ([D[DI)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_correlationCoefficient_1C___3D_3DI
        (JNIEnv *, jclass, jdoubleArray, jdoubleArray, jint);

#ifdef __cplusplus
}
#endif
#endif
