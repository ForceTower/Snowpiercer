package dev.forcetower.breaker;

import dev.forcetower.breaker.model.*;
import dev.forcetower.breaker.result.Outcome;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;

public class JOrchestra {
    private final Orchestra instance;

    public JOrchestra(Orchestra instance) {
        this.instance = instance;
    }

    public Future<Outcome<Person>> login() {
        var completable = new CompletableFuture<Outcome<Person>>();
        instance.login(Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<List<? extends Semester>>> semesters(long profileId) {
        var completable = new CompletableFuture<Outcome<List<? extends Semester>>>();
        instance.semesters(profileId, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<List<? extends SemesterInformation>>> allSemestersInformation(long profileId) {
        var completable = new CompletableFuture<Outcome<List<? extends SemesterInformation>>>();
        instance.allSemestersInformation(profileId, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<List<? extends DisciplineData>>> grades(long profileId, long semesterId) {
        var completable = new CompletableFuture<Outcome<List<? extends DisciplineData>>>();
        instance.grades(profileId, semesterId, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<MessagesDataPage>> messages(long profileId) {
        return messages(profileId, "");
    }

    public Future<Outcome<MessagesDataPage>> messages(long profileId, String until) {
        var completable = new CompletableFuture<Outcome<MessagesDataPage>>();
        instance.messages(profileId, until, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<List<? extends Lecture>>> lectures(long classId, int limit, int offset) {
        var completable = new CompletableFuture<Outcome<List<? extends Lecture>>>();
        instance.lectures(classId, limit, offset, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }

    public Future<Outcome<List<? extends LectureMissed>>> absences(long profileId,long classId, int limit, int offset) {
        var completable = new CompletableFuture<Outcome<List<? extends LectureMissed>>>();
        instance.absences(profileId, classId, limit, offset, Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }
}
