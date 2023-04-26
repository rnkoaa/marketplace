package com.richard.marketplace;

import java.time.Instant;
import java.util.UUID;

public class Ad {

    private UUID id;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;

    public Ad(UUID id, String title, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ad(CreateAdCommand command) {
        var adCreatedEvent = new AdCreatedEvent(
                UUID.randomUUID(),
                command.title()
        );

        apply(adCreatedEvent);
    }

    void apply(Object event) {
        switch (event) {
            case null -> System.out.println("cannot handle null event");
            case AdCreatedEvent createdEvent -> adCreatedEvent(createdEvent);
            case AdUpdatedEvent updatedEvent -> adUpdatedEvent(updatedEvent);
            default -> System.out.println("unknown");
        }
    }

    private void adUpdatedEvent(AdUpdatedEvent updatedEvent) {
        this.title = updatedEvent.title();
        this.updatedAt = Instant.now();
    }

    private void adCreatedEvent(AdCreatedEvent createdEvent) {
        this.title = createdEvent.title();
        this.id = createdEvent.id();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
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

    public void handle(UpdateAdCommand command) {
        var event = new AdUpdatedEvent(command.id(), command.title());
        apply(event);
    }
}
