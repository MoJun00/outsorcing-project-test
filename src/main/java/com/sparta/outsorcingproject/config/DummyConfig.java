package com.sparta.outsorcingproject.config;

import com.sparta.outsorcingproject.entity.Menu;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DummyConfig implements ApplicationRunner {

    private final MenuRepository menuRepository;

    @Override
    public void run(ApplicationArguments args) {
        Optional<Menu> menuOptional = menuRepository.findById(0L);
        if(menuOptional.isPresent())
            return;
        // 0번 더미가 있으면 그냥 넘어감

        // 메뉴 0번 더미 데이터 추가
        /*Menu menu = Menu.builder()
                .id(0)
                .name("삭제된 메뉴")
                .description("삭제된 메뉴입니다.")
                .price(0)
                .store(null)
                .build();
        menuRepository.save(menu);*/

    }
}