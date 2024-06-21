package com.sparta.outsorcingproject.repository;

import com.sparta.outsorcingproject.entity.Menu;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    default Menu findMenuById(Long id, MessageSource messageSource) {
        return findById(id).orElseThrow(()->new IllegalArgumentException(messageSource.getMessage("not.find.menu",null, Locale.getDefault())));
    }

    List<Menu> findAllByStoreId(long storeId);
}
