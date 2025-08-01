package com.cargopro.linternship_project.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "loads")
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String shipperId;

    @Column(nullable = false)
    private String loadingPoint;

    @Column(nullable = false)
    private String unloadingPoint;

    private String productType;
    private String truckType;
    private int noOfTrucks;
    private double weight;
    private String comment;
    private Timestamp loadingDate;
    private Timestamp unloadingDate;

    @CreationTimestamp
    private Timestamp datePosted;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        POSTED, BOOKED, CANCELLED
    }

    // --- Getters and Setters ---
    // Your IDE can generate these, or you can copy them from here.

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getShipperId() { return shipperId; }
    public void setShipperId(String shipperId) { this.shipperId = shipperId; }
    public String getLoadingPoint() { return loadingPoint; }
    public void setLoadingPoint(String loadingPoint) { this.loadingPoint = loadingPoint; }
    public String getUnloadingPoint() { return unloadingPoint; }
    public void setUnloadingPoint(String unloadingPoint) { this.unloadingPoint = unloadingPoint; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public String getTruckType() { return truckType; }
    public void setTruckType(String truckType) { this.truckType = truckType; }
    public int getNoOfTrucks() { return noOfTrucks; }
    public void setNoOfTrucks(int noOfTrucks) { this.noOfTrucks = noOfTrucks; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Timestamp getLoadingDate() { return loadingDate; }
    public void setLoadingDate(Timestamp loadingDate) { this.loadingDate = loadingDate; }
    public Timestamp getUnloadingDate() { return unloadingDate; }
    public void setUnloadingDate(Timestamp unloadingDate) { this.unloadingDate = unloadingDate; }
    public Timestamp getDatePosted() { return datePosted; }
    public void setDatePosted(Timestamp datePosted) { this.datePosted = datePosted; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}