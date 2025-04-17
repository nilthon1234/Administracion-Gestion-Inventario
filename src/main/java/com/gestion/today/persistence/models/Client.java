package com.gestion.today.persistence.models;

import com.gestion.today.persistence.models.num.StateSeparationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private String id;
    private String name;
    private String lastName;
    private int dni;
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_separation")
    private StateSeparationType separationType;


}
