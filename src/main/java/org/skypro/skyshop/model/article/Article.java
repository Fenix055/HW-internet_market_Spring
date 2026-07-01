package org.skypro.skyshop.model.article;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;


public final class Article implements Searchable{
    private final String name;
    private final String text;
    private final UUID id;

    public Article(String name, String text) {
        this.name = name;
        this.text = text;
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return name + '\n' + text;
    }
    @Override
    public String getSearchableName() {return name;}
    @Override
    @JsonIgnore
    public String getSearchableType() {return "ARTICLE";};
    @Override
    @JsonIgnore
    public String getSearchableTerm() {return toString();};
    @Override
    public UUID getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(name, article.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
