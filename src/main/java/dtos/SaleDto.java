package dtos;

import models.PaymentMethod;
import models.Professional;
import models.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleDto {
    private String id;
    private Professional professional;
    private PaymentMethod paymentMethod;
    private List<Service> services = new ArrayList<>();
    private Double total;
    private Date soldAt;

    public SaleDto(Professional professional, PaymentMethod paymentMethod, List<Service> services, Double total, Date soldAt) {
        this.professional = professional;
        this.paymentMethod = paymentMethod;
        this.services = services;
        this.total = total;
        this.soldAt = soldAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Date soldAt) {
        this.soldAt = soldAt;
    }
}