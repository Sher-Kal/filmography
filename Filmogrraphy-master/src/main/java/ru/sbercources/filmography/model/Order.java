package ru.sbercources.filmography.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "publishing_seq", allocationSize = 1)
public class Order extends GenericModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", foreignKey = @ForeignKey(name = "FK_ORDER_FILM"))
    private Film film;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

    @Column(name = "is_purchased", nullable = false)
    private boolean isPurchased;

    @Builder
    public Order(Long id, LocalDateTime createdWhen, String createdBy, LocalDateTime updatedWhen, String updatedBy, LocalDateTime deletedWhen, String deletedBy, boolean isDeleted, User user, Film film, LocalDateTime rentDate, Integer rentPeriod, boolean isPurchased) {
        super(id, createdWhen, createdBy, updatedWhen, updatedBy, deletedWhen, deletedBy, isDeleted);
        this.user = user;
        this.film = film;
        this.rentDate = rentDate;
        this.rentPeriod = rentPeriod;
        this.isPurchased = isPurchased;
    }
}