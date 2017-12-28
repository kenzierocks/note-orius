package me.kenzierocks.noteorius.lowlevel;

public class NtOrMath {

    public static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    public static int gcd(int a, int b) {
        while (b > 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

}
