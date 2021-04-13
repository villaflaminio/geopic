package com.flaminiovilla.geris.helper;

import com.flaminiovilla.geris.controller.dto.SecretaryDTO;
import com.flaminiovilla.geris.exception.ReferralPersonException;
import com.flaminiovilla.geris.exception.SecretaryException;
import com.flaminiovilla.geris.model.Region;
import com.flaminiovilla.geris.model.Secretary;
import com.flaminiovilla.geris.repository.RegionRepository;
import com.flaminiovilla.geris.repository.SecretaryRepository;
import com.flaminiovilla.geris.security.model.User;
import com.flaminiovilla.geris.security.repository.AuthorityRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecretaryHelper {

    @Autowired
    private SecretaryRepository secretaryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AuthorityRepository authorityRepository;


    public List<Secretary> findAll() {
        return secretaryRepository.findAllByOrderByIdAsc();
    }

    public String findAllShort(User user) {
        List<Secretary> secretaries = secretaryRepository.findAllByOrderByIdAsc();
        JSONArray secretaryShort = new JSONArray();

        for (Secretary s : secretaries) {
            JSONObject jsonSecretary = new JSONObject().put("name", s.getName());
            jsonSecretary.put("id", String.valueOf(s.getId()));
            jsonSecretary.put("email", String.valueOf(s.getEmail()));
            secretaryShort.put(jsonSecretary);
        }
        return secretaryShort.toString();
    }

    public Secretary create(SecretaryDTO secretaryDTO) {
        if (!secretaryRepository.existsByName(secretaryDTO.name))
            return secretaryRepository.save(Secretary.builder()
                    .name(secretaryDTO.name)
                    .email(secretaryDTO.email)
                    .address(secretaryDTO.address)
                    .phone(secretaryDTO.phone)
                    .region(getRegion(secretaryDTO))
                    .build());
        throw new SecretaryException(SecretaryException.secretaryExceptionCode.SECRETARY_NAME_ALREADY_EXISTING);
    }
    public Secretary update(SecretaryDTO secretaryDTO) {
        if (!secretaryRepository.existsByName(secretaryDTO.name))
            return secretaryRepository.save(Secretary.builder()
                    .id(secretaryDTO.id)
                    .name(secretaryDTO.name)
                    .email(secretaryDTO.email)
                    .address(secretaryDTO.address)
                    .phone(secretaryDTO.phone)
                    .region(getRegion(secretaryDTO))
                    .build());
        throw new SecretaryException(SecretaryException.secretaryExceptionCode.SECRETARY_NAME_ALREADY_EXISTING);
    }
    public Boolean delete(SecretaryDTO secretaryDTO) {
        if(deletePermit(secretaryDTO)) {
            try {
                secretaryRepository.deleteById(secretaryDTO.id);
                return true;
            } catch (Exception e) {
                throw new SecretaryException(SecretaryException.secretaryExceptionCode.SECRETARY_DELETE_ERROR);
            }
        }
        throw new SecretaryException(SecretaryException.secretaryExceptionCode.ACTION_NOT_PERMITTED);
    }

    public Optional<Secretary> findById(Long id) {
        return secretaryRepository.findById(id);
    }
    public boolean existById(Long id){
        return secretaryRepository.existsById(id);
    }

    public Region getRegion(SecretaryDTO secretaryDTO) {
        //se ha come id di regione 99 si tratta di un admin, quindi vado a prendere la regione specificata
        if (secretaryDTO.regionId != null && secretaryDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) {
            Optional<Region> regionToAdd = regionRepository.findById(secretaryDTO.regionId);
            if (regionToAdd.isPresent()) {
                return regionToAdd.get();
            } else
                throw new ReferralPersonException(ReferralPersonException.referralPersonExceptionCode.ADD_TO_REGION_FAILED);
        } else { // altrimenti se non si tratta di admin vado a prendere la stessa regione dell'utente loggato
            Optional<Region> find = regionRepository.findById(secretaryDTO.user.getRegion().getId());
            if (find.isPresent())
                return find.get();
        }
        return null;
    }

    public boolean deletePermit(SecretaryDTO secretaryDTO) {
        Optional<Secretary> get = secretaryRepository.findById(secretaryDTO.id);
        if (get.isPresent()) {
            if (secretaryDTO.user.getRegion().getId().equals(get.get().getRegion().getId())) {
                //una secretary puo' cancellare solo una referralPerson che ha lo stessa regione
                return true;
            } else if (secretaryDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) { // altrimenti
                //se si tratta di admin puo' cancellare senza limiti
                return true;
            }
        }
        return false;
    }
}
