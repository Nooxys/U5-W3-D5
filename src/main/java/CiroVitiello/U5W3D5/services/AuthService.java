package CiroVitiello.U5W3D5.services;

import CiroVitiello.U5W3D5.dto.UserLoginDTO;
import CiroVitiello.U5W3D5.entities.User;
import CiroVitiello.U5W3D5.exceptions.UnauthorizedException;
import CiroVitiello.U5W3D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jt;

    public String authenticateUsersAndGenerateToken(UserLoginDTO body) {
        User user = this.us.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jt.createToken(user);

        } else {
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}
