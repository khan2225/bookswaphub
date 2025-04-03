package com.BookSwapHub.BookSwapHub.service;

import com.BookSwapHub.BookSwapHub.model.Book;
import com.BookSwapHub.BookSwapHub.model.Swap;
import com.BookSwapHub.BookSwapHub.model.User;
import com.BookSwapHub.BookSwapHub.repository.BookRepository;
import com.BookSwapHub.BookSwapHub.repository.SwapRepository;
import com.BookSwapHub.BookSwapHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwapService {

    @Autowired
    private SwapRepository swapRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Swap> getSwapsByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return swapRepository.findByRequester(user);
    }

    public Swap createSwap(Swap swap){
        // Fetch full user and book objects before saving the swap
        User requester = userRepository.findById(swap.getRequester().getUserId())
                .orElseThrow(() -> new RuntimeException("Requester not found with id: " + swap.getRequester().getUserId()));
        Book book = bookRepository.findById(swap.getBook().getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + swap.getBook().getBookId()));

        swap.setRequester(requester);
        swap.setBook(book);
        swap.setRequestedAt(LocalDate.now());
        swap.setUpdatedAt(LocalDate.now());
        swap.setStatus("pending");
        return swapRepository.save(swap);
    }


    // Fetch all swaps for a provider's books
    public List<Swap> getSwapsByProvider(Long userId) {
        User provider = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + userId));

        // Fetch all books provided by this provider
        List<Book> providerBooks = bookRepository.findByProvider(provider);

        List<Swap> swaps = new ArrayList<>();
        for (Book book : providerBooks) {
            swaps.addAll(swapRepository.findByBook(book)); // Find all swaps related to this book
        }

        return swaps;
    }


    // Get all swaps related to books provided by a specific provider
    public List<Swap> getSwapsForProviderBooks(Long userId) {
        // Fetch provider
        User provider = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + userId));

        // Fetch books provided by the provider
        List<Book> providerBooks = bookRepository.findByProvider(provider);

        // Collect swaps for those books
        List<Swap> swaps = new ArrayList<>();
        for (Book book : providerBooks) {
            swaps.addAll(swapRepository.findByBook(book));
        }

        return swaps;
    }
}
