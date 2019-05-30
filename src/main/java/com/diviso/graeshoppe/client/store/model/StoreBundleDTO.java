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
package com.diviso.graeshoppe.client.store.model;

import java.util.List;

/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public class StoreBundleDTO {

	
	StoreDTO store;
	DeliveryInfoDTO deliveryInfo;
	List<TypeDTO> typeDTO;
	public StoreDTO getStore() {
		return store;
	}
	public void setStore(StoreDTO store) {
		this.store = store;
	}
	public DeliveryInfoDTO getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(DeliveryInfoDTO deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	public List<TypeDTO> getTypeDTO() {
		return typeDTO;
	}
	@Override
	public String toString() {
		return "StoreBundleDTO [store=" + store + ", deliveryInfo=" + deliveryInfo + ", typeDTO=" + typeDTO + "]";
	}
	public void setTypeDTO(List<TypeDTO> typeDTO) {
		this.typeDTO = typeDTO;
	}
	
	
}
