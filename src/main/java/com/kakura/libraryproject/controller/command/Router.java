package com.kakura.libraryproject.controller.command;

public class Router {

    public enum RouterType {
        FORWARD, REDIRECT;
    }

    private String page = PagePath.HOME;
    private RouterType type = RouterType.FORWARD;

    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }

    /*public void setRedirect() { //todo delete this method
        this.type = RouterType.REDIRECT;
    }*/
}
