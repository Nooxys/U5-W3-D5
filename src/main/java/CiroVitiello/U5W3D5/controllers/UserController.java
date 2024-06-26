package CiroVitiello.U5W3D5.controllers;

import CiroVitiello.U5W3D5.dto.NewUserDTO;
import CiroVitiello.U5W3D5.entities.User;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService us;


    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody @Validated NewUserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.us.findByIDAndUpdate(currentUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentUser) {
        this.us.findByIdAndDelete(currentUser.getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.us.getUsers(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findEmployeeById(@PathVariable long userId) {
        return us.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findEmployeeByIdAndUpdate(@PathVariable long userId, @RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return us.findByIDAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findUsersByIdAndDelete(@PathVariable long userId) {
        us.findByIdAndDelete(userId);
    }


}
