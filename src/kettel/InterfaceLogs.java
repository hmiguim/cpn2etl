package kettel;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public interface InterfaceLogs<T> {

    public void readConfig();

    public ArrayList<T> getT();

    public void overrideConfig();
}
