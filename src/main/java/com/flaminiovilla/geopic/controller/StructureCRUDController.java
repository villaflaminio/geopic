package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.security.exception.UserException;
import com.flaminiovilla.geopic.security.model.User;
import com.flaminiovilla.geopic.security.service.UserService;
import com.flaminiovilla.geopic.model.Structure;
import com.flaminiovilla.geopic.service.StructureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/structure")
public class StructureCRUDController {

    @Autowired
    private StructureServiceImpl structureService;
    private final UserService userService;

    public StructureCRUDController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Crea un nuovo obj di tipo structure
     * {
     * "name" : "",
     * "macroCategory" : "",
     * "description" : "",
     * "discount" : ,
     * "expireDateConvention" : "",
     * "email" : "",
     * "phone" : "",
     * "website" : "",
     * "address" : "",
     * "latitude" : ,
     * "longitude" : ,
     * "categoryId" : ,
     * "referralPersonId" : ,
     * "secretaryId" : ,
     * }
     *
     * @return Structure
     */
    @PostMapping("/create")
    @ResponseBody
    public Structure create(@RequestBody StructureDTO structureDTO) {
        setUser(structureDTO);

        return structureService.create(structureDTO);
    }

    /**
     * Crea un nuovo obj di tipo structure
     * {
     * -> "id" : ,
     * "name" : "",
     * "macroCategory" : "",
     * "description" : "",
     * "discount" : ,
     * "expireDateConvention" : "",
     * "email" : "",
     * "phone" : "",
     * "website" : "",
     * "address" : "",
     * "latitude" : ,
     * "longitude" : ,
     * "categoryId" : ,
     * "referralPersonId" : ,
     * "secretaryId" : ,
     * }
     *
     * @return Structure
     */
    @PutMapping("/update")
    @ResponseBody
    public Structure update(@RequestBody StructureDTO structureDTO) {
        setUser(structureDTO);

        return structureService.update(structureDTO);
    }

    /**
     * delete from id
     * { "id" : }
     *
     * @return Category
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody StructureDTO structureDTO) {
        setUser(structureDTO);

        return structureService.delete(structureDTO);
    }

    private void setUser(StructureDTO structureDTO){
        Optional<User> userLogged = userService.getUserWithAuthorities();
        if (userLogged.isPresent())
            structureDTO.user = userLogged.get();
        else
            throw new UserException(UserException.userExceptionCode.USER_NOT_LOGGED_IN);

    }
}
