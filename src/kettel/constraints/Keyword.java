/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel.constraints;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Keyword {
    
    private String keyword;
    private boolean keywordPresence;
    private int count;

    public Keyword() {}
    
    public Keyword(String keyword, boolean keywordPresence, int count) {
        this.keyword = keyword;
        this.keywordPresence = keywordPresence;
        this.count = count;
    }
    
    public Keyword(Keyword key) {
        this.keyword = key.getKeyword();
        this.keywordPresence = key.isKeywordPresence();
        this.count = key.getCount();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isKeywordPresence() {
        return keywordPresence;
    }

    public void setKeywordPresence(boolean keywordPresence) {
        this.keywordPresence = keywordPresence;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.keyword);
        hash = 53 * hash + (this.keywordPresence ? 1 : 0);
        hash = 53 * hash + this.count;
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
        final Keyword other = (Keyword) obj;
        if (!Objects.equals(this.keyword, other.keyword)) {
            return false;
        }
        if (this.keywordPresence != other.keywordPresence) {
            return false;
        }
        return this.count == other.count;
    }

    @Override
    public String toString() {
        return "Keywords{" + "keyword=" + keyword + ", keywordPresence=" + keywordPresence + ", count=" + count + '}';
    }
    
    @Override
    public Keyword clone() {
        return new Keyword(this);
    }
    
}
