package com.BookSwapHub.BookSwapHub.repository;

import com.BookSwapHub.BookSwapHub.model.Book;
import com.BookSwapHub.BookSwapHub.model.Swap;
import com.BookSwapHub.BookSwapHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SwapRepository extends JpaRepository<Swap, Long> {
    List<Swap> findByRequester(User requester);

    // Get all swaps for a given book (Provider's books)
    List<Swap> findByBook(Book book);

    // Get all swaps for a provider's books (via the provider's books)
    List<Swap> findByBookProvider(User provider);


    int countByRequester_UserIdAndStatus(Long requesterId, String status);


    @Query("SELECT COUNT(s) FROM Swap s WHERE s.book.provider.id = :providerId AND s.status = :status")
    int countByProvider_IdAndStatus(@Param("providerId") Long providerId, @Param("status") String status);

    @Query("SELECT COUNT(s) FROM Swap s WHERE s.book.provider.id = :providerId")
    int countByProviderId(@Param("providerId") Long providerId);



}