package com.example.comp1011finalexamsummer2024;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class JsonReader {

    public List<Customer> readCustomers() {
        Gson gson = new Gson();
        List<Customer> customers = null;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("customers.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            if (inputStream == null) {
                System.err.println("Resource not found: customers.json");
                return null;
            }
            Type customerListType = new TypeToken<List<Customer>>() {}.getType();
            customers = gson.fromJson(reader, customerListType);
            System.out.println("JSON parsed successfully.");

        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return customers;
    }


}
