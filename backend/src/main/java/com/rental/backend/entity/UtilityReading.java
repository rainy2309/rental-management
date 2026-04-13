package com.rental.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "utility_readings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"room_id", "reading_month"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "reading_month", nullable = false)
    private String readingMonth; // yyyy-MM

    @Column(nullable = false)
    private Integer electricOld;

    @Column(nullable = false)
    private Integer electricNew;

    @Column(nullable = false)
    private Integer waterOld;

    @Column(nullable = false)
    private Integer waterNew;

    @Column(nullable = false)
    private Double electricUnitPrice;

    @Column(nullable = false)
    private Double waterUnitPrice;
}