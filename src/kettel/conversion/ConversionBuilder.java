package kettel.conversion;

import cpn.Page;
import cpn.Place;
import cpn.Transition;
import java.util.ArrayList;
import kettel.Mapping;

/**
 *
 * @author hmg
 */
public class ConversionBuilder {

    public ConversionBuilder() {
    }

    public ArrayList<Mapping> convertModule(Transition module) {

        String name = module.getSubPageInfo().getPage().getName();
        ArrayList<Mapping> mapping = new ArrayList<>();

        switch (name) {

            case "SKP":
                mapping = this.convertSKP(module.getSubPageInfo().getPage());
                break;

        }

        return mapping;
    }

    private ArrayList<Mapping> convertSKP(Page p) {

        ArrayList<Mapping> maps = new ArrayList<>();

        for (Place place : p.getPlaces().values()) {

            if (place.getText().contains("Lookup Table")) {
                Mapping map = new Mapping(place.getText(), "DBLookup");

                maps.add(map);
            }

        }
        return maps;
    }
}
