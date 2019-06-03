 /*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diviso.graeshoppe.client.order.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;



/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public class DeliveryInfoDTO implements Serializable {

    private Long id;

    private String deliveryType;

    private Instant expectedDelivery;

    private Double deliveryCharge;


    private Long deliveryAddressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Instant getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(Instant expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long addressId) {
        this.deliveryAddressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryInfoDTO deliveryInfoDTO = (DeliveryInfoDTO) o;
        if (deliveryInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryInfoDTO{" +
            "id=" + getId() +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", expectedDelivery='" + getExpectedDelivery() + "'" +
            ", deliveryCharge=" + getDeliveryCharge() +
            ", deliveryAddress=" + getDeliveryAddressId() +
            "}";
    }
}

