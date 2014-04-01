package utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hmg
 */
public class Utilities {

    private static final String _USER = "cpn2etl";

    public static String getUser() {
        return Utilities._USER;
    }

    public static String today() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        String today = sdf.format(date);

        return today;
    }

    public static String[] normalizeAxis(double x, double y) {

        String[] normalized_axis = {normalizeX(x), normalizeY(y)};

        return normalized_axis;
    }

    private static String normalizeX(double axis) {
        String str;

        if (axis < 0) {
            axis *= -1;
        } else if (axis == 0) {
            axis += 60;
        }

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(axis);

        return str;
    }

    private static String normalizeY(double axis) {
        String str;

        if (axis < 0) {
            axis *= -1;
        }

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(axis);

        return str;
    }

}
