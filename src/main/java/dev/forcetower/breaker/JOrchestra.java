package dev.forcetower.breaker;

import dev.forcetower.breaker.model.Person;
import dev.forcetower.breaker.result.Outcome;

import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;

public class JOrchestra {
    private final Orchestra instance;

    public JOrchestra(Orchestra instance) {
        this.instance = instance;
    }

    public Future<Outcome<? extends Person>> login() {
        var completable = new CompletableFuture<Outcome<? extends Person>>();
        instance.login(Coroutines.getContinuation((outcome, throwable) -> {
            if (throwable != null) {
                completable.completeExceptionally(throwable);
            } else {
                completable.complete(outcome);
            }
        }));
        return completable;
    }
}
