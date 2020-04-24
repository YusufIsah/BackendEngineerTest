package data.mapper.exception;

import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        int code = 500;
        String ERROR = "error";
        String CODE_LITERAL = "code";
        if (e instanceof WebApplicationException) {
            code = ((WebApplicationException) e).getResponse().getStatus();
        }
        return Response.status(code)
                .entity(Json.createObjectBuilder().add(ERROR, e.getMessage()).add(CODE_LITERAL, code).build())
                .build();
    }
}
