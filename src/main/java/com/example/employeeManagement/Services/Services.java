package com.example.employeeManagement.Services;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Cacheable
public class Services {

    WebClient webClient = WebClient.create();
    public void callExternalApi() {

        String url = "https://countriesnow.space/api/v0.1/countries/iso";

        Mono<List<Countries>> data = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(Servicesdto.class)
                        .map(Servicesdto::getData);

//        for(Countries countries: data.getData()){
//            System.out.println("key : " + countries.getName() + " :: value : " + countries.getIso2());
//        }

   /*   data.flatMapMany(Flux::fromIterable)
              .flatMap(countries -> {
                  String key = countries.getName();
                  String value = countries.getIso3();
                  redisConfig.saveToRedis(key, value);
                  System.out.println(key);
                  return null;
              });*/
    }

}
