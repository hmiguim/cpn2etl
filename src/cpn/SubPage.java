package cpn;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class SubPage {

    private String id;
    private String name;
    private String pageRef;
    private Page page;

    public SubPage() {
    }

    public SubPage(String id, String name, String pageRef, Page page) {
        this.id = id;
        this.name = name;
        this.pageRef = pageRef;
        this.page = page;
    }

    public SubPage(SubPage s) {
        this.id = s.getId();
        this.name = s.getName();
        this.pageRef = s.getPageRef();
        this.page = s.getPage();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPageRef() {
        return pageRef;
    }

    public void setPageRef(String pageRef) {
        this.pageRef = pageRef;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.pageRef);
        hash = 67 * hash + Objects.hashCode(this.page);
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
        final SubPage other = (SubPage) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.pageRef, other.pageRef)) {
            return false;
        }
        return Objects.equals(this.page, other.page);
    }

    @Override
    public SubPage clone() {
        return new SubPage(this);
    }
}
