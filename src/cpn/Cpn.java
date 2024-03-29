package cpn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author hmg
 */
public class Cpn {

    private HashMap<String, Page> pages;

    public Cpn() {
    }

    public Cpn(HashMap<String, Page> pages) {
        this.pages = pages;
    }

    public Cpn(Cpn cpn) {
        this.pages = cpn.getPages();
    }

    public HashMap<String, Page> getPages() {
        return pages;
    }

    public void setPages(HashMap<String, Page> pages) {
        this.pages = pages;
    }
    
    /**
     * Method that produces a Collection of {@code Transition} from the main
     * page given all the patterns that page have. In case of the inexistence of
     * patterns on that page this method returns a 0-length HashMap.
     *
     * @return A Collection with the results of the main page dissection
     */
    public Collection<Transition> getPatternsMainPage() {

        ArrayList<Transition> results = new ArrayList<>();

        Collection<Page> pagesCollection = this.pages.values();

        for (Page p : pagesCollection) {
            if (p.getName().toLowerCase().equals("etl")) {
                for (Transition t : p.getTransitions().values()) {
                    results.add(t);
                }
            }
        }

        return results;
    }

    /**
     * Give all the important statistics about the CPN Page, like number of
     * places, number of arcs, number of transitions, among others counters
     *
     * @return A {@link Stats} class for the current CPN Page 
     */
    public Stats stats() {

        Stats cpnStats = new Stats();

        cpnStats.setPages(this.pages.size());

        int count_arcs = 0;
        int count_places = 0;
        int count_transitions = 0;
        int count_places_in = 0;
        int count_places_out = 0;
        int count_places_io = 0;
        int count_unique_places = 0;

        ArrayList<String> pageRefs = new ArrayList<>();

        Collection<Page> pages_collection = this.pages.values();

        for (Page p : pages_collection) {
            count_arcs += p.getArcs().size();
            count_places += p.getPlaces().size();

            Collection<Transition> transitions = p.getTransitions().values();

            for (Transition t : transitions) {
                if (t.isSubPage()) {
                    if (!pageRefs.contains(t.getSubPageInfo().getPageRef())) {
                        pageRefs.add(t.getSubPageInfo().getPageRef());
                    }
                } else {
                    count_transitions++;
                }
            }

            Collection<Place> places = p.getPlaces().values();
            for (Place place : places) {
                if (place.havePort()) {
                    switch (place.getPort().getType()) {
                        case "In":
                            count_places_in++;
                            break;
                        case "Out":
                            count_places_out++;
                            break;
                        case "I/O":
                            count_places_io++;
                            break;
                    }
                } else {
                    count_unique_places++;
                }
            }
        }

        cpnStats.setArcs(count_arcs);
        cpnStats.setModules(this.getPatternsMainPage().size());
        cpnStats.setPlaces(count_places);
        cpnStats.setPlacesIO(count_places_io);
        cpnStats.setPlacesInput(count_places_in);
        cpnStats.setPlacesOutput(count_places_out);
        cpnStats.setSubPages(pageRefs.size());
        cpnStats.setTransitions(count_transitions);
        cpnStats.setUniquePlaces(count_unique_places);

        return cpnStats;
    }

    @Override
    public Cpn clone() {
        return new Cpn(this);
    }
}
