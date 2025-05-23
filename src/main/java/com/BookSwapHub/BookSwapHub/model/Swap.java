package com.BookSwapHub.BookSwapHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "swaps")
public class Swap{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long swapId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;


    @Column(length = 20)
    private String status; //"pending", "accepted"

    private LocalDate requestedAt;
    private LocalDate updatedAt;

    public Swap(){
        this.requestedAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.status = "pending";

    }

    //Getters & Setters
    public Long getSwapId(){
        return swapId;
    }

    public Book getBook() {
        return book;
    }


    public LocalDate getRequestedAt() {
        return requestedAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public User getRequester() {
        return requester;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setRequestedAt(LocalDate requestedAt) {
        this.requestedAt = requestedAt;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public void setSwapId(Long swapId) {
        this.swapId = swapId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getBookTitle() {
        return book != null ? book.getTitle() : "Unknown Book";
    }

    public String getRequesterName() {
        return requester != null ? requester.getName() : "Unknown";
    }

    public String getProviderName() {
        return provider != null ? provider.getName() : "Unknown";
    }


}