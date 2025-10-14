package com.nandina.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private String mercadoPagoUrl;
    private String currency;

    @Column(name = "value")
    private Long value;

    @Column(name = "title_image")
    private byte[] titleImage;

    @Column(name = "creator_id")
    private Long creatorId;

}