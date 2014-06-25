package skyprocstarter;

import java.util.Random;
import skyprocstarter.YourSaveFile.Settings;
import java.text.*;

public class RBS_Randomize {

    public static String createID(int Number) {
        NumberFormat formatter = new DecimalFormat("000");
        return (formatter.format(Number));
    }

    public static String createID(int Number, int digits) {
        NumberFormat formatter = new DecimalFormat("00");
        return (formatter.format(Number));
    }

    public static String createRandomID(String seed) {
        NumberFormat formatter = new DecimalFormat("000");
        return (formatter.format(toInt(seed, 1, (RBS_Main.amountBodyTypes) - 1)));
    }

    public static String createRandomID(String seed, int min, int max) {
        NumberFormat formatter = new DecimalFormat("000");
        String test = formatter.format(toInt(seed, min, max));
        return (test);
    }

    public static String createRandomID(int seed) {
        String string = String.valueOf(seed);
        NumberFormat formatter = new DecimalFormat("000");
        return (formatter.format(toInt(string, 1, (RBS_Main.amountBodyTypes) - 1)));
    }

    public static String createRandomID(int seed, int min, int max) {
        String string = String.valueOf(seed);
        NumberFormat formatter = new DecimalFormat("000");
        return (formatter.format(toInt(string, min, max)));
    }

    public static String toString(String string, int min, int max) {
        Random ran = new Random();
        int ascii = 0;
        for (int i = 0; i < string.length(); i++) {
            char dummy = string.charAt(i);
            ascii = ascii + (int) dummy;
        }

        if (SkyProcStarter.save.getBool(Settings.FIXED_RANDOMNESS_ON)) {
            ran = new Random((long) ascii);
        }
        int x = ran.nextInt(max - min) + min;
        NumberFormat formatter = new DecimalFormat("000");
        String s = formatter.format(x);
        return (s);
    }

    public static float toFloat(String string, int min, int max) {
        int ascii = 0;
        Random ran = new Random();
        for (int i = 0; i < string.length(); i++) {
            char dummy = string.charAt(i);
            ascii = ascii + (int) dummy;
        }
        if (SkyProcStarter.save.getBool(Settings.FIXED_RANDOMNESS_ON)) {
            ran = new Random((long) ascii);
        }
        int x = ran.nextInt(max - min) + min;
        return (x);
    }

    public static int toInt(String string, int min, int max) {
        Random ran = new Random();
        int ascii = 0;
        for (int i = 0; i < string.length(); i++) {
            char dummy = string.charAt(i);
            ascii = ascii + (int) dummy;
        }
        if (SkyProcStarter.save.getBool(Settings.FIXED_RANDOMNESS_ON)) {
            ran = new Random((long) ascii);
        }
        int x = ran.nextInt(max - min) + min;
        return (x);
    }

    

}
