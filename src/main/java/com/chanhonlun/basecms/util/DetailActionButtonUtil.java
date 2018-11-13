package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.DetailAction;
import com.chanhonlun.basecms.response.vo.DetailButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class DetailActionButtonUtil {

    private static final Logger logger = LoggerFactory.getLogger(DetailActionButtonUtil.class);

    @Value("${server.servlet.context-path}")
    private String       contextPath;
    private String       section;
    private List<DetailAction> actions;

    public void init(String section) {
        this.section = section;
        this.actions = Arrays.asList(DetailAction.LIST, DetailAction.EDIT, DetailAction.DELETE);
    }

    public void init(String section, List<DetailAction> actions) {
        this.section = section;
        this.actions = actions;
    }

    public List<DetailButton> get(Serializable id) {
        return getActionButtons(id, this.actions);
    }

    public List<DetailButton> get(Serializable id, List<DetailAction> actions) {
        return getActionButtons(id, actions);
    }

    private List<DetailButton> getActionButtons(Serializable id, List<DetailAction> actions) {
        return actions
                .stream()
                .map(action -> DetailButton.builder()
                        .type(action.getType())
                        .faIcon(action.getFaIcon())
                        .bootstrapColor(action.getBootstrapColor())
                        .href(action.getUri(contextPath, section, id))
                        .redirectUrl(action.getRedirectUrl(contextPath, section, id))
                        .build())
                .collect(Collectors.toList());
    }
}
