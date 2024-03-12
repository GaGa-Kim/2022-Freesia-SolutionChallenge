package com.freesia.imyourfreesia.service.center;

import com.freesia.imyourfreesia.domain.center.Center;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CenterCrawler {
    private static final String SAEIL_CENTER_URL = "https://saeil.mogef.go.kr/hom/info/search.do?page=";
    private static final String WOMAN_UP_URL = "https://www.seoulwomanup.or.kr/womanup/common/cntnts/selectContents.do?cntnts_id=W0000044";

    private static final int FIRST_PAGE_INDEX = 1;
    private static final int LAST_PAGE_INDEX = 16;

    public List<Center> crawlSaeilCenters() {
        return IntStream.rangeClosed(FIRST_PAGE_INDEX, LAST_PAGE_INDEX)
                .parallel()
                .mapToObj(this::crawlSaeilCenterPage)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Center> crawlWomanUpCenters() {
        try {
            Connection conn = Jsoup.connect(WOMAN_UP_URL);
            Document document = conn.get();
            Elements centerElements = document.select("li.jb_mb > ul > li");
            return centerElements.stream()
                    .map(this::parseWomanUpCenter)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
            return Collections.<Center>emptyList();
        }
    }

    private List<Center> crawlSaeilCenterPage(int page) {
        try {
            Connection conn = Jsoup.connect(SAEIL_CENTER_URL + page);
            Document document = conn.get();
            Elements centerElements = document.select("table.tableList02 > tbody > tr ");
            return centerElements.stream()
                    .map(this::parseSaeilCenter)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
            return Collections.<Center>emptyList();
        }
    }

    private Center parseSaeilCenter(Element element) {
        return Center.builder()
                .name(element.select("td").get(1).text())
                .contact(element.select("td").get(2).text())
                .address(element.select("td").get(3).text())
                .websiteUrl(element.select("td").get(5).select("a").attr("href"))
                .build();
    }

    private Center parseWomanUpCenter(Element element) {
        String address = element.select("ol > li").get(0).text().substring(6);
        if (!address.startsWith("서울특별시")) {
            address = "서울특별시 " + address;
        }
        return Center.builder()
                .name(element.select("h4").text())
                .contact(element.select("ol > li").get(1).text().substring(7))
                .address(address)
                .websiteUrl(element.select("ol > li").get(2).text().substring(7))
                .build();
    }
}
