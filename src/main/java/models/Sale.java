package models;

import java.util.Date;

public class Sale {
    private String id;
    private String professionalDocument;
    private String paymentMethodId;
    private Double total;
    private Date soldAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProfessionalDocument() { return professionalDocument; }
    public void setProfessionalDocument(String professionalDocument) { this.professionalDocument = professionalDocument; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Date getSoldAt() { return soldAt; }
    public void setSoldAt(Date soldAt) { this.soldAt = soldAt; }
}