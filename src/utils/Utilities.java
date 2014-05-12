package utils;

import cpn.Place;
import cpn.Transition;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
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

    public static String normalize(String str) {

        str = str.replace("/", "_");
        str = str.replace(" ", "_");

        return str;
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

    public static String removePointZero(double number) {

        String str;

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(number);

        return str;
    }

    private static String normalizeY(double axis) {
        String str;

        if (axis < 0) {
            axis *= -1;
        } else {
            axis += 100;
        }

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(axis);

        return str;
    }

    public static String[] middlePoint(String cx, String cy, String dx, String dy) {

        double x1, x2, y1, y2, r1, r2;

        x1 = Double.parseDouble(cx);
        y1 = Double.parseDouble(cy);

        x2 = Double.parseDouble(dx);
        y2 = Double.parseDouble(dy);

        r1 = (x1+x2)/2;
        r2 = (y1+y2)/2;
        
        String[] r = {removePointZero(r1), removePointZero(r2)};

        return r;
    }

    public static Collection<Place> normalizePlaces(Collection<Place> places) {
        double great_negative_y = 0;
        double great_negative_x = 0;
        double great_positive_y = 0;

        for (Place p : places) {
            if (p.getPosY() < great_negative_y) {
                great_negative_y = p.getPosY();
            }
            if (p.getPosX() < great_negative_x) {
                great_negative_x = p.getPosX();
            }
        }

        if (great_negative_x < 0 || great_negative_y < 0) {
            for (Place p : places) {
                p.setPosX(p.getPosX() + great_negative_x);
                p.setPosY(p.getPosY() + (great_negative_y * -1));

                if (p.getPosY() > great_positive_y) {
                    great_positive_y = p.getPosY();
                }
            }
        }

        for (Place p : places) {
            p.setPosY(Math.abs(p.getPosY() - great_positive_y));
        }

        return places;
    }

    public static Collection<Transition> normalizeTransitions(Collection<Transition> transitions) {
        double great_negative_y = 0;
        double great_negative_x = 0;
        double great_positive_y = 0;

        for (Transition t : transitions) {
            if (t.getPosY() < great_negative_y) {
                great_negative_y = t.getPosY();
            }
            if (t.getPosX() < great_negative_x) {
                great_negative_x = t.getPosX();
            }
        }

        if (great_negative_x < 0 || great_negative_y < 0) {
            for (Transition t : transitions) {
                t.setPosX(t.getPosX() + great_negative_x);
                t.setPosY(t.getPosY() + (great_negative_y * -1));

                if (t.getPosY() > great_positive_y) {
                    great_positive_y = t.getPosY();
                }
            }
        }

        for (Transition t : transitions) {
            t.setPosY(Math.abs(t.getPosY() - great_positive_y));
        }

        return transitions;
    }

}
