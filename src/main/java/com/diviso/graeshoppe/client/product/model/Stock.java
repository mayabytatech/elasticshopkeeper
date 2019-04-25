package com.diviso.graeshoppe.client.product.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Stock.
 */

@Document(indexName = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    
  
    private Long id;

    
    private String reference;

   
    private Long deliveryNoteRef;

    private LocalDate dateOfStockUpdated;

    private Double storageCost;

   
    private Status status;

    private Set<StockLine> stockLines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Stock reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getDeliveryNoteRef() {
        return deliveryNoteRef;
    }

    public Stock deliveryNoteRef(Long deliveryNoteRef) {
        this.deliveryNoteRef = deliveryNoteRef;
        return this;
    }

    public void setDeliveryNoteRef(Long deliveryNoteRef) {
        this.deliveryNoteRef = deliveryNoteRef;
    }

    public LocalDate getDateOfStockUpdated() {
        return dateOfStockUpdated;
    }

    public Stock dateOfStockUpdated(LocalDate dateOfStockUpdated) {
        this.dateOfStockUpdated = dateOfStockUpdated;
        return this;
    }

    public void setDateOfStockUpdated(LocalDate dateOfStockUpdated) {
        this.dateOfStockUpdated = dateOfStockUpdated;
    }

    public Double getStorageCost() {
        return storageCost;
    }

    public Stock storageCost(Double storageCost) {
        this.storageCost = storageCost;
        return this;
    }

    public void setStorageCost(Double storageCost) {
        this.storageCost = storageCost;
    }

    public Status getStatus() {
        return status;
    }

    public Stock status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<StockLine> getStockLines() {
        return stockLines;
    }

    public Stock stockLines(Set<StockLine> stockLines) {
        this.stockLines = stockLines;
        return this;
    }

  /*  public Stock addStockLines(StockLine stockLine) {
        this.stockLines.add(stockLine);
        stockLine.getStocks().add(this);
        return this;
    }

    public Stock removeStockLines(StockLine stockLine) {
        this.stockLines.remove(stockLine);
        stockLine.getStocks().remove(this);
        return this;
    }*/

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
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", deliveryNoteRef=" + getDeliveryNoteRef() +
            ", dateOfStockUpdated='" + getDateOfStockUpdated() + "'" +
            ", storageCost=" + getStorageCost() +
            "}";
    }
}
