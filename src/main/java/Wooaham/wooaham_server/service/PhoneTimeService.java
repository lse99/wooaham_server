package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.repository.ParentRepository;
import Wooaham.wooaham_server.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PhoneTimeService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;


}
