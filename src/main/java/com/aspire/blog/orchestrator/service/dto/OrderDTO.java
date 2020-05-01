package com.aspire.blog.orchestrator.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.aspire.blog.order.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

	private Long id;

	private String name;

	private Double amount;

	private Long inventory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		OrderDTO orderDTO = (OrderDTO) o;
		if (orderDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), orderDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "OrderDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", amount=" + getAmount() + ", inventory="
				+ getInventory() + "}";
	}
}
