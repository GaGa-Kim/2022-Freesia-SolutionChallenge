package com.freesia.imyourfreesia.service.cheering;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.domain.cheering.CheeringRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.service.user.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheeringService {
    private final CheeringRepository cheeringRepository;
    private final UserService userService;

    /* 응원 설정 */
    @Transactional
    public Cheering cheering(CheeringSaveRequestDto requestDto) {
        return cheeringRepository.save(requestDto.toEntity());
    }

    /* 응원 해제 */
    @Transactional
    public void unCheering(Long id) {
        Cheering cheering = cheeringRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        cheeringRepository.delete(cheering);
    }

    /* 응원 전체 갯수 조회 */
    @Transactional
    public Long countByYourEmail(String userEmail) {
        return cheeringRepository.countByRecipientEmail(userEmail);
    }

    /* 응원 랭킹 Top 10 조회 */
    @Transactional
    public List<Map<String, Object>> ranking() {

        List<User> userList = userService.findUserList();

        List<Map<String, Object>> countList = new ArrayList<>();

        for (User user : userList) {
            Map<String, Object> map = new HashMap<>();

            map.put("email", user.getEmail());
            map.put("nickName", user.getNickName());
            map.put("cnt", countByCreatedDateBetweenAndYourEmail(user.getEmail()));

            countList.add(map);
        }

        countList.sort((obj1, obj2) -> {
            Long cnt1 = (Long) obj1.get("cnt");
            Long cnt2 = (Long) obj2.get("cnt");
            return cnt2.compareTo(cnt1);
        });
        if (countList.size() < 10) {
            return countList.subList(0, countList.size());
        } else {
            return countList.subList(0, 10);
        }
    }

    /* 응원 일주일 갯수 조회 */
    @Transactional
    public Long countByCreatedDateBetweenAndYourEmail(String userEmail) {
        LocalDate startDatetime = LocalDate.now();
        LocalDate endDatetime = LocalDate.now().plusDays(7);

        System.out.println(startDatetime + "/" + endDatetime);

        return cheeringRepository.countByCreatedDateBetweenAndRecipientEmail(startDatetime, endDatetime, userEmail);
    }

    /* 내가 응원한 유저 아이디 조회 */
    @Transactional
    public List<Cheering> findByMyEmail(String userEmail) {
        return cheeringRepository.findBySenderEmail(userEmail);
    }

    /* 상대방 응원 여부 */
    @Transactional
    public Boolean findByMyEmailAndYourEmail(String myEmail, String yourEmail) {
        Cheering cheering = cheeringRepository.findBySenderEmailAndRecipientEmail(myEmail, yourEmail);
        if (cheering != null) {
            return true;
        } else {
            return false;
        }
    }
}
