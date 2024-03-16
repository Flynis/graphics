package ru.dyakun.picfilter.model;

public class Kernel {

    private final int[] kernel;
    private final int size;
    private int div;

    public Kernel(int size) {
        if(size % 2 == 0) {
            throw new IllegalArgumentException("Kernel size must be odd");
        }
        if(size < 3) {
            throw new IllegalArgumentException("Kernel is too small");
        }
        this.size = size;
        this.kernel = new int[size * size];
    }

    public int get(int x, int y) {
        return kernel[y * size + x];
    }

    public void set(int x, int y, int val) {
        kernel[y * size + x] = val;
    }

    public int getDiv() {
        return div;
    }

    public void setDiv(int div) {
        this.div = div;
    }

    public void copy(int[] arr) {
        if(arr.length > kernel.length) {
            throw new IllegalArgumentException("Too big array for kernel");
        }
        System.arraycopy(arr, 0, kernel, 0, arr.length);
    }

}
