package com.diviso.graeshoppe.client.product.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Product
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-17T14:38:38.757+05:30[Asia/Kolkata]")
@Document(indexName = "product")
public class Product   {

    private Long id;


    private String userId;

    
    private String reference;


    private String searchkey;

 
    private String name;

    @Lob
    private byte[] image;

    private String imageContentType;

  
    private String description;


    private String sku;

    private String mpn;


    private Boolean visible;

 
    private LocalDate dateOfMfd;

  
    private LocalDate dateOfExpiry;

    private Double maximumStockLevel;

  
    private Double reOrderLevel;

   
    private Boolean outOfStock;


    private Barcode barcode;

    private Set<Note> notes = new HashSet<>();
    
    private Set<StockDiary> stockDiaries = new HashSet<>();
    

    private Set<StockLine> stockLines = new HashSet<>();
    
  
    private Set<Label> labels = new HashSet<>();


    private Set<Category> categories = new HashSet<>();

    private StockCurrent stockCurrent;


    private Status status;


    private TaxCategory taxCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Product userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReference() {
        return reference;
    }

    public Product reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public Product searchkey(String searchkey) {
        this.searchkey = searchkey;
        return this;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Product imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMpn() {
        return mpn;
    }

    public Product mpn(String mpn) {
        this.mpn = mpn;
        return this;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Product visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDate getDateOfMfd() {
        return dateOfMfd;
    }

    public Product dateOfMfd(LocalDate dateOfMfd) {
        this.dateOfMfd = dateOfMfd;
        return this;
    }

    public void setDateOfMfd(LocalDate dateOfMfd) {
        this.dateOfMfd = dateOfMfd;
    }

    public LocalDate getDateOfExpiry() {
        return dateOfExpiry;
    }

    public Product dateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
        return this;
    }

    public void setDateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public Double getMaximumStockLevel() {
        return maximumStockLevel;
    }

    public Product maximumStockLevel(Double maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
        return this;
    }

    public void setMaximumStockLevel(Double maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
    }

    public Double getReOrderLevel() {
        return reOrderLevel;
    }

    public Product reOrderLevel(Double reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
        return this;
    }

    public void setReOrderLevel(Double reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Boolean isOutOfStock() {
        return outOfStock;
    }

    public Product outOfStock(Boolean outOfStock) {
        this.outOfStock = outOfStock;
        return this;
    }

    public void setOutOfStock(Boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public Product barcode(Barcode barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Product notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Product addNotes(Note note) {
        this.notes.add(note);
        note.setProduct(this);
        return this;
    }

    public Product removeNotes(Note note) {
        this.notes.remove(note);
        note.setProduct(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<StockDiary> getStockDiaries() {
        return stockDiaries;
    }

    public Product stockDiaries(Set<StockDiary> stockDiaries) {
        this.stockDiaries = stockDiaries;
        return this;
    }

    public Product addStockDiaries(StockDiary stockDiary) {
        this.stockDiaries.add(stockDiary);
        stockDiary.setProduct(this);
        return this;
    }

    public Product removeStockDiaries(StockDiary stockDiary) {
        this.stockDiaries.remove(stockDiary);
        stockDiary.setProduct(null);
        return this;
    }

    public void setStockDiaries(Set<StockDiary> stockDiaries) {
        this.stockDiaries = stockDiaries;
    }

    public Set<StockLine> getStockLines() {
        return stockLines;
    }

    public Product stockLines(Set<StockLine> stockLines) {
        this.stockLines = stockLines;
        return this;
    }

    public Product addStockLines(StockLine stockLine) {
        this.stockLines.add(stockLine);
        stockLine.setProduct(this);
        return this;
    }

    public Product removeStockLines(StockLine stockLine) {
        this.stockLines.remove(stockLine);
        stockLine.setProduct(null);
        return this;
    }

    public void setStockLines(Set<StockLine> stockLines) {
        this.stockLines = stockLines;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public Product labels(Set<Label> labels) {
        this.labels = labels;
        return this;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Product categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public StockCurrent getStockCurrent() {
        return stockCurrent;
    }

    public Product stockCurrent(StockCurrent stockCurrent) {
        this.stockCurrent = stockCurrent;
        return this;
    }

    public void setStockCurrent(StockCurrent stockCurrent) {
        this.stockCurrent = stockCurrent;
    }

    public Status getStatus() {
        return status;
    }

    public Product status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public Product taxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
        return this;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", reference='" + getReference() + "'" +
            ", searchkey='" + getSearchkey() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", sku='" + getSku() + "'" +
            ", mpn='" + getMpn() + "'" +
            ", visible='" + isVisible() + "'" +
            ", dateOfMfd='" + getDateOfMfd() + "'" +
            ", dateOfExpiry='" + getDateOfExpiry() + "'" +
            ", maximumStockLevel=" + getMaximumStockLevel() +
            ", reOrderLevel=" + getReOrderLevel() +
            ", outOfStock='" + isOutOfStock() + "'" +
            "}";
    }
}
