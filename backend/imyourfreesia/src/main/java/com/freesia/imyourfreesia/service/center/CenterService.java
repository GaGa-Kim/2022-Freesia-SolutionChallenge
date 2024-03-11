package com.freesia.imyourfreesia.service.center;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CenterService {
    /**
     * 여성 취업 관련 사이트의 센터를 추출해 저장한다. (여성새로일하기 센터의 새일센터, 서울우먼업의 서울시여성인력개발기관)
     */
    @PostConstruct
    void saveCenters() throws IOException;
}