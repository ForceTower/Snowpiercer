package dev.forcetower.breaker;

import com.google.gson.Gson;
import dev.forcetower.breaker.model.Authorization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Interesting {
    public static void main(String[] args) {
        try {
            var file = new File("auth.json");
            var json = Files.lines(file.toPath()).collect(Collectors.joining());
            var authorization = new Gson().fromJson(json, Authorization.class);

            var orchestra = new Orchestra.Builder().build();
//            orchestra.setAuthorization(new Authorization("actual_username", "actual_password"));
            orchestra.setAuthorization(authorization);

            var result = new JOrchestra(orchestra).login().get();
            if (result.isSuccess()) {
                var success = result.asSuccess();
                var person = success.getValue();
                System.out.println(person);
            } else {
                var error = result.asError();
                System.out.println(error);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
