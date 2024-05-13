import java.util.function.BiFunction;

public class RungeKuttaMethod {

    private final BiFunction<Double, Double, Double> func;
    private final double x0, xn, y0, eps;
    private double h;
    public double[] rkX, rkY;

    RungeKuttaMethod(BiFunction<Double, Double, Double> func, double h, double x0, double xn, double y0, double eps){
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
        System.out.println("Метод Рунге-Кутта");
        System.out.println("Решение с заданной точностью найдено при шаге h = " + h/2);
        System.out.println("-----------------------");
//        for(int i = 0; i < datah2[0].length; i++){
//            System.out.println(i + " " + datah2[0][i] + " " + datah2[1][i]);
//        }
        rkX = datah2[0];
        rkY = datah2[1];
    }

    public double[][] trySolve(double h){
        int n = (int) ((xn - x0) / h) + 1;
        double[][] res = new double[2][n];

        double x = x0, y = y0, f, k1, k2, k3, k4;
        for(int i = 0; i < n; i++){
            res[0][i] = x;
            res[1][i] = y;
            k1 = h * func.apply(x, y);
            k2 = h * func.apply(x + h/2, y + k1/2);
            k3 = h * func.apply(x + h/2, y + k2/2);
            k4 = h * func.apply(x + h, y + k3);

            y = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6 ;

            x += h;
        }
        return res;
    }

    private boolean checkAccuracy(double[][] data, double[][] data2){
        int p = 4; // порядок точности
        int n = (int) ((xn - x0) / h) + 1;
        for(int i = 0; i < n; i++){
            if(Math.abs(data[1][i] - data2[1][i*2]) / (Math.pow(2, p) - 1) > eps){
                return false;
            }
        }
        return true;
    }
    public double[][] findFirstFour(){
        double[][] res = new double[2][4];
        double x = x0, y = y0, k1, k2, k3, k4;
        for(int i = 0; i < 4; i++){
            k1 = h * func.apply(x, y);
            k2 = h * func.apply(x + h/2, y + k1/2);
            k3 = h * func.apply(x + h/2, y + k2/2);
            k4 = h * func.apply(x + h, y + k3);

            res[0][i] = x;
            res[1][i] = y;
            y = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6 ;
            x += h;
        }

        return res;
    }


}