package com.sparta.outsorcingproject.service;

import com.sparta.outsorcingproject.dto.StoreRequestDto;
import com.sparta.outsorcingproject.dto.StoreResponseDto;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.StoreRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class StoreService {

    //User user = new User 부분 추후 구현
    private final StoreRepository storeRepository;
    private final MessageSource messageSource;

    public ResponseEntity<String> createStore(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid StoreRequestDto requestDto) {

        User user = new User();

        Store store = storeRepository.save(new Store(user, requestDto));
        return ResponseEntity.ok("가게 등록 완료");
    }

    public ResponseEntity<List<StoreResponseDto>> findAll() {
        List<StoreResponseDto> list = storeRepository.findAll()
                .stream()
                .map(StoreResponseDto::new).toList();

        // 가게 없는 경우 => throw
        if (list.isEmpty()) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "not.find.store",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // 가게 단일 조회
    public ResponseEntity<StoreResponseDto> findOne(Long storeId) {
        Store store = storeRepository.findStoreById(storeId, messageSource);
        return new ResponseEntity<>(new StoreResponseDto(store), HttpStatus.OK);
    }

    // 가게 수정
    @Transactional
    public ResponseEntity<String> updateStore(UserDetails userDetails, StoreRequestDto requestDto,
            Long storeId) {

        // User부분 완성 후에 구현 해야함
        User user = new User();

        Store store = storeRepository.findStoreById(storeId, messageSource);

        if (store == null) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        if (!Objects.equals(user.getId(), store.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }

        store.update(requestDto);
        return ResponseEntity.ok("수정 완료");
    }

    @Transactional
    public ResponseEntity<String> deleteStore(UserDetails userDetails, Long storeId) {
        User user = new User();

        Store store = storeRepository.findStoreById(storeId, messageSource);

        if (store == null) {
            throw new IllegalArgumentException("해당 가게는 존재하지 않습니다.");
        }

        if(!Objects.equals(user.getId(), store.getUser().getId())){
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        storeRepository.delete(store);

        return ResponseEntity.ok("게시글 삭제 완료");
    }
}
