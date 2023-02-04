cmake_minimum_required(VERSION 3.23)
project(AsLib)
set(CMAKE_CXX_STANDARD 11)
# 设置动态库源文件
add_library(AsLib SHARED ASMath.cpp)
# 设置头文件目录 第一个是Java运行时头文件 第二个是AS运行时头文件
include_directories(head AsHead)
