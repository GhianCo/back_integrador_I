import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import modules.user.models.User;
import modules.user.services.impl.UserServiceImpl;
import shared.ActionPayload;
import shared.pojos.LoginBodyReq;

@Path("auth")
public class AuthResource {

    @POST
    @Path("/login")
    public Response login(LoginBodyReq login) {

        User user_login = new User();

        User userObj = new User();
        userObj.setNick(login.getUser());
        userObj.setPass(String.valueOf(login.getPass()));

        UserServiceImpl userservice = new UserServiceImpl();
        user_login = userservice.login(userObj);

        if (user_login.getNick() != null) {
            Algorithm algorithm = Algorithm.HMAC256("integrador_1_proyecto_2025!-!");

            String token = JWT.create()
                    .withSubject(String.valueOf(user_login.getUser_id()))
                    .withClaim("name", user_login.getName())
                    .withClaim("lastname", user_login.getLastname())
                    .withClaim("email", user_login.getEmail())
                    .withClaim("role", user_login.getRole())
                    .sign(algorithm);

            return Response
                    .status(Response.Status.OK)
                    .entity(new ActionPayload(200, token, "Inicio de sesion"))
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new ActionPayload(401, null, "Credenciales invalidas, intenta nuevamente."))
                    .build();
        }
    }
}
