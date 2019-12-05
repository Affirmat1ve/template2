package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest {
  /**
   * ожидается 4 таких теста
   */

      /*
  @Test
  public void mulDD() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }

  @Test(expected = RuntimeException.class)
  public void mulDD2() {
    Matrix m1 = new DenseMatrix("m3.txt");
    Matrix m2 = new DenseMatrix("m1.txt");
    m1.mul(m2);
  }

  @Test
  public void mulDD3() {
    Matrix m4 = new DenseMatrix("m4.txt");
    Matrix m5 = new DenseMatrix("m5.txt");
    Matrix expected = new DenseMatrix("result45.txt");
    assertEquals(expected, m4.mul(m5));
  }

  @Test //small sparse
  public void mulSSEx1() {
    Matrix m1 = new SparseMatrix("SparseA1.txt");
    Matrix m2 = new SparseMatrix("SparseA2.txt");
    Matrix actual = m1.mul(m2);
    Matrix expected = new SparseMatrix("ResA1xA2.txt");
    System.out.println("expected:" + (expected).toString());
    System.out.println("actual:" + ((SparseMatrix) actual).toString());
    assertEquals(expected, actual);
  }
*/

  @Test
  public void mulSS() {
    Matrix m1 = new SparseMatrix("m1.txt");
    Matrix m2 = new SparseMatrix("m2.txt");
    Matrix expected = new SparseMatrix("result.txt");
    Matrix actual = m1.mul(m2);
    System.out.println("expected:" + (expected).toString());
    System.out.println("actual:" + ((SparseMatrix) actual).toString());
    assertEquals(expected, m1.mul(m2));
  }
/*
  @Test //сколько по времени умножается 2000x2000
  public void MultyMulSS() {
    Matrix m1 = new SparseMatrix("sparse1.txt");
    Matrix m2 = new SparseMatrix("sparse2.txt");

    long start = System.currentTimeMillis();
    m1.mul(m2);
    long finish = System.currentTimeMillis();
    System.out.println(finish - start);
  }
*/
  //Тест умножения плотной матрицы на разреженную
@Test
  public void mulDSEx3() {
   Matrix m1 = new DenseMatrix("m3.txt");
   Matrix m2 = new SparseMatrix("m4.txt");
   Matrix actual = m1.mul(m2);
   Matrix expected = new SparseMatrix("result2.txt");
   System.out.println("expected:" + (expected).toString());
   System.out.println("actual:" + ((SparseMatrix) actual).toString());
   assertEquals(expected, m1.mul(m2));
  }

  @Test
  public void mulDSEx1() {
    Matrix m1 = new DenseMatrix("sm1.txt");
    Matrix m2 = new SparseMatrix("sm2.txt");
    Matrix actual = m1.mul(m2);
    Matrix expected = new SparseMatrix("result3.txt");
    System.out.println("expected:" + (expected).toString());
    System.out.println("actual:" + ((SparseMatrix) actual).toString());
    assertEquals(expected, m1.mul(m2));
  }

  @Test  //Тест умножения разреженной матрицы и плотной
  public void mulSDEx1() {
    Matrix m1 = new SparseMatrix("sm1.txt");
    Matrix m2 = new DenseMatrix("sm2.txt");
    Matrix actual=m1.mul(m2);
    Matrix expected=new SparseMatrix("result3.txt");
    System.out.println("expected:"+(expected).toString());
    System.out.println("actual:"+((SparseMatrix)actual).toString());
    assertEquals(expected, actual);
  }


}
