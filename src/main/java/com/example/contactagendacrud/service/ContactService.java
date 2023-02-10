package com.example.contactagendacrud.service;

import com.example.contactagendacrud.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    public List<Contact> findActiveContacts();

    public Contact createContact(Contact contact);

    public Optional<Contact> findContactById(Long id);

    public Contact updateContact(Contact contact);

    public void deleteContact(Long id);

}
