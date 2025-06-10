
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import com.auth0.jwt.JWT;

import java.io.IOException;
import shared.ActionPayload;

@Provider
@Priority(Priorities.AUTHENTICATION) // Se asegura de que se ejecute primero
public class JsonRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.contains("auth")) {
            return;
        }
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        // Aquí validas tu token (JWT, por ejemplo)
        if (!isValidToken(token)) {
            abortWithUnauthorized(requestContext);
        }

    }

    private boolean isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("integrador_1_proyecto_2025!-!");

            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            return verifier.verify(token) != null;
        } catch (Exception exception) {
            System.out.println("Invalid token: " + exception.getMessage());
            return false;
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new ActionPayload(401, null, "Acceso no autorizado, envia un token válido."))
                    .build());
    }
}
