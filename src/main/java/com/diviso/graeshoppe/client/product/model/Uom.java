package com.diviso.graeshoppe.client.product.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Uom.
 */

@Document(indexName = "uom")
public class Uom implements Serializable {

    private static final long serialVersionUID = 1L;
    
   
    private Long id;

 
    private String name;

   
    private String description;

   
    private Set<StockLine> stockLines = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Uom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Uom description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StockLine> getStockLines() {
        return stockLines;
    }

    public Uom stockLines(Set<StockLine> stockLines) {
        this.stockLines = stockLines;
        return this;
    }

    public Uom addStockLines(StockLine stockLine) {
        this.stockLines.add(stockLine);
        stockLine.setUom(this);
        return this;
    }

    public Uom removeStockLines(StockLine stockLine) {
        this.stockLines.remove(stockLine);
        stockLine.setUom(null);
        return this;
    }

    public void setStockLines(Set<StockLine> stockLines) {
        this.stockLines = stockLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Uom uom = (Uom) o;
        if (uom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Uom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
