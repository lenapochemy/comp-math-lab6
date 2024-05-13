import methods.EulerMethod;
import methods.Methods;
import methods.MilneMethod;
import methods.RungeKuttaMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        ScannerManager scannerManager = new ScannerManager(new Scanner(System.in));

        Map<Integer, BiFunction<Double, Double, Double>> functions = new HashMap<>();
        functions.put(1, (x, y) -> y + (1 + x) * y * y);
        functions.put(2, (x, y) -> (2 * x * y) / (x * x - 1));
        functions.put(3, (x, y) -> - (2 * y + 1) * (1/ Math.tan(x)));
        functions.put(4, (x, y) -> (y - 1) / (x * x + x));

        String[] funcString = {
                "y'=y+(1+x)y²",
                "y'=2xy/(x²-1)",
                "y'=-(2y+1)*ctg(x)",
                "y'=(y-1)/(x²+x)"
        };

        int num = scannerManager.sayFunctionNumber(funcString);
        double x0 = scannerManager.sayX0(num);
        double xn = scannerManager.sayXn(x0, num);
        double y0 = scannerManager.sayDoubleNumber("начальное условие - значение для точки " + x0);
        double h = scannerManager.sayDoubleNumber("начального шага");
        double eps = scannerManager.sayEpsilon();
//        int num = 1;
//        double x0 = 1, xn = 2, y0 = -1, h = 0.1, eps = 0.1;

        double[] cFunc = {
                0,
                -Math.exp(x0) * (1/y0 + x0),
                y0 / (x0 * x0 - 1),
                (2 * y0 + 1) * Math.sin(x0) * Math.sin(x0),
                (y0 * (x0 + 1) - 1) / x0
        };

        Map<Integer, BiFunction<Double, Double, Double>> realFunctions = new HashMap<>();
        realFunctions.put(1, (c, a) -> - Math.exp(a) / (a * Math.exp(a) + c));
        realFunctions.put(2, (c, a) -> c * (a * a - 1));
        realFunctions.put(3, (c, a) -> (c / (2 * Math.sin(a) * Math.sin(a)) - 0.5));
        realFunctions.put(4, (c, a) -> (c * a + 1) / (a + 1));


        BiFunction<Double, Double, Double> func = functions.get(num);
        Methods methods = scannerManager.sayMethod();
        Chart chart = new Chart();

        int n = (int) ((xn - x0) / h);
        double[] x = new double[n+1];
        double[] y = new double[n+1];
        double xi = x0, c;

        for(int i = 0; i <= n; i++){
            x[i] = xi;
            c = cFunc[num];
            y[i] = realFunctions.get(num).apply(c, xi);
            System.out.println(x[i] + " " + y[i]);
            xi += h;
        }

        double[] euler = null, runge = null, milne = null;

        switch (methods){
            case EULER -> {
                EulerMethod eulerMethod = new EulerMethod(func, h, x0, xn, y0, eps);
                eulerMethod.solve();
                euler = eulerMethod.eulerY;
                chart.drawGraphics(x, y, eulerMethod.eulerX, eulerMethod.eulerY, "Метод Эйлера");
            }
            case RUNGE_KUTTA -> {
                RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(func, h, x0, xn, y0, eps);
                rungeKuttaMethod.solve();
                runge = rungeKuttaMethod.rkY;
                chart.drawGraphics(x, y, rungeKuttaMethod.rkX, rungeKuttaMethod.rkY, "Метод Рунге-Кутта");
            }
            case MILNE -> {
                RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(func, h, x0, xn, y0, eps);
                double[][] first = rungeKuttaMethod.findFirstFour();
                MilneMethod milneMethod = new MilneMethod(func, h, x0, xn, y0, eps, first[0], first[1]);
                milneMethod.solve();
                milne = milneMethod.milneY;
                chart.drawGraphics(x, y, milneMethod.milneX, milneMethod.milneY, "Метод Милна");
            }
            case ALL -> {
                EulerMethod eulerMethod = new EulerMethod(func, h, x0, xn, y0, eps);
                eulerMethod.solve();
                euler = eulerMethod.eulerY;
                RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(func, h, x0, xn, y0, eps);
                rungeKuttaMethod.solve();
                runge = rungeKuttaMethod.rkY;
                double[][] first = rungeKuttaMethod.findFirstFour();
                MilneMethod milneMethod = new MilneMethod(func, h, x0, xn, y0, eps, first[0], first[1]);
                milneMethod.solve();
                milne = milneMethod.milneY;
                chart.drawForAll(x, y, eulerMethod.eulerX, eulerMethod.eulerY, rungeKuttaMethod.rkX, rungeKuttaMethod.rkY,
                        milneMethod.milneX, milneMethod.milneY);
            }
        }

        printTable(n, x, y, euler, runge, milne);


    }

    public static void printTable(int n, double[] x, double[] y, double[] euler, double[] runge, double[] milne){
        int nEuler = 1, nRunge = 1;

        String res =  String.format(" %-3s|", "i");
        res += String.format(" %-7s|", "x");
        res += String.format(" %-17s|", "Точное значение");

        if(euler != null){
            res += String.format(" %-17s|", "Метод Эйлера");
            nEuler = euler.length / n;
        }
        if(runge != null){
            res += String.format(" %-17s|", "Метод Рунге-Кутта");
            nRunge = runge.length / n;
        }
        if(milne != null){
            res += String.format(" %-17s|", "Метод Милна");
        }

        System.out.println(res);

        for(int i = 0; i <= n; i++){
            res = String.format(" %-3s|", i);
            res += String.format(" %-7s|", rounding(x[i]));
            res += String.format(" %-17s|", rounding(y[i]));
            if(euler != null){
                res += String.format(" %-17s|",  rounding(euler[i * nEuler]));
            }
            if(runge != null){
                res += String.format(" %-17s|",  rounding(runge[i * nRunge]));
            }
            if(milne != null){
                res += String.format(" %-17s|", rounding(milne[i]));
            }
            System.out.println(res);
        }
    }

    public static double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(6, RoundingMode.HALF_UP);
        return help.doubleValue();
    }
}