package com.diviso.graeshoppe.client.sale.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SaleDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-04-30T17:03:35.502+05:30[Asia/Calcutta]")

public class SaleDTO   {
  @JsonProperty("customerId")
  private Long customerId = null;

  @JsonProperty("grandTotal")
  private Double grandTotal = null;

  @JsonProperty("id")
  private Long id = null;

  public SaleDTO customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
  **/
  @ApiModelProperty(value = "")


  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public SaleDTO grandTotal(Double grandTotal) {
    this.grandTotal = grandTotal;
    return this;
  }

  /**
   * Get grandTotal
   * @return grandTotal
  **/
  @ApiModelProperty(value = "")


  public Double getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(Double grandTotal) {
    this.grandTotal = grandTotal;
  }

  public SaleDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaleDTO saleDTO = (SaleDTO) o;
    return Objects.equals(this.customerId, saleDTO.customerId) &&
        Objects.equals(this.grandTotal, saleDTO.grandTotal) &&
        Objects.equals(this.id, saleDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, grandTotal, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaleDTO {\n");
    
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

