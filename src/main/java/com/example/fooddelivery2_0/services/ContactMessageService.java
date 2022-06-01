package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.ContactMessage;
import com.example.fooddelivery2_0.repos.ContactMessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepo contactMessageRepo;

    public Optional<ContactMessage> getById(Long id){
        return contactMessageRepo.findById(id);
    }
    public List<ContactMessage> getAllByEmail(String email){
        return contactMessageRepo.findAllByEmail(email);
    }
    public List<ContactMessage> getAllByMessage(String message){
        return contactMessageRepo.findAllByMessage(message);
    }
    public List<ContactMessage> getAllByEmailAndMessage(String email, String message){
        return contactMessageRepo.findAllByEmailAndMessage(email, message);
    }

    public void save(ContactMessage contactMessage) {
        contactMessageRepo.save(contactMessage);
    }
}
