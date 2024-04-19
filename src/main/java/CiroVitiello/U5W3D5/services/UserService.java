package CiroVitiello.U5W3D5.services;

import CiroVitiello.U5W3D5.dto.NewUserDTO;
import CiroVitiello.U5W3D5.entities.User;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.exceptions.NotFoundException;
import CiroVitiello.U5W3D5.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO ud;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ud.findAll(pageable);
    }

    public User save(NewUserDTO body) {
        this.ud.findByEmail(body.email())
                .ifPresent(user -> {
                    throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                });

        this.ud.findByUsername(body.username())
                .ifPresent(user -> {
                    throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                });
        User newUser = new User(body.username(), body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        return this.ud.save(newUser);
    }

    public User findById(long id) {
        return this.ud.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIDAndUpdate(long id, NewUserDTO body) {
        User found = this.findById(id);

        this.ud.findByEmail(body.email())
                .ifPresent(user -> {
                    throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                });

        this.ud.findByUsername(body.username())
                .ifPresent(user -> {
                    throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                });

        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setPassword(body.password());
        this.ud.save(found);
        return found;
    }

    public void findByIdAndDelete(long id) {
        User found = this.findById(id);
        this.ud.delete(found);
    }

    public User findByEmail(String email) {
        return ud.findByEmail(email).orElseThrow(() -> new NotFoundException("User with " + email + " not found!"));
    }
}
