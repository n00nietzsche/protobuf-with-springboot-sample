package com.example.demo.protobuf;

import com.google.protobuf.CodedOutputStream;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookProtosTest {

    @Test
    public void protobufTest() {
        String email = "abc@google.com";
        int id = new Random().nextInt();
        String name = "jake seo";
        String number = "123123123";

        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .addNumbers(number)
                .build();

        assertEquals(person.getEmail(), email);
    }


    @Test
    public void makeBinaryFile() throws IOException {
        String email = "abc@google.com";
        int id = new Random().nextInt();
        String name = "jake seo";
        String number = "123123123";

        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .addNumbers(number)
                        .build();

        assertEquals(person.getEmail(), email);

        AddressBookProtos.AddressBook addressBook
                = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();
        OutputStream outputStream = new FileOutputStream("C:/Users/IWAZ/Desktop/a.bin");
        addressBook.writeTo(outputStream);
    }

    @Test
    public void deserializeBinaryFile() throws IOException {
        AddressBookProtos.AddressBook deserialized
                = AddressBookProtos.AddressBook.newBuilder()
                .mergeFrom(new FileInputStream("C:/Users/IWAZ/Desktop/a.bin")).build();

        assertEquals(deserialized.getPeople(0).getEmail(), "abc@google.com");
    }

}