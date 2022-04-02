package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.IconDto;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.service.IconService;
import Wooaham.wooaham_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/icons")
public class IconController {
    private IconService iconService;

    public IconController(IconService iconService) {
        this.iconService = iconService;
    }

    @GetMapping
    public List<IconDto> getIcons(){
        return iconService.getIcons();
    }

    @GetMapping("/{id}")
    public IconDto getIcon(@PathVariable(name = "id") Long iconId){
        return iconService.getIcon(iconId);
    }
}
