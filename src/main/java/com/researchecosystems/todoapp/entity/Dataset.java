package com.researchecosystems.todoapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "beacon_dataset_table")
@NoArgsConstructor
@Getter
@Setter
public class Dataset {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "stable_id")
    private String stableId;

    @Column(name = "description")
    private String description;

    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private DatasetAccessType accessType;

    @Column(name = "reference_genome")
    private String referenceGenome;

    @Column(name = "variant_cnt")
    private long variantCount;

    @Column(name = "call_cnt")
    private long callCount;

    @Column(name = "sample_cnt")
    private long sampleCount;

}
