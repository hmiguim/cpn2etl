package cpn;

/**
 *
 * @author hmg
 */
public class Stats {

    private int places;
    private int transitions;
    private int uniquePlaces;
    private int pages;
    private int subPages;
    private int modules;
    private int arcs;
    private int placesInput;
    private int placesOutput;
    private int placesIO;

    public Stats() {
        this.places = 0;
        this.transitions = 0;
        this.uniquePlaces = 0;
        this.pages = 0;
        this.subPages = 0;
        this.arcs = 0;
        this.placesInput = 0;
        this.placesOutput = 0;
        this.placesIO = 0;
        this.modules = 0;
    }

    public Stats(int places, int transitions, int uniquePlaces, int pages, int subPages, int modules, int arcs, int placesInput, int placesOutput, int placesIO) {
        this.places = places;
        this.transitions = transitions;
        this.uniquePlaces = uniquePlaces;
        this.pages = pages;
        this.subPages = subPages;
        this.modules = modules;
        this.arcs = arcs;
        this.placesInput = placesInput;
        this.placesOutput = placesOutput;
        this.placesIO = placesIO;
    }

    public Stats(Stats cpn) {
        this.places = cpn.getPlaces();
        this.transitions = cpn.getTransitions();
        this.uniquePlaces = cpn.getUniquePlaces();
        this.pages = cpn.getPages();
        this.subPages = cpn.getSubPages();
        this.modules = cpn.getModules();
        this.arcs = cpn.getArcs();
        this.placesInput = cpn.getPlacesInput();
        this.placesOutput = cpn.getPlacesOutput();
        this.placesIO = cpn.getPlacesIO();
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getTransitions() {
        return transitions;
    }

    public void setTransitions(int transitions) {
        this.transitions = transitions;
    }

    public int getUniquePlaces() {
        return uniquePlaces;
    }

    public void setUniquePlaces(int uniquePlaces) {
        this.uniquePlaces = uniquePlaces;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSubPages() {
        return subPages;
    }

    public void setSubPages(int subPages) {
        this.subPages = subPages;
    }

    public int getModules() {
        return modules;
    }

    public void setModules(int modules) {
        this.modules = modules;
    }

    public int getArcs() {
        return arcs;
    }

    public void setArcs(int arcs) {
        this.arcs = arcs;
    }

    public int getPlacesInput() {
        return placesInput;
    }

    public void setPlacesInput(int placesInput) {
        this.placesInput = placesInput;
    }

    public int getPlacesOutput() {
        return placesOutput;
    }

    public void setPlacesOutput(int placesOutput) {
        this.placesOutput = placesOutput;
    }

    public int getPlacesIO() {
        return placesIO;
    }

    public void setPlacesIO(int placesIO) {
        this.placesIO = placesIO;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.places;
        hash = 73 * hash + this.transitions;
        hash = 73 * hash + this.uniquePlaces;
        hash = 73 * hash + this.pages;
        hash = 73 * hash + this.subPages;
        hash = 73 * hash + this.modules;
        hash = 73 * hash + this.arcs;
        hash = 73 * hash + this.placesInput;
        hash = 73 * hash + this.placesOutput;
        hash = 73 * hash + this.placesIO;
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
        final Stats other = (Stats) obj;
        if (this.places != other.places) {
            return false;
        }
        if (this.transitions != other.transitions) {
            return false;
        }
        if (this.uniquePlaces != other.uniquePlaces) {
            return false;
        }
        if (this.pages != other.pages) {
            return false;
        }
        if (this.subPages != other.subPages) {
            return false;
        }
        if (this.modules != other.modules) {
            return false;
        }
        if (this.arcs != other.arcs) {
            return false;
        }
        if (this.placesInput != other.placesInput) {
            return false;
        }
        if (this.placesOutput != other.placesOutput) {
            return false;
        }
        return this.placesIO == other.placesIO;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Places: \t\t").append(places).append(" \n");
        str.append("Transitions: \t\t").append(transitions).append("\n");
        str.append("Arcs: \t\t").append(arcs).append("\n");
        str.append("Pages: \t\t").append(pages).append("\n");
        str.append("Subpages: \t\t").append(subPages).append("\n");
        str.append("Modules: \t\t").append(modules).append("\n");
        str.append("Places Input: \t\t").append(placesInput).append("\n");
        str.append("Places Output: \t\t").append(placesOutput).append("\n");
        str.append("Places I/O: \t\t").append(placesIO).append("\n");
        str.append("Unique Places: \t\t").append(uniquePlaces).append("\n");

        return str.toString();
    }

    @Override
    public Stats clone() {
        return new Stats(this);
    }
}
