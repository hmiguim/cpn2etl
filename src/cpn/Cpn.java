package cpn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

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
     * Method that produces a HashMap with the pair {@code <String,Transition>}
     * from the {@link Page} {@code p} parameter, given all the modules that
     * page have. In case of the inexistence of modules on that page this method
     * returns a 0-length HashMap.
     *
     * @param p Page to be dissected to obtain the modules
     * @return A HashMap with the results of the dissection
     */
    public HashMap<String, Transition> getModulesPerPage(Page p) {

        HashMap<String, Transition> results = new HashMap<>();

        for (Transition t : p.getTransitions().values()) {
            if (t.haveSubPage()) {
                results.put(t.getId(), t);
            }
        }

        return results;
    }

    /**
     * Method that produces a Collection of {@code Transition} from the main
     * page given all the modules that page have. In case of the inexistence of
     * modules on that page this method returns a 0-length HashMap.
     *
     * @return A Collection with the results of the dissection
     */
    public Collection<Transition> getModulesMainPage() {

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
                if (t.haveSubPage()) {
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
        cpnStats.setModules(this.getModulesMainPage().size());
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
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.pages);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cpn other = (Cpn) obj;
        return Objects.equals(this.pages, other.pages);
    }

    @Override
    public Cpn clone() {
        return new Cpn(this);
    }
}
