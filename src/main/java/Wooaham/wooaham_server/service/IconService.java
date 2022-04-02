package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.dto.IconDto;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.IconRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IconService {
    private final IconRepository iconRepository;

    public IconService(IconRepository iconRepository) {
        this.iconRepository = iconRepository;
    }

    public List<IconDto> getIcons(){
        return iconRepository.findAll().stream()
                .map(IconDto::from)
                .collect(Collectors.toList());
    }

    public IconDto getIcon(Long iconId){
        return iconRepository.findById(iconId)
                .map(IconDto::from)
                .orElseThrow();
    }
}
