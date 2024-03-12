package com.freesia.imyourfreesia.service.cheering;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.domain.cheering.CheeringRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.user.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheeringServiceImpl implements CheeringService {
    private final CheeringRepository cheeringRepository;
    private final UserService userService;

    @Override
    public Cheering saveCheering(CheeringSaveRequestDto requestDto) {
        return cheeringRepository.save(requestDto.toEntity());
    }

    @Override
    public void deleteCheering(Long cheeringId) {
        Cheering cheering = cheeringRepository.findById(cheeringId).orElseThrow(NotFoundException::new);
        cheeringRepository.delete(cheering);
    }

    @Override
    public Long countByRecipientEmail(String email) {
        return cheeringRepository.countByRecipientEmail(email);
    }

    @Override
    public List<Map<String, Object>> cheeringRanking() {
        List<User> userList = userService.findUserList();
        return userList.stream()
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", user.getEmail());
                    map.put("nickName", user.getNickName());
                    map.put("count", countByCreatedDateBetweenAndRecipientEmail(user.getEmail()));
                    return map;
                })
                .sorted((obj1, obj2) -> {
                    Long cheeringCount1 = (Long) obj1.get("count");
                    Long cheeringCount2 = (Long) obj2.get("count");
                    return cheeringCount2.compareTo(cheeringCount1);
                })
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByCreatedDateBetweenAndRecipientEmail(String email) {
        LocalDate startDatetime = LocalDate.now();
        LocalDate endDatetime = LocalDate.now().plusDays(7);
        return cheeringRepository.countByCreatedDateBetweenAndRecipientEmail(startDatetime, endDatetime, email);
    }

    @Override
    public boolean exitsBySenderEmailAndRecipientEmail(String senderEmail, String recipientEmail) {
        return cheeringRepository.existsBySenderEmailAndRecipientEmail(senderEmail, recipientEmail);
    }

    @Override
    public List<Cheering> findCheeringByUser(String email) {
        return cheeringRepository.findBySenderEmail(email);
    }
}