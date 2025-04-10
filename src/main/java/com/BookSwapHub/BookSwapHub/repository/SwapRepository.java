package com.BookSwapHub.BookSwapHub.repository;

import com.BookSwapHub.BookSwapHub.model.Book;
import com.BookSwapHub.BookSwapHub.model.Swap;
import com.BookSwapHub.BookSwapHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwapRepository extends JpaRepository<Swap, Long> {
    List<Swap> findByRequester(User requester);

    // Get all swaps for a given book (Provider's books)
    List<Swap> findByBook(Book book);

    // Get all swaps for a provider's books (via the provider's books)
    List<Swap> findByBookProvider(User provider);


}