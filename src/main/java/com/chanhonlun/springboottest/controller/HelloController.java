package com.chanhonlun.springboottest.controller;

import com.chanhonlun.springboottest.repository.SystemParameterRepository;
import com.chanhonlun.springboottest.vo.SystemParameterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @GetMapping("/system-parameter")
    public List<SystemParameterVO> getSystemParameterList() {
        return systemParameterRepository.findByIsDeleteFalse()
                .stream()
                .map(SystemParameterVO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/system-parameter/{id}")
    public SystemParameterVO getSystemParameter(@PathVariable Long id) {
        return new SystemParameterVO(systemParameterRepository.findByIdAndIsDeleteFalse(id));
    }
}
