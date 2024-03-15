package com.freesia.imyourfreesia.service.center;

import com.freesia.imyourfreesia.domain.center.Center;
import com.freesia.imyourfreesia.domain.center.CenterRepository;
import com.freesia.imyourfreesia.util.CenterCrawler;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;

    private final CenterCrawler centerCrawler;

    @Override
    @PostConstruct
    public void saveCenterList() {
        List<Center> centerList = new ArrayList<>();
        centerList.addAll(centerCrawler.crawlSaeilCenters());
        centerList.addAll(centerCrawler.crawlWomanUpCenters());
        centerRepository.saveAll(centerList);
    }

    @Override
    public List<Center> findCenterListByAddress(String address) {
        return centerRepository.findByAddressContains(address);
    }

    @Override
    public List<Center> findCenterList() {
        return centerRepository.findAll();
    }
}