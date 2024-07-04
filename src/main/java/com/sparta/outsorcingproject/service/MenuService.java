package com.sparta.outsorcingproject.service;

import com.sparta.outsorcingproject.dto.MenuRequestDto;
import com.sparta.outsorcingproject.dto.MenuResponseDto;
import com.sparta.outsorcingproject.entity.Menu;
import com.sparta.outsorcingproject.entity.OrdersMenu;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.MenuRepository;
import com.sparta.outsorcingproject.repository.OrdersMenuRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MessageSource messageSource;

    private final OrdersMenuRepository ordersMenuRepository;

    @Transactional
    public String createMenu(long storeId, MenuRequestDto requestDto, UserDetailsImpl userDetails) {
        Store store = getStore(storeId,messageSource,userDetails);

        Menu menu = Menu.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .description(requestDto.getDescription())
                .store(store)
                .build();

        menuRepository.save(menu);

        return "메뉴 저장 성공";
    }

    public MenuResponseDto getMenu(long storeId, long menuId) {
        Store store = storeRepository.findStoreById(storeId,messageSource);
        Menu menu = menuRepository.findMenuById(menuId,messageSource);

        return new MenuResponseDto(menu);
    }

    @Transactional
    public String updateMenu(long storeId, long menuId, MenuRequestDto requestDto, UserDetailsImpl userDetails) {
        Store store = getStore(storeId,messageSource,userDetails);

        Menu menu = menuRepository.findMenuById(menuId,messageSource);

        if(menu.getStore().getId() != storeId){
            throw new IllegalArgumentException("("+menu.getStore().getStoreName() +") 에서 가지고 있는 메뉴가 아닙니다.");
        }

        menu.update(requestDto);

        return "메뉴 업데이트 완료";
    }

    @Transactional
    public String deleteMenu(long storeId, long menuId, UserDetailsImpl userDetails) {
        Store store = getStore(storeId,messageSource,userDetails);

        Menu menu = menuRepository.findMenuById(menuId,messageSource);

        if(menu.getStore().getId() != storeId){
            throw new IllegalArgumentException("("+menu.getStore().getStoreName() +") 에서 가지고 있는 메뉴가 아닙니다.");
        }

        /*
        ///
        List<OrdersMenu> ordersMenus = ordersMenuRepository.findAllByMenuId(menuId);
        for (int i = 0; i < ordersMenus.size(); i++) {
            ordersMenuRepository.delete(ordersMenus.get(i));
        }
        ///
        */
        String str = menu.getName() + " 메뉴 삭제 완료";
        menuRepository.delete(menu);
        return str;
    }

    public List<MenuResponseDto> getMenus(long storeId) {
        Store store = storeRepository.findStoreById(storeId,messageSource);

        List<Menu> menus = menuRepository.findAllByStoreId(storeId);

        List<MenuResponseDto> menusDto = menus.stream().map(MenuResponseDto::new).collect(Collectors.toList());

        return menusDto;
    }

    private Store getStore(long storeId, MessageSource messageSource,UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Store store = storeRepository.findStoreById(storeId,user.getId(),messageSource); // 존재하지 않는 가게 + 유저가 가게의 소유주가 아닌 경우 오류
        //Store store = storeRepository.findById(storeId).orElseThrow(
        //        ()->new IllegalArgumentException(messageSource.getMessage("not.find.store",null, Locale.getDefault())));

        return store;
    }
}
