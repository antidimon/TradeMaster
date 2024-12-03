package antidimon.web.restservice.services;

import antidimon.web.restservice.mappers.briefcase.BriefcaseMapper;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseEditDTO;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseInfoDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.user.MyUser;
import antidimon.web.restservice.repositories.briefcase.BriefcaseRepository;
import antidimon.web.restservice.repositories.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BriefcaseService {

    private final BriefcaseMapper briefcaseMapper;
    private final BriefcaseRepository briefcaseRepository;
    private final MyUserService myUserService;

    public BriefcaseInfoDTO getBriefcaseInfo(BriefcaseInputDTO briefcaseInputDTO) throws Exception{
        MyUser user = myUserService.getUserEntity(briefcaseInputDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseRepository.findByUserAndName(user.getId(), briefcaseInputDTO.getBriefcaseName()).get();
        return briefcaseMapper.toInfoDTO(briefcase);
    }

    public BriefcaseOutputDTO getBriefcase(BriefcaseInputDTO briefcaseInputDTO) throws Exception{
        MyUser user = myUserService.getUserEntity(briefcaseInputDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseRepository.findByUserAndName(user.getId(), briefcaseInputDTO.getBriefcaseName()).get();
        return briefcaseMapper.toOutputDTO(briefcase);
    }

    @Transactional
    public boolean editBriefcaseName(BriefcaseEditDTO briefcaseEditDTO) {
        MyUser user = myUserService.getUserEntity(briefcaseEditDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseRepository.findByUserAndName(user.getId(), briefcaseEditDTO.getBriefcaseName()).get();
        List<String> briefcaseNames = briefcaseRepository.findAllUserOtherBriefcaseNames(user.getId(), briefcase.getBriefcaseName());
        if (briefcaseNames.contains(briefcaseEditDTO.getNewName())) {
            return false;
        }
        try {
            briefcaseRepository.updateBriefcaseName(briefcase.getId(), briefcaseEditDTO.getNewName());
            log.info("successfull edit briefcase with id = {}", briefcase.getId());
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean editBriefcaseBalance(BriefcaseEditDTO briefcaseEditDTO) {
        MyUser user = myUserService.getUserEntity(briefcaseEditDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseRepository.findByUserAndName(user.getId(), briefcaseEditDTO.getBriefcaseName()).get();
        if ((briefcaseEditDTO.getAddedBalance() < 0 && briefcase.getFreeBalance() + briefcaseEditDTO.getAddedBalance() < 0)){
            return false;
        }
        try {
            briefcaseRepository.updateBriefcaseBalance(briefcase.getId(), briefcase.getFreeBalance() + briefcaseEditDTO.getAddedBalance());
            if (briefcaseEditDTO.getAddedBalance() > 0){
                briefcaseRepository.addDeposit(briefcase.getId(), briefcaseEditDTO.getAddedBalance());
            }
            log.info("successfull edit briefcase with id = {}", briefcase.getId());
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Briefcase getBriefcase(long id, String briefcaseName) {
        return briefcaseRepository.findByUserAndName(id, briefcaseName).get();
    }
}
