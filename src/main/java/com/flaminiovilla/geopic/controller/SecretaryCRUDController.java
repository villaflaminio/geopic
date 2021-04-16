package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.exception.SecretaryException;
import com.flaminiovilla.geopic.security.exception.UserException;
import com.flaminiovilla.geopic.security.model.User;
import com.flaminiovilla.geopic.security.service.UserService;
import com.flaminiovilla.geopic.controller.dto.SecretaryDTO;
import com.flaminiovilla.geopic.model.Secretary;
import com.flaminiovilla.geopic.service.SecretaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/secretary")
public class SecretaryCRUDController {

    @Autowired
    private SecretaryServiceImpl secretaryService;
    private final UserService userService;
    public SecretaryCRUDController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/findAllShort")
    String findAllShort(){
        Optional<User> user = userService.getUserWithAuthorities();
        if(user.isPresent())
            return secretaryService.findAllShort(user.get());
        throw new SecretaryException(SecretaryException.secretaryExceptionCode.ACTION_NOT_PERMITTED);
    }

    /**
     * search from id:
     * {"id" : }

     * @return Optional<Secretary>
     */
    @PostMapping("/findById")
    Optional<Secretary> findById(@RequestBody SecretaryDTO secretaryDTO){
        return secretaryService.findById(secretaryDTO);
    }

    //crud
    /**
     * Crea un nuovo obj di tipo Secretary
     * {
     *
     *     "email": "prova22@tiscali.it",
     *     "name": "secretary prova",
     *     "phone": "3775093443",
     *     "address": "via prova 21",
     *     "regionId": 1
     * }
     *
     * @return Secretary
     */
    @PostMapping("/create")
    @ResponseBody
    public Secretary create(@RequestBody SecretaryDTO secretaryDTO){
        setUser(secretaryDTO);

        return secretaryService.create(secretaryDTO);
    }

    /**
     * Modifica una Secretary dati tutti i parametri, id incluso
     *{
     *     "id" : 4,
     *     "email": "prova59@tiscali.it",
     *     "name": "secretary59 prova",
     *     "phone": "3775093443",
     *     "address": "via prova 21",
     *     "regionId": 1
     * }
     *
     * @return Secretary
     */
    @PutMapping("/update")
    @ResponseBody
    public Secretary update(@RequestBody SecretaryDTO secretaryDTO){
        setUser(secretaryDTO);

        return secretaryService.update(secretaryDTO);
    }

    /**
     * delete from id:
     * {"id" : }

     * @return boolean
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody SecretaryDTO secretaryDTO){
        setUser(secretaryDTO);

        return secretaryService.delete(secretaryDTO);
    }

    private void setUser(SecretaryDTO secretaryDTO){
        Optional<User> userLogged = userService.getUserWithAuthorities();
        if (userLogged.isPresent())
            secretaryDTO.user = userLogged.get();
        else
            throw new UserException(UserException.userExceptionCode.USER_NOT_LOGGED_IN);

    }
}
