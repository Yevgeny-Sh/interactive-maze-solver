package org.example;

public class Main {

    public static void main(String[] args) {

        ApiService apiService = new ApiService();

        String config = apiService.getRenderConfig();

        System.out.println(config);
    }
}