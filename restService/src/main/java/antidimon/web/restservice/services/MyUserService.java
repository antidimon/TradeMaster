package antidimon.web.restservice.services;


import antidimon.web.restservice.mappers.briefcase.BriefcaseMapper;
import antidimon.web.restservice.mappers.briefcase.BriefcaseStockMapper;
import antidimon.web.restservice.mappers.user.MyUserMapper;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseStockFindDTO;
import antidimon.web.restservice.models.dto.input.user.MyUserInputDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseStockOutputDTO;
import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import antidimon.web.restservice.models.entities.user.MyUser;
import antidimon.web.restservice.repositories.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final BriefcaseMapper briefcaseMapper;
    private final MyUserRepository myUserRepository;
    private final MyUserMapper myUserMapper;
    private final BriefcaseStockMapper briefcaseStockMapper;


    public MyUser getUserEntity(String phoneNumber){
        return myUserRepository.findByPhoneNumber(phoneNumber).get();
    }

    public MyUserOutputDTO getUserDTO(String phoneNumber){
        MyUser myUser = getUserEntity(phoneNumber);
        return myUserMapper.toOutputDTO(myUser);
    }

    public List<BriefcaseOutputDTO> getUserBriefcases(String phoneNumber) {
        MyUser user = getUserEntity(phoneNumber);
        List<Briefcase> briefcases = user.getBriefcases();
        List<Briefcase> sortedBriefcases =  briefcases.stream().sorted(new Comparator<Briefcase>() {
            @Override
            public int compare(Briefcase o1, Briefcase o2) {
                return (int)(o1.getId()-o2.getId());
            }
        }).toList();

        List<BriefcaseOutputDTO> briefcaseOutputDTOS = new ArrayList<>();
        for (Briefcase briefcase : sortedBriefcases) {
            briefcaseOutputDTOS.add(briefcaseMapper.toOutputDTO(briefcase));
        }
        return briefcaseOutputDTOS;
    }

    public List<BriefcaseStockOutputDTO> findStocks(String phoneNumber, String name) {

        List<BriefcaseStock> stocks = myUserRepository.findStocks(phoneNumber, name);
        return stocks.stream().map(briefcaseStockMapper::toOutputDTO).toList();
    }

    public List<BriefcaseStockOutputDTO> findStocks(String phoneNumber) {

        List<BriefcaseStock> stocks = myUserRepository.findStocks(phoneNumber);
        return stocks.stream().map(briefcaseStockMapper::toOutputDTO).toList();
    }

    @Transactional
    public void save(MyUserInputDTO user) {
        MyUser myUser = myUserMapper.toEntity(user);
        myUserRepository.save(myUser);
    }
}
