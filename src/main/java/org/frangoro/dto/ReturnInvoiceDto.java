package org.frangoro.dto;

import java.util.List;

public class ReturnInvoiceDto {

	private Double totalLateCharge;
	private List<InvoiceItemDto> invoiceItems;
	
	public Double getTotalLateCharge() {
		return totalLateCharge;
	}
	public void setTotalLateCharge(Double totalLateCharge) {
		this.totalLateCharge = totalLateCharge;
	}
	public List<InvoiceItemDto> getInvoiceItems() {
		return invoiceItems;
	}
	public void setInvoiceItems(List<InvoiceItemDto> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

}
