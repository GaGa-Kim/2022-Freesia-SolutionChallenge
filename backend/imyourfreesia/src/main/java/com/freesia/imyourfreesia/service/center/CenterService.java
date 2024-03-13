package com.freesia.imyourfreesia.service.center;

import com.freesia.imyourfreesia.domain.center.Center;
import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CenterService {
    /**
     * 여성 취업 관련 사이트의 센터를 추출해 저장한다. (여성새로일하기 센터의 새일센터, 서울우먼업의 서울시여성인력개발기관)
     */
    @Transactional
    void saveCenterList() throws IOException;

    /**
     * 지역에 따른 센터 목록을 조회한다.
     *
     * @param address (지역)
     * @return List<Center> (지역에 따라 조회한 센터 목록)
     */
    List<Center> findCenterListByAddress(String address);

    /**
     * 전체 센터 목록을 조회한다.
     *
     * @return List<Center> (전체 센터 목록)
     */
    List<Center> findCenterList();
}