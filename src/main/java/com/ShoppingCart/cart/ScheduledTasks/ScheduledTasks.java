package com.ShoppingCart.cart.ScheduledTasks;



import com.ShoppingCart.cart.entity.Car;
import com.ShoppingCart.cart.repository.CarRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

@Service
public class ScheduledTasks {

    private final CarRepository carRepository;

    @Autowired
    public ScheduledTasks(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Scheduled(fixedRate = 43200000)
    public void fetchDataFromSite() {
        String url = "https://999.md/ro/list/transport/cars";
        List<Car> cars = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements carElements = doc.select(".ads-list-photo-item");
            for (Element carElement : carElements) {
                String make = carElement.select(".ads-list-photo-item-title").text();
                String price = carElement.select(".ads-list-photo-item-price").text().replace("$", "").replace(",", "");

                Car car = Car.builder()
                        .make(make)
                        .price(price)
                        .build();

                cars.add(car);
            }

            carRepository.saveAll(cars);
            System.out.println("Fetched and saved data: " + cars);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}