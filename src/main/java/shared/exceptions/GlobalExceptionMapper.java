package shared.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import shared.ActionPayload;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int status = 500;
        String message = "Error interno del servidor";

        if (exception instanceof IllegalArgumentException) {
            status = 400;
            message = exception.getMessage();
        } else if (exception.getClass().getSimpleName().contains("NotFound")) {
            status = 404;
            message = exception.getMessage();
        } else if (exception instanceof RuntimeException) {
            message = exception.getMessage();
        }

        return Response.status(status)
                .entity(new ActionPayload(status, null, message))
                .build();
    }
}
