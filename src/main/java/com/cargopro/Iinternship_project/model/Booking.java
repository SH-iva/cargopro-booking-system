package com.cargopro.linternship_project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // This defines the foreign key relationship to the Load entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @Column(nullable = false)
    private String transporterId;

    private double proposedRate;
    private String comment;

    @CreationTimestamp // Automatically set the timestamp when created
    private Timestamp requestedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    // --- Getters and Setters ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Load getLoad() { return load; }
    public void setLoad(Load load) { this.load = load; }
    public String getTransporterId() { return transporterId; }
    public void setTransporterId(String transporterId) { this.transporterId = transporterId; }
    public double getProposedRate() { return proposedRate; }
    public void setProposedRate(double proposedRate) { this.proposedRate = proposedRate; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Timestamp getRequestedAt() { return requestedAt; }
    public void setRequestedAt(Timestamp requestedAt) { this.requestedAt = requestedAt; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}