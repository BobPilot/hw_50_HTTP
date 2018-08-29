package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class Convert {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String from;
    private String to;
    private double amount = 0;

    public void play(){

        System.out.println("=== CONVERT ===");

        double res = toConvert();

        System.out.println(LocalDate.now() + ": the rate of " + amount + " " + from + " is "
                + new DecimalFormat("#0.00").format(res) + " " + to);

        closeStream();
    }

    private double toConvert(){

        Response response;
        boolean success;
        do {
            setData();
            response = getResponse();

            if(!response.success && response.getRates().size() == 2){
                System.out.println(response.getError().getInfo());
                System.out.println("Try again");
                success = false;
            } else if(response.getRates().size() != 2){
                System.out.println("Error writing currency code");
                System.out.println("Try again");
                success = false;
            } else {
                success = true;
            }

        }   while(!success);

        return response.getRates().get(to) /
                response.getRates().get(from) * amount;

    }

    private void setData() {

        do {
            System.out.print("amount: ");
            amount = setAmount();

            System.out.print("code of currency from: ");
            from = setCurrency();

            System.out.print("code of currency to: ");
            to = setCurrency();
        } while(!isDataCorrect());

    }

    private String setCurrency(){
        try {
            return br.readLine().trim().toUpperCase();
        } catch (IOException e) {
            return null;
        }
    }

    private double setAmount() {
        try {
            return Double.parseDouble(br.readLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean isDataCorrect() {
        boolean isCorrect = isCurrencyCorrect(from) && isCurrencyCorrect(to) && amount > 0;

        if(!isCorrect){
            System.out.println("Incorrect data. Try again.");
        }
        return isCorrect;
    }

    private boolean isCurrencyCorrect(String currency) {
        return currency != null && currency.length() == 3;
    }

    private Response getResponse() {

        String url = "http://data.fixer.io/api/latest?access_key=70598d7922b40798bb6e716fb9373c2d&symbols=";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(new HttpHeaders());

        return restTemplate.exchange(url + from + "," + to,
                HttpMethod.GET, httpEntity, Response.class)
                .getBody();
    }

    private void closeStream() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
