package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.ListAction;
import com.chanhonlun.basecms.response.vo.ActionButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ListActionButtonUtil {

    private static final Logger logger = LoggerFactory.getLogger(ListActionButtonUtil.class);

    @Value("${server.servlet.context-path}")
    private String       contextPath;
    private String       section;
    private List<ListAction> actions;

    public void init(String section) {
        this.section = section;
        this.actions = Arrays.asList(ListAction.DETAIL, ListAction.EDIT, ListAction.DELETE);
    }

    public void init(String section, List<ListAction> actions) {
        this.section = section;
        this.actions = actions;
    }

    public List<ActionButton> get(Long id) {
        return getActionButtons(id, this.actions);
    }

    public List<ActionButton> get(Long id, List<ListAction> actions) {
        return getActionButtons(id, actions);
    }

    private List<ActionButton> getActionButtons(Long id, List<ListAction> actions) {
        return actions
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
