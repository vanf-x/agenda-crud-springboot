package com.example.contactagendacrud.controller;

import com.example.contactagendacrud.entity.Contact;
import com.example.contactagendacrud.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping({"", "/", "/home"})
    private String displayHomePage() {
        return "home";
    }

    @GetMapping("/contacts")
    public String displayContactsPage(Model model) {
        model.addAttribute("contacts", contactService.findActiveContacts());
        return "contacts";
    }

    @GetMapping("/contacts/create")
    public String displayContactFormPage(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contactForm";
    }

    @PostMapping("/contacts")
    public String createContact(@ModelAttribute("contact") Contact contact) {
        contactService.createContact(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/edit/{id}")
    public String displayEditContactForm(@PathVariable Long id, Model model) {
        Optional<Contact> opContact = contactService.findContactById(id);
        Contact contact = new Contact();
      /*  if (opContact.isPresent()) {
            contact = (Contact) opContact.get();
        }
        model.addAttribute("contact", contact);
        return "editContactForm";*/
        if(opContact.isPresent()){
            model.addAttribute("contact", (Contact)opContact.get());
        }
        return "editContactForm";
    }

    @PostMapping("/contacts/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute("contact") Contact contact){
        Optional<Contact> existingContact = contactService.findContactById(id);
        if(existingContact.isPresent()){
            existingContact.get().setId(contact.getId());
            existingContact.get().setName(contact.getName());
            existingContact.get().setLastName(contact.getLastName());
            existingContact.get().setMobileNumber(contact.getMobileNumber());
            existingContact.get().setEmail(contact.getEmail());
            existingContact.get().setBirthDate(contact.getBirthDate());
            contactService.updateContact((Contact)existingContact.get());
        }
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/{id}")
    public String deleteContact(@PathVariable Long id){
        Optional<Contact> existingContact = contactService.findContactById(id);
        if(existingContact.isPresent()){
            Contact contact = new Contact();
            contact.setId(id);
            contact.setName(existingContact.get().getName());
            contact.setLastName(existingContact.get().getLastName());
            contact.setMobileNumber(existingContact.get().getMobileNumber());
            contact.setEmail(existingContact.get().getEmail());
            contact.setBirthDate(existingContact.get().getBirthDate());
            contact.setActive(false);
            contactService.deleteContact(id);
            contactService.createContact(contact);
            return "redirect:/contacts";
        }
        return "error";

    }
}
