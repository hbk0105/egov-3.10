package com.set.resolver;

import java.util.Locale;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class CustomViewResolver extends UrlBasedViewResolver implements Ordered {
    @Override protected View loadView(String viewName, Locale locale) throws Exception {// TODO Auto-generated method stub
        AbstractUrlBasedView view = buildView(viewName);
        View viewObj = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
        if (viewObj instanceof JstlView) {
            JstlView jv = (JstlView) viewObj;
            if (jv.getBeanName().indexOf(".jsp") != -1) {return null;}
        }return viewObj;
    }
}
