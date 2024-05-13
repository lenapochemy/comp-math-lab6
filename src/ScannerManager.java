
import java.util.*;

public class ScannerManager {
    private Scanner scanner;
    public ScannerManager(Scanner scanner){
        this.scanner = scanner;
    }

    public Methods sayMethod(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Выберите метод решения уравнения:");
                System.out.println("\t1. Метод Эйлера");
                System.out.println("\t2. Метод Рунге-Кутта");
                System.out.println("\t3. Метод Милна");
                System.out.println("\t4. Все методы");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "1" -> {
                        flag = true;
                        return Methods.EULER;
                    }
                    case "2" -> {
                        flag = true;
                        return Methods.RUNGE_KUTTA;
                    }
                    case "3" -> {
                        flag = true;
                        return Methods.MILNE;
                    }
                    case "4" -> {
                        flag = true;
                        return Methods.ALL;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть числом от 1 до 4");

            }
        }
        return null;
    }

    public int sayFunctionNumber(String[] functionStrings){
        int n = functionStrings.length;
        int num = 0;
        String sNum;
        while (num <= 0 || num > n){
            try {
                System.out.println("Выберите ОДУ для решения: ");
                for(int i = 0; i < n; i++){
                    System.out.println("\t" + (i+1) + ". "+ functionStrings[i]);
                }
                sNum = scanner.nextLine().trim();
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num <= 0 || num > n) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Номер функции должен быть положительным числом, не большим " + n );
            } catch (NullPointerException e){
                System.out.println("Номер функции не может быть пустым");
            }  catch (NumberFormatException e){
                System.out.println("Номер функции должен быть целым числом");
            }
        }
        return num;
    }

    public double sayEpsilon(){
        double num = 0;
        String sNum;
        while (num < 0.000001 || num > 1){
            try {
                System.out.print("Введите значение точности [0.000001; 1]: ");
                sNum = scanner.nextLine().trim();
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Double.parseDouble(sNum);
                if(num < 0.000001 || num > 1) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Значение точности должно быть положительным числом из промежутка [0.000001; 1]");
            } catch (NullPointerException e){
                System.out.println("Значение точности не может быть пустым");
            }   catch (NumberFormatException e){
                System.out.println("Количество итераций должно быть числом");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return num;
    }

    public double sayX0(int num){
        double a = sayDoubleNumber("левой границы интервала");
        if(num == 2){
            while(a == 1.0 || a == -1.0){
               System.out.println("Функция не определена в точке " + a + ", выберите другой интервал");
                a = sayDoubleNumber("левой границы интервала");
            }
//            while(a == -1.0){
//                System.out.println("Функция не определена в точке -1, выберите другой интервал");
//                a = sayDoubleNumber("левой границы интервала");
//            }
        }
        if(num == 4){
            while(a == 0 || a == -1.0){
                System.out.println("Функция не определена в точке " + a + ", выберите другой интервал");
                a = sayDoubleNumber("левой границы интервала");
            }
        }
        return a;
    }

    public double sayXn(double a, int num) {
        double b = a;
        boolean flag;
        if(num == 2) {
            flag = true;
        } else flag = false;

        while(b <= a || flag) {
            b = sayDoubleNumber("правой границы интервала");
            if(b <= a) System.out.println("Значение правой границы интервала должно быть больше левой, равной " + a);
            if(num == 2){
                if(a < 1 && b >= 1){
                    System.out.println("Функция не определена в точке 1, выберите другой интервал");
                    flag = true;
                } else {
                    if (a < -1 && b >= -1) {
                        System.out.println("Функция не определена в точке -1, выберите другой интервал");
                        flag = true;
                    } else flag = false;
                }
            }
            if(num == 4){
                if(a < 0 && b >= 0){
                    System.out.println("Функция не определена в точке 0, выберите другой интервал");
                    flag = true;
                } else {
                    if (a < -1 && b >= -1) {
                        System.out.println("Функция не определена в точке -1, выберите другой интервал");
                        flag = true;
                    } else flag = false;
                }
            }
        }
        return b;
    }

    public double sayY0(double x0){
        return sayDoubleNumber("начальное условие - значение для точки " + x0);
    }

    public double sayDoubleNumber(String name){
        double num = 0;
        String sNum;
        boolean flag = true;
        while (flag){
            try {
                System.out.print("Введите значение " + name +  " : ");
                sNum = scanner.nextLine().trim();
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Double.parseDouble(sNum);
                flag = false;
            } catch (NullPointerException e){
                System.out.println("Значение " + name +" не может быть пустым");
            }  catch (NumberFormatException e){
                System.out.println("Значение " + name + " должно быть числом");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return num;
    }

}