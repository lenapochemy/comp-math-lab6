package methods;

import java.util.function.BiFunction;

public class MilneMethod {

    private final BiFunction<Double, Double, Double> func;
    private final double eps;
    private int n;
    private double h;

    public double[] milneX, milneY;
    private final double[] firstX, firstY, fi;

    public MilneMethod(BiFunction<Double, Double, Double> func, double h, double x0, double xn, double y0, double eps,
                double[] firstX, double[] firstY){
        this.func = func;
        this.h = h;
        this.eps = eps;

        this.firstX = firstX;
        this.firstY = firstY;
        n = (int) ((xn - x0) / h) + 1;
        while (n < 5) {
            this.h = this.h / 2;
            n = (int) ((xn - x0) / this.h) + 1;
        }
        milneX = new double[n];
        milneY = new double[n];
        fi = new double[n];
    }

    public void solve(){
        System.out.println("milne");
        System.out.println("Решение найдено при h = " + h);
        System.out.println("-------------------------");

        double[] xi = new double[n];
        double[] yi = new double[n];
        for(int i = 0; i < 4; i++){
            xi[i] = firstX[i];
            yi[i] = firstY[i];
            fi[i] = func.apply(xi[i], yi[i]);
//            System.out.println(i + " " + xi[i] + " " + yi[i]);
            milneX[i] = xi[i];
            milneY[i] = yi[i];
        }

        double yi_prog, yi_korr, f_prog;
        for(int i = 4; i < n; i++){
            xi[i] = xi[i-1] + h;
            boolean flag = true;
            yi_prog = yi[i - 4] + (4 * h / 3) * (2 * fi[i - 3] - fi[i - 2] + 2 * fi[i - 1]);
            while(flag) {
                f_prog = func.apply(xi[i], yi_prog);
                yi_korr = yi[i - 2] + (h / 3) * (fi[i - 2] + 4 * fi[i - 1] + f_prog);
//                System.out.println("check " + i + " " + xi[i] + " " + yi_prog + " " + f_prog + " " + yi_korr);

                if(Math.abs(yi_prog - yi_korr) < eps){
                    flag = false;
                } else {
                    yi_prog = yi_korr;
                }
            }
            yi[i] = yi_prog;
            fi[i] = func.apply(xi[i], yi[i]);
            milneX[i] = xi[i];
            milneY[i] = yi[i];
//            System.out.println(i + " " + xi[i] + " " + yi[i]);

        }
    }


}