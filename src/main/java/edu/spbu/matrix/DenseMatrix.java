package edu.spbu.matrix;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix {
  public int height, width;
  public double[][] matrix;
  public int hachCode;

  private DenseMatrix(int height, int width, double[][] matrix) {
    this.height = height;
    this.width = width;
    this.matrix = matrix;
    this.hachCode = Arrays.deepHashCode(this.matrix);
  }

  /**
   * загружает матрицу из файла
   *
   * @param fileName - path to the text file with matrix
   */
  public DenseMatrix(String fileName) {
    this.width = 0;
    this.height = 0;
    LinkedList<double[]> rows = new LinkedList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line = br.readLine();
      double[] matrixRow = Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
      this.width = matrixRow.length;
      this.height = 1;
      rows.add(matrixRow);

      while ((line = br.readLine()) != null) {
        matrixRow = Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();

        rows.add(matrixRow);
        ++this.height;
      }

      this.matrix = new double[this.height][this.width];
      for (int i = 0; i < this.height; ++i) {
        this.matrix[i] = rows.get(i);
      }
      this.hachCode = Arrays.deepHashCode(this.matrix);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * однопоточное умнджение матриц
   */
  @Override public Matrix mul(Matrix o)
  {
    if(o instanceof DenseMatrix)
      return this.mul((DenseMatrix) o);
    else if(o instanceof SparseMatrix)
    {
      return this.mul ((SparseMatrix)o);
    }
    else throw new RuntimeException("Применяемый операнд является представителем класса иного происхождения");

  }


  public Matrix mul(DenseMatrix other) {
    if (other instanceof DenseMatrix && this.getWidth() == other.getHeight()) {
      DenseMatrix dm = (DenseMatrix) other;
      int newHeight = this.height, newWidth = dm.width;
      double[][] matrix = new double[newHeight][newWidth];


      matrix = new double[newHeight][newWidth];
      for (int i = 0; i < newHeight; ++i) {
        for (int j = 0; j < newWidth; ++j) {
          for (int k = 0; k < this.width; ++k) {
            matrix[i][j] += this.matrix[i][k] * dm.matrix[k][j];
          }
        }
      }
      return new DenseMatrix(newHeight, newWidth, matrix);
    }
    throw new IllegalArgumentException(String.format("Can't multiply matrices of size (%n, %n) and (%n, %n)", this.width, this.height, other.getHeight(), other.getWidth()));
  }


 /* public DenseMatrix mul(SparseMatrix SMtx){
    if(width==0&&SMtx.height==0) return null;
    if(width==SMtx.height)
    {
      double[][] res=new double[height][SMtx.width];

      for(int i=0;i<height;i++)
      {
        for(Point p:SMtx.val.keySet())
        {
          for(int k=0;k<height;k++)
          {
            if(p.x==k)
            {
              res[i][p.y]+=matrix[i][k]*SMtx.val.get(p);
            }
          }
        }
      }
      return new DenseMatrix(res);
    }else throw new RuntimeException("Размеры матриц не отвечают матричному уможению.");
  } */


  public SparseMatrix mul(SparseMatrix SMtx) {
    if (width == SMtx.height && SMtx.val != null && matrix != null) {
      //double[][] res = new double[height][SMtx.width];
      HashMap<Point, Double> result = new HashMap<>();
      for (Point p : SMtx.val.keySet()) {
        for (int j = 0; j < height; j++) {
            {
              //res[j][p.y] += SMtx.val.get(p) * matrix[j][p.x];
              Point pn = new Point(j,p.y);
              double temp;
              if (result.containsKey(pn)) {
                temp = result.get(pn)+ SMtx.val.get(p) * matrix[j][p.x];
                if (temp == 0) result.remove(pn);
                else result.put(pn, temp);
              } else {
                temp = SMtx.val.get(p) * matrix[j][p.x];
                if ((temp != 0))
                result.put(pn, temp);
              }


            }
        }
      }

      return new SparseMatrix(result, height, SMtx.width);
    } else throw new RuntimeException("Размеры матриц не отвечают матричному уможению.");
  }



  /**
   * многопоточное умножение матриц
   *
   * @param o - another matrix to multiply by
   * @return resulting matrix of the multiplication
   */
  @Override
  public Matrix dmul(Matrix o) {
    return null;
  }

  /**
   * спавнивает с обоими вариантами
   *
   * @param o - an object to compare against
   * @return true if equals
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (o instanceof DenseMatrix) {
      DenseMatrix dm = (DenseMatrix) o;
      if (this.getHeight() != dm.getHeight() || this.getWidth() != dm.getWidth()) {
        return false;
      }

     // if (this.hachCode != dm.hachCode) {
      //  return false;
      //}

      for (int i = 0; i < this.getHeight(); ++i) {
        for (int j = 0; j < this.getWidth(); ++j) {
          if (this.matrix[i][j] != dm.matrix[i][j]) {
            return false;
          }
        }
      }
      return true;
    }

    if (o instanceof SparseMatrix) {
      System.out.println("FALSE");
      return false;
    }
    System.out.println("FALSE");
    return false;
  }

  public double get(int i, int j) {
    if (i < this.getHeight() && j < this.getWidth()) {
      return this.matrix[i][j];
    }
    return 0;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  //
  public DenseMatrix(double[][] input)
  {
    if (input.length > 0 )
    {
      matrix=input;
      height=input.length;
      width=input[0].length;
    }
  }


  public DenseMatrix transpose()
  {
    double[][] transposedDMtx=new double[width][height];
    for(int i=0;i<width;i++)
    {
      for(int j=0;j<height;j++)
      {
        transposedDMtx[i][j]=matrix[j][i];
      }
    }
    return new DenseMatrix(transposedDMtx);
  }

  @Override public String toString() {
    if(matrix==null) throw new RuntimeException("Встречена пустая матрица");
    StringBuilder resBuilder=new StringBuilder();
    resBuilder.append('\n');
    for(int i=0;i<height;i++) {
      resBuilder.append('[');
      for (int j = 0; j < width; j++) {
        resBuilder.append(matrix[i][j]);
        if (j < width - 1)
          resBuilder.append(" ");
      }
      resBuilder.append("]\n");

    }
    return resBuilder.toString();
  }


}