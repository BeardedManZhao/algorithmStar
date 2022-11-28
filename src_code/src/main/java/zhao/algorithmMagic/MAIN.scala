package zhao.algorithmMagic

import zhao.algorithmMagic.utils.ASMath

/**
 * 示例代码文件
 */
object MAIN {
  def main(args: Array[String]): Unit = {
    println(ASMath.sort(false, 1, 2, 3, 4, 5, 10, 9, 8, 7, 100, 13).mkString("Array(", ", ", ")"))
  }
}
