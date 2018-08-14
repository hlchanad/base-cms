package com.chanhonlun.springboottest.controller;

import com.chanhonlun.springboottest.constant.Status;
import com.chanhonlun.springboottest.repository.SystemParameterRepository;
import com.chanhonlun.springboottest.req.SystemParameterRequest;
import com.chanhonlun.springboottest.vo.SystemParameterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Value("${com.chanhonlun.profile}")
    private String profile;

    @GetMapping("active-profile")
    public String activeProfile() {
        return profile;
    }

    @GetMapping("current-timestamp")
    public String currentTimestamp() {
        return new Date().toString();
    }

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

    @PutMapping("/system-parameter")
    public boolean upsertSystemParameter(@RequestBody SystemParameterRequest request) {

        Long user = 0L;
        Date now  = new Date();

        systemParameterRepository.upsert(request.getCategory(), request.getKey(), request.getValue(),
                request.getDescription(), request.getDataType().name(), request.getIsConfigurableInCms(),
                Status.NORMAL.name(), false, user, now, user, now);
        return true;
    }
}
