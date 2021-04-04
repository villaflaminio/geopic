package com.flaminiovilla.geris.helper;

import com.flaminiovilla.geris.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geris.exception.ReferralPersonException;
import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.model.Region;
import com.flaminiovilla.geris.repository.ReferralPersonRepository;
import com.flaminiovilla.geris.repository.RegionRepository;
import com.flaminiovilla.geris.security.model.Authority;
import com.flaminiovilla.geris.security.model.User;
import com.flaminiovilla.geris.security.repository.AuthorityRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.flaminiovilla.geris.exception.ReferralPersonException.referralPersonExceptionCode.*;

@Component
public class ReferralPersonHelper {

    @Autowired
    private ReferralPersonRepository referralPersonRepository;
    @Autowired
    private SecretaryHelper secretaryHelper;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AuthorityRepository authorityRepository;


    public List<ReferralPerson> findAll() {
        return referralPersonRepository.findAllByOrderByIdAsc();

    }

    public String findAllShort(User user) {
        List<ReferralPerson> referralPeople;

        if (user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN")))
            referralPeople = referralPersonRepository.findAllByRegionOrderByIdAsc(user.getRegion());
        else
            referralPeople = referralPersonRepository.findAllByOrderByIdAsc();

        JSONArray secretaryShort = new JSONArray();

        for (ReferralPerson r : referralPeople) {
            JSONObject jsonSecretary = new JSONObject().put("name", r.getName());
            jsonSecretary.put("id", String.valueOf(r.getId()));
            jsonSecretary.put("email", String.valueOf(r.getEmail()));

            secretaryShort.put(jsonSecretary);
        }
        return secretaryShort.toString();
    }

    public ReferralPerson create(ReferralPersonDTO referralPersonDTO) {

        if (secretaryHelper.existById(referralPersonDTO.secretaryId)) {
            if (!referralPersonRepository.existsByEmail(referralPersonDTO.email)) {
                return referralPersonRepository.save(
                        ReferralPerson.builder()
                                .email(referralPersonDTO.email)
                                .name(referralPersonDTO.name)
                                .surname(referralPersonDTO.surname)
                                .phone(referralPersonDTO.phone)
                                .region(getRegion(referralPersonDTO))
                                .secretary(secretaryHelper.findById(referralPersonDTO.secretaryId).get())
                                .build());
            } else {
                // l'email esiste gia
                throw new ReferralPersonException(REFERRAL_PERSON_ALREADY_EXIST);
            }
        } else {
            //la secretary non esiste
            throw new ReferralPersonException(SECRETARY_NOT_EXIST);
        }
    }

    public ReferralPerson update(ReferralPersonDTO referralPersonDTO) {
        return referralPersonRepository.save(
                ReferralPerson.builder()
                        .id(referralPersonDTO.id)
                        .email(referralPersonDTO.email)
                        .name(referralPersonDTO.name)
                        .surname(referralPersonDTO.surname)
                        .phone(referralPersonDTO.phone)
                        .region(getRegion(referralPersonDTO))
                        .secretary(secretaryHelper.findById(referralPersonDTO.secretaryId).get())
                        .build());
    }

    public Optional<ReferralPerson> findById(Long id) {
        return referralPersonRepository.findById(id);

    }

    public Boolean delete(ReferralPersonDTO referralPersonDTO) {
        if (deletePermit(referralPersonDTO)) {
            try {
                referralPersonRepository.deleteById(referralPersonDTO.id);
                return true;
            } catch (Exception e) {
                throw new ReferralPersonException(REFERRAL_PERSON_DELETE_ERROR);
            }
        }
        throw new ReferralPersonException(ACTION_NOT_PERMITTED);
    }

    public boolean existById(Long id) {
        return referralPersonRepository.existsById(id);
    }

    public Region getRegion(ReferralPersonDTO referralPersonDTO) {
        //se ha come id di regione 99 si tratta di un admin, quindi vado a prendere la regione specificata
        if (referralPersonDTO.regionId != null && referralPersonDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) {
            Optional<Region> regionToAdd = regionRepository.findById(referralPersonDTO.regionId);
            if (regionToAdd.isPresent()) {
                return regionToAdd.get();
            } else
                throw new ReferralPersonException(ADD_TO_REGION_FAILED);
        } else { // altrimenti se non si tratta di admin vado a prendere la stessa regione dell'utente loggato
            Optional<Region> find = regionRepository.findById(referralPersonDTO.user.getRegion().getId());
            if (find.isPresent())
                return find.get();
        }
        return null;
    }

    public boolean deletePermit(ReferralPersonDTO referralPersonDTO) {
        Optional<ReferralPerson> get = referralPersonRepository.findById(referralPersonDTO.id);
        if (get.isPresent()) {
            if (referralPersonDTO.user.getRegion().getId().equals(get.get().getRegion().getId())) {
                //una secretary puo' cancellare solo una referralPerson che ha lo stessa regione
                return true;
            } else if (referralPersonDTO.user.getAuthorities().contains(authorityRepository.getByName("ROLE_ADMIN"))) { // altrimenti
                //se si tratta di admin puo' cancellare senza limiti
                return true;
            }
        }
        return false;
    }
}
