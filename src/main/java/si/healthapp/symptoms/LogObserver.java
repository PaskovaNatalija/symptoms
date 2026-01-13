package si.healthapp.symptoms;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class LogObserver {

    @Inject
    @Channel("sua_rv2_1_exchange")
    Emitter<String> emitter;

    void observeLogEvent(@Observes LogEvent logEvent) {
        emitter.send(logEvent.getLogMessage());
    }
}