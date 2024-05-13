import java.util.Vector;
import java.util.function.BiFunction;

public class EulerMethod {

    private final BiFunction<Double, Double, Double> func;
    private final double x0, xn, y0, eps;
    private double[] xi, yi, xi2, yi2;
    private double h;
    public double[] eulerX, eulerY;

    EulerMethod(BiFunction<Double, Double, Double> func, double h, double x0, double xn, double y0, double eps){
        this.func = func;
        this.h = h;
        this.x0 = x0;
        this.xn = xn;
        this.y0 = y0;
        this.eps = eps;
    }

    public void solve(){
        double[][] datah = trySolve(h);
        double[][] datah2 = trySolve(h/2);
        while (!checkAccuracy(datah, datah2)){
            datah = datah2;
            h = h/2;
            datah2 = trySolve(h/2);
        }
        System.out.println("-----------------------");
        System.out.println("Метод Эйлера");
        System.out.println("Решение с заданной точностью найдено при шаге h = " + h/2);
//        for(int i = 0; i < datah2[0].length; i++){
//            System.out.println(i + " " + datah2[0][i] + " " + datah2[1][i]);
//        }
        eulerX = datah2[0];
        eulerY = datah2[1];
    }

    public double[][] trySolve(double h){
        int n = (int) ((xn - x0) / h) + 1;
        double[][] res = new double[2][n];;
        double x = x0, y = y0, f;
        for(int i = 0; i < n; i++){
            f = func.apply(x, y);
            res[0][i] = x;
            res[1][i] = y;
            x += h;
            y += h * f;
        }
        return res;
    }

    private boolean checkAccuracy(double[][] data, double[][] data2){
        int p = 1; //первый порядок точности
        int n = (int) ((xn - x0) / h) + 1;
        for(int i = 0; i < n; i++){
            if(Math.abs(data[1][i] - data2[1][i*2]) / (Math.pow(2, p) - 1) > eps){
                return false;
            }
        }
        return true;
    }

}