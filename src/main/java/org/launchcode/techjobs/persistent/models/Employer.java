package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    private String location;

    public Employer() {}

    public @NotBlank @Size(min = 3, max = 50) String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank @Size(min = 3, max = 50) String location) {
        this.location = location;
    }
}
