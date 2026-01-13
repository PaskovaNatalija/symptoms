package si.healthapp.symptoms;

import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;

import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
public class LoggingFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class);

    @Context
    UriInfo info;

    @Inject
    private Event<LogEvent> logEvent;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        final String address = context.getUriInfo().getRequestUri().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        String timestamp = dateFormat.format(new Date(System.currentTimeMillis()));

        String logMessage = String.format("%s INFO %s Correlation: %s [%s] - <* Klic storitve %s %s >",
                timestamp,
                address,
                context.getUriInfo().getBaseUri().getPort(),
                ((PostMatchContainerRequestContext) context).getResourceMethod().getResourceClass().getName(),
                context.getMethod(),
                ((PostMatchContainerRequestContext) context).getResourceMethod().getMethod().getName());

        LOG.infof(logMessage);
         logEvent.fire(new LogEvent(logMessage));
    }

}