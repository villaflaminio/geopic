package com.flaminiovilla.geris.controller;

import com.flaminiovilla.geris.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geris.exception.ReferralPersonException;
import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.security.exception.UserException;
import com.flaminiovilla.geris.security.model.User;
import com.flaminiovilla.geris.security.service.UserService;
import com.flaminiovilla.geris.service.ReferralPersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.flaminiovilla.geris.exception.ReferralPersonException.referralPersonExceptionCode.ACTION_NOT_PERMITTED;
import static com.flaminiovilla.geris.security.exception.UserException.userExceptionCode.USER_NOT_LOGGED_IN;

@RestController
@RequestMapping("/api/referralPerson")
public class ReferralPersonCRUDController {

    @Autowired
    private ReferralPersonServiceImpl referralPersonService;
    private final UserService userService;

    public ReferralPersonCRUDController(UserService userService) {
        this.userService = userService;
    }

    /**
     * restiruisce
     * {
     *     "id" : ,
     *     "name" : ,
     *     "email" :
     * }
     * */
    @GetMapping("/findAllShort")
    String findAllShort(){
        Optional<User> user = userService.getUserWithAuthorities();
        if(user.isPresent())
            return referralPersonService.findAllShort(user.get());
        throw new ReferralPersonException(ACTION_NOT_PERMITTED);

    }
    /**
     * search from id:
     * E' possibile cercare tra tutti quelli presenti senza problemi
     * {"id" : }
     * @return Optional<ReferralPerson>
     */
    @PostMapping("/findById")
    Optional<ReferralPerson> findById(@RequestBody ReferralPersonDTO referralPersonDTO){
        return referralPersonService.findById(referralPersonDTO);
    }
    /**
     * Dati nome e colore creo un nuovo obj, se l'inserimento riesce
     * restituisco il nuovo obj
     * {
     *     "id" : 4,
     *     "email": "prova59@tiscali.it",
     *     "name": "secretary59 prova",
     *     "phone": "3775093443",
     *     "address": "via prova 21",
     *     "regionId": 1
     * }
     * @return Category
     */
    @PostMapping("/create")
    @ResponseBody
    public ReferralPerson create(@RequestBody ReferralPersonDTO referralPersonDTO){
        setUser(referralPersonDTO);
        return referralPersonService.create(referralPersonDTO);
    }

    /**
     * Update from id
     * {
     *     "id" : 10,
     *     "email": "prova33@tiscali.it",
     *     "name": "Fabrizio",
     *     "surname": "Villa",
     *     "phone": "3775093443",
     *     "secretaryId": 1,
     *     "regionId": 17
     *
     * }
     * @return Category
     */
    @PutMapping("/update")
    @ResponseBody
    public ReferralPerson update(@RequestBody ReferralPersonDTO referralPersonDTO){
        setUser(referralPersonDTO);

        return referralPersonService.update(referralPersonDTO);
    }



    /**
     * delete from id:
     * {"id" : }
     * @return boolean
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody ReferralPersonDTO referralPersonDTO){
        setUser(referralPersonDTO);
        return referralPersonService.delete(referralPersonDTO);
    }

    private void setUser(ReferralPersonDTO referralPersonDTO){
        Optional<User> userLogged = userService.getUserWithAuthorities();
        if (userLogged.isPresent())
            referralPersonDTO.user = userLogged.get();
        else
            throw new UserException(USER_NOT_LOGGED_IN);

    }
}
