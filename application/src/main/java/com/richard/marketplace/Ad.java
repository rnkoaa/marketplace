package com.richard.marketplace;

import com.richard.marketplace.cqrs.AggregateRoot;
import com.richard.marketplace.cqrs.annotations.Aggregate;
import com.richard.marketplace.cqrs.annotations.AggregateIdentifier;
import com.richard.marketplace.cqrs.annotations.CommandHandler;
import com.richard.marketplace.cqrs.annotations.EventSourcingHandler;

import java.time.Instant;
import java.util.UUID;

@Aggregate
public class Ad extends AggregateRoot<AdId> {

    @AggregateIdentifier
    private AdId id;

    private String title;
    private Instant createdAt;
    private Instant updatedAt;

    public Ad() {
    }

    public Ad(UUID id, String title, Instant createdAt, Instant updatedAt) {
        this.id = new AdId(id);
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @CommandHandler
    public Ad(CreateAdCommand command) {
        var id =  AdId.newId();
        var adCreatedEvent = new AdCreatedEvent(
            id,
            command.title()
        );

        apply(adCreatedEvent);
    }

    public void apply(Object event) {
        super.apply(event);
        switch (event) {
            case null -> System.out.println("cannot handle null event");
            case AdCreatedEvent createdEvent -> adCreatedEvent(createdEvent);
            case AdUpdatedEvent updatedEvent -> adUpdatedEvent(updatedEvent);
            default -> System.out.println("unknown");
        }
    }

    @EventSourcingHandler
    void adUpdatedEvent(AdUpdatedEvent updatedEvent) {
        this.title = updatedEvent.title();
        this.updatedAt = Instant.now();
    }

    @EventSourcingHandler
    void adCreatedEvent(AdCreatedEvent createdEvent) {
        this.title = createdEvent.title();
        this.id = createdEvent.id();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public AdId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Ad{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }

    @CommandHandler
    public void handle(UpdateAdTextCommand command) {
        apply(new AdUpdatedEvent(AdId.of(command.id()), command.title()));
    }
}
