//
// Created by zhao on 2023/2/4.
//
#include "zhao_algorithmMagic_utils_ASMath.h"
#include <cmath>
/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    avg
 * Signature: (I[D)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_avg_1C__I_3D
        (JNIEnv *jniEnv, jclass, jint length, jdoubleArray doubles) {
    jdouble *v = jniEnv->GetDoubleArrayElements(doubles, JNI_FALSE);
    jdouble res = v[0];
    for (int i = 1; i < length; ++i) {
        res += v[i];
    }
    return res / length;
}

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    avg
 * Signature: (I[I)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_avg_1C__I_3I
        (JNIEnv *jniEnv, jclass, jint length, jintArray ints) {
    jint *v = jniEnv->GetIntArrayElements(ints, JNI_FALSE);
    jint res = v[0];
    for (int i = 1; i < length; ++i) {
        res += v[i];
    }
    return res / (jdouble) length;
}


/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    CrossMultiplication_C
 * Signature: (II[I[I[I)V
 */
JNIEXPORT void JNICALL Java_zhao_algorithmMagic_utils_ASMath_CrossMultiplication_1C__II_3II_3I_3I
        (JNIEnv *JNIEnv1, jclass, jint jint1Size, jint jint2Size, jintArray res, jint length, jintArray ints1,
         jintArray ints2) {
    int now = -1;
    jint *v1 = JNIEnv1->GetIntArrayElements(ints1, JNI_FALSE);
    jint *v2 = JNIEnv1->GetIntArrayElements(ints2, JNI_FALSE);
    int resSize = jint1Size * (jint2Size - 1);
    jint res1[length];
    for (int i1 = 0; i1 < jint1Size; ++i1) {
        int temp = v1[i1];
        for (int i2 = 0; i2 < jint2Size; ++i2) {
            if (i2 != i1) res1[++now] = temp * v2[i2];
        }
    }
    JNIEnv1->SetIntArrayRegion(res, 0, resSize, res1);
}

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    CrossMultiplication_C
 * Signature: (II[D[D[D)V
 */
JNIEXPORT void JNICALL Java_zhao_algorithmMagic_utils_ASMath_CrossMultiplication_1C__II_3DI_3D_3D
        (JNIEnv *JNIEnv1, jclass, jint jint1Size, jint jint2Size, jdoubleArray res, jint length, jdoubleArray doubles1,
         jdoubleArray doubles2) {
    int now = -1;
    jdouble *v1 = JNIEnv1->GetDoubleArrayElements(doubles1, JNI_FALSE);
    jdouble *v2 = JNIEnv1->GetDoubleArrayElements(doubles2, JNI_FALSE);
    jdouble res1[length];
    for (int i1 = 0; i1 < jint1Size; ++i1) {
        double temp = v1[i1];
        for (int i2 = 0; i2 < jint2Size; ++i2) {
            if (i2 != i1) res1[++now] = temp * v2[i2];
        }
    }
    JNIEnv1->SetDoubleArrayRegion(res, 0, length, res1);
}

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    correlationCoefficient_C
 * Signature: ([I[II)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_correlationCoefficient_1C___3I_3II
        (JNIEnv *jniEnv, jclass, jintArray jintArray1, jintArray jintArray2, jint length) {
    jint Sx = 0; // 第一个序列的总和
    jint Sy = 0; // 第二个序列的总和
    jint Sxy = 0; // 双序列元素之乘积的总和
    jint SxF2 = 0; // 第一个序列的平方总和
    jint SyF2 = 0; // 第二个序列的平方总和
    jint *doubles1 = jniEnv->GetIntArrayElements(jintArray1, JNI_FALSE);
    jint *doubles2 = jniEnv->GetIntArrayElements(jintArray2, JNI_FALSE);
    // 开始计算
    for (int i = 0; i < length; i++) {
        jint x = doubles1[i];
        jint y = doubles2[i];
        Sx += x;
        Sy += y;
        Sxy += x * y;
        SxF2 += x * x;
        SyF2 += y * y;
    }
    return ((length * Sxy) - (Sx * Sy)) / (sqrt(length * SxF2 - (Sx * Sx)) * sqrt(length * SyF2 - (Sy * Sy)));
}

/*
 * Class:     zhao_algorithmMagic_utils_ASMath
 * Method:    correlationCoefficient_C
 * Signature: ([D[DI)D
 */
JNIEXPORT jdouble JNICALL Java_zhao_algorithmMagic_utils_ASMath_correlationCoefficient_1C___3D_3DI
        (JNIEnv *jniEnv, jclass, jdoubleArray jdoubleArray1, jdoubleArray jdoubleArray2, jint length) {
    double Sx = 0; // 第一个序列的总和
    double Sy = 0; // 第二个序列的总和
    double Sxy = 0; // 双序列元素之乘积的总和
    double SxF2 = 0; // 第一个序列的平方总和
    double SyF2 = 0; // 第二个序列的平方总和
    jdouble *doubles1 = jniEnv->GetDoubleArrayElements(jdoubleArray1, JNI_FALSE);
    jdouble *doubles2 = jniEnv->GetDoubleArrayElements(jdoubleArray2, JNI_FALSE);
    // 开始计算
    for (int i = 0; i < length; i++) {
        double x = doubles1[i];
        double y = doubles2[i];
        Sx += x;
        Sy += y;
        Sxy += x * y;
        SxF2 += x * x;
        SyF2 += y * y;
    }
    return ((length * Sxy) - (Sx * Sy)) / (sqrt(length * SxF2 - (Sx * Sx)) * sqrt(length * SyF2 - (Sy * Sy)));
}
