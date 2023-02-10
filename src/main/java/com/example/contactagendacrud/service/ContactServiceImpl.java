package com.example.contactagendacrud.service;

import com.example.contactagendacrud.entity.Contact;
import com.example.contactagendacrud.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> findActiveContacts() {
        return contactRepository.findAll().stream().filter(Contact::isActive).collect(Collectors.toList());
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> findContactById(Long id) {
        return Optional.of(contactRepository.findById(id).get());
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long id) {
        Optional<Contact> contactToDelete = contactRepository.findById(id);
        if(contactToDelete.isPresent()) {
            contactToDelete.get().setActive(false);
        }
    }


}
