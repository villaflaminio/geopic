package com.flaminiovilla.geopic.helper;

import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.exception.StructureException;
import com.flaminiovilla.geopic.repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StructureCheck {
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private ReferralPersonHelper referralPersonHelper;
    @Autowired
    private SecretaryHelper secretaryHelper;
    @Autowired
    private StructureRepository structureRepository;

    boolean categoryExist(StructureDTO structureDTO) {
        if (categoryHelper.existById(structureDTO.categoryId))
            return true;
        else {
            throw new StructureException(StructureException.structureExceptionCode.CATEGORY_NOT_EXIST);
        }
    }

    boolean secretaryExist(StructureDTO structureDTO) {
        if (secretaryHelper.existById(structureDTO.secretaryId))
            return true;
        else {
            throw new StructureException(StructureException.structureExceptionCode.SECRETARY_NOT_EXIST);
        }
    }

    boolean referralPersonExist(StructureDTO structureDTO) {
        if (referralPersonHelper.existById(structureDTO.referralPersonId))
            return true;
        else {
            throw new StructureException(StructureException.structureExceptionCode.REFERRAL_PERSON_NOT_EXIST);
        }
    }


    boolean latitudeAndLongitudeUsed(StructureDTO structureDTO) {
        if (structureRepository.existsByLatitudeAndLongitude(structureDTO.latitude, structureDTO.longitude))
            return true;
        throw new StructureException(StructureException.structureExceptionCode.LATITUDE_AND_LONGITUDE_ALREADY_EXISTING);
    }

    boolean latitudeAndLongitudeUsedByOtherStructure(StructureDTO structureDTO) {
        if (structureRepository.existsByLatitudeAndLongitude(structureDTO.latitude, structureDTO.longitude)){
            return true;
        }else if(structureDTO.id.equals(structureRepository.findFirstByLatitudeAndLongitude(structureDTO.latitude, structureDTO.longitude).getId())) {
            return true;
        }
        throw new StructureException(StructureException.structureExceptionCode.LATITUDE_AND_LONGITUDE_ALREADY_EXISTING);
    }
    public static boolean isValidEmailAddress(String email) {
        Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.matches();
    }

    public static boolean ceckMacroCategory(StructureDTO structureDTO) {
         if(structureDTO.macroCategory.equals("Liberi Professionisti") ||
                structureDTO.macroCategory.equals("Convenzioni Commerciali") ||
                structureDTO.macroCategory.equals("Sanitaria"))
             return true;
        throw new StructureException(StructureException.structureExceptionCode.MACRO_CATEGORY_NOT_FOUND);
    }

    public boolean structureValidating(StructureDTO structureDTO) {
        return categoryExist(structureDTO) && referralPersonExist(structureDTO) && secretaryExist(structureDTO)
                && latitudeAndLongitudeUsed(structureDTO) && isValidEmailAddress(structureDTO.email)
                && ceckMacroCategory(structureDTO);

    }
    public boolean structureUpdateValidating(StructureDTO structureDTO) {
        return categoryExist(structureDTO) && referralPersonExist(structureDTO) && secretaryExist(structureDTO)
                && latitudeAndLongitudeUsedByOtherStructure(structureDTO) && isValidEmailAddress(structureDTO.email)
                && ceckMacroCategory(structureDTO);

    }
}
