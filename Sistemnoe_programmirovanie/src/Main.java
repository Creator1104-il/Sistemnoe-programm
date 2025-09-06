import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите параметр а: ");
        int a = in.nextInt();
        System.out.print("введите параметр b: ");
        int b = in.nextInt();
        int k = Perviq(a, b);
        int p = Vtoroq(a, b);
        int l = Tretiq(a, b);
        double d = Chetvertiq(a, b);
        int s = Patyq(a, b);
        int q = Schestoiq(a, b);
        int y = Semq(a,b);
        System.out.println("Решение первого метода: "+ k);
        System.out.println("Решение второго метода: "+ p);
        System.out.println("Решение третьего метода: "+ l);
        System.out.println("Решение четвертого метода: "+ d);
        System.out.println("Решение пятого метода: "+ s);
        System.out.println("Решение шестого метода: "+ q);
        System.out.println("Решение седьмого метода: "+ y);
    }
    //Метод сложения (итеративный подход)
    public static int Perviq(int a, int b){
        int res = 0;
        for (int i = 0; i < Math.abs(b); i++) {
            res += Math.abs(a);
        }
        if ((a < 0 && b > 0) || (a > 0 && b < 0)) {
            return -res;
        }
        return res;
    }
    //Рекурсивный метод сложения
    public static int Vtoroq(int a, int b) {
        if (b == 0) return 0;
        if (b > 0) return a + Vtoroq(a, b - 1);
        return - Vtoroq(a, -b);
    }
    //Метод с использованием битовых операций
    public static int Tretiq(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                result += a;
            }
            a <<= 1;
            b >>>= 1;
        }
        return result;
    }
    //Метод с использованием деления
    public static double Chetvertiq(int a, int b) {
        if (b == 0) return 0;
        return a / (1.0 / b); // a ÷ (1/b) = a × b
    }
    //Метод русского крестьянского умножения
    public static int Patyq(int a, int b) {
        int result = 0;
        while (b > 0) {
            if (b % 2 == 1) {
                result += a;
            }
            a <<= 1;
            b >>= 1;
        }
        return result;
    }
    //Метод с использованием стримов
    public static int Schestoiq(int a, int b) {
        return IntStream.generate(() -> Math.abs(a))
                .limit(Math.abs(b))
                .sum() * Integer.signum(a) * Integer.signum(b);
    }
    //Метод с использованием AtomicInteger
    public static int Semq(int a, int b) {
        AtomicInteger counter = new AtomicInteger(Math.abs(b));
        AtomicInteger result = new AtomicInteger(0);

        while (counter.getAndDecrement() > 0) {
            result.addAndGet(Math.abs(a));
        }

        return ((a < 0) ^ (b < 0)) ? -result.get() : result.get();
    }
}