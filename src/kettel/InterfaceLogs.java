package kettel;

import java.util.ArrayList;

/**
 *
 * @author hmg
 * @param <T>
 */
public interface InterfaceLogs<T> {

    public void readConfig();

    public ArrayList<T> getT();

    public void overrideConfig();
}
