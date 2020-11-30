package dev.forcetower.breaker;

import com.google.gson.Gson;
import dev.forcetower.breaker.model.Authorization;
import dev.forcetower.breaker.model.ClassTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class JExec {
    public static void main(String[] args) {
        try {
            var file = new File("auth.json");
            var json = Files.lines(file.toPath()).collect(Collectors.joining());
            var authorization = new Gson().fromJson(json, Authorization.class);

            var kOrchestra  = new Orchestra.Builder().build();
//            orchestra.setAuthorization(new Authorization("actual_username", "actual_password"));
            kOrchestra.setAuthorization(authorization);
            var orchestra = new JOrchestra(kOrchestra);

            var result = orchestra.login().get();
            if (result.isSuccess()) {
                var success = result.asSuccess();
                var person = success.getValue();
                System.out.println("Connected as " + person);

                var semesters = (orchestra.semesters(person.getId()).get().asSuccess()).getValue();
                System.out.println("Semesters: " + semesters.size());

                var last = semesters.stream().sorted(Comparator.comparingLong(value -> value.getId() * -1)).collect(Collectors.toList()).get(0);
                System.out.println("Information about " + last.getCode() + "\n");

                var disciplines = orchestra.grades(person.getId(), last.getId()).get().asSuccess().getValue();
                disciplines.forEach(discipline -> {
                    System.out.println(discipline.getName());
                    System.out.println(discipline.getProgram());

                    discipline.getClasses().forEach(clazz -> {
                        System.out.println("Type: " + clazz.getType());
                        System.out.println("Allocations are shown below");

                        clazz.getAllocations()
                            .stream()
                            .sorted(Comparator.comparingInt(value -> {
                                ClassTime time = value.getTime();
                                if (time == null) return 0;
                                return time.getDay();
                            }))
                            .collect(Collectors.toList()).forEach(System.out::println);

                        try {
                            var lectures = orchestra.lectures(clazz.getId(), 3, 0).get().asSuccess().getValue();
                            System.out.println("First 3 Lectures");
                            lectures.forEach(lecture -> System.out.println(lecture.getSubject()));

                            var absences = orchestra.absences(person.getId(), clazz.getId(), 0, 0).get().asSuccess().getValue();
                            System.out.println("Missed classes");
                            absences.forEach(missed -> System.out.println(missed.getLecture().getSubject()));
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });

                    System.out.println("Evaluations...");
                    discipline.getEvaluations().forEach(evaluation -> {
                        System.out.println("Evaluation: " + evaluation.getName());
                        evaluation.getGrades().forEach(grade -> {
                            System.out.println("[" + grade.getDate() + "] " + grade.getName() + " -> " + grade.getValue() + " ** " + grade.getWeight());
                        });
                    });

                    System.out.println("---------------------------------------------------");
                });
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
