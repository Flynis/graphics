package ru.dyakun.picfilter.transformations.base;

public class Matrix {

    private final int[] matrix;
    private final int width;
    private final int height;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[width * height];
    }

    public int get(int x, int y) {
        return matrix[y * width + x];
    }

    public void set(int x, int y, int val) {
        matrix[y * width + x] = val;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void copy(int[] arr) {
        if(arr.length > matrix.length) {
            throw new IllegalArgumentException("Array is bigger than matrix");
        }
        System.arraycopy(arr, 0, matrix, 0, arr.length);
    }

}
