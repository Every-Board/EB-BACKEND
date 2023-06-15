package com.java.everyboard.scrap.service;

import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import com.java.everyboard.constant.ScrapType;
import com.java.everyboard.scrap.entity.Scrap;
import com.java.everyboard.scrap.mapper.ScrapMapper;
import com.java.everyboard.scrap.repository.ScrapRepository;
import com.java.everyboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final ScrapMapper scrapMapper;
    private final ContentRepository contentRepository;
    public Scrap createScrap(User user, Content content){
        Scrap scrap;
        int scrapStatus =0;
        //좋아요 안했을 경우
        if (isNotAlreadyScrap(user, content)) {
            scrap = new Scrap(user,content);
            scrap.setScrapType(ScrapType.ADD);
            scrapStatus = 1;
        }else{//좋아요를 했지만 취소했을 경우
            scrap = findVerifiedScrap(user,content);
            switch(scrap.getScrapType()){
                case ADD:
                    scrap.setScrapType(ScrapType.REMOVE);
                    scrapStatus = -1;
                    break;
                case REMOVE:
                    scrap.setScrapType(ScrapType.ADD);
                    scrapStatus = 1;
                    break;
                default:
            }
        }
        contentRepository.save(content);
        Scrap savedScrap = scrapRepository.save(scrap);
        return savedScrap;
    }

    public Scrap findVerifiedScrap(User user,Content content) {
        Optional<Scrap> optionalScrap = scrapRepository.findByUserAndContent(user, content);
        Scrap scrap = optionalScrap.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.SCRAP_NOT_FOUND)
        );
        return scrap;
    }

    private boolean isNotAlreadyScrap(User user, Content content) {
        return scrapRepository.findByUserAndContent(user, content).isEmpty();
    }
}