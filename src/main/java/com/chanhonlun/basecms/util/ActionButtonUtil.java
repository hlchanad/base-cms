package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.Action;
import com.chanhonlun.basecms.model.ActionButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ActionButtonUtil {

    private static final Logger logger = LoggerFactory.getLogger(ActionButtonUtil.class);

    private String       contextPath;
    private String       section;
    private List<Action> actions;

    public void init(String contextPath, String section) {
        this.contextPath = contextPath;
        this.section = section;
        this.actions = Arrays.asList(Action.DETAIL, Action.EDIT, Action.DELETE);
    }

    public void init(String contextPath, String section, List<Action> actions) {
        this.contextPath = contextPath;
        this.section = section;
        this.actions = actions;
    }

    public List<ActionButton> get(Long id) {
        return this.actions
                .stream()
                .map(action -> ActionButton.builder()
                        .type(action.getType())
                        .displayName(action.getDisplayName())
                        .bootstrapColor(action.getBootstrapColor())
                        .href(this.contextPath + "/" + this.section + "/" + id + action.getUri())
                        .build())
                .collect(Collectors.toList());
    }
}
