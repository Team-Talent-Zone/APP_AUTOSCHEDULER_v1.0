package com.src.pojo;

public class FreelancerPaymentInput {

	public int beneficiaryId;
	private String purpose;
	private Double amount;
	private String batchId;
	private String merchantRefId;
	private String paymentType;

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getMerchantRefId() {
		return merchantRefId;
	}

	public void setMerchantRefId(String merchantRefId) {
		this.merchantRefId = merchantRefId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
