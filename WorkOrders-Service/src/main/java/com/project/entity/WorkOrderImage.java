package com.project.entity;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
public class WorkOrderImage {

    @Id
    @UuidGenerator
    private String workOrderImageId;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Column(name = "work_order_id", length = 255)
    private String workOrderId;

    // Getters and Setters

    public String getWorkOrderImageId() {
        return workOrderImageId;
    }

    public void setWorkOrderImageId(String workOrderImageId) {
        this.workOrderImageId = workOrderImageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }
}
