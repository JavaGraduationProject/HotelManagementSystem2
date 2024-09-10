package com.hotel.system.enums;

/**
 * 资源类型
 * @author liuyanzhao
 * @date 2022-03-16
 */
public enum ResourceTypeEnum {

    /**
     * 菜单
     */
    MENU("menu", "菜单"),

    /**
     * 接口
     */
    BUTTON("button", "接口"),

    /**
     * 菜单
     */
    PAGE("page", "页面");


    private String code;

    private String description;

    ResourceTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
