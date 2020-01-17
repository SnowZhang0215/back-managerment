package io.snow.springcloud.userservice.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.assertj.core.util.Lists;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 */
@Entity
public class Permission extends BaseEntity{

//    `id` int(11) NOT NULL AUTO_INCREMENT,
//  `code` varchar(255) DEFAULT NULL COMMENT '菜单编码',
//            `p_code` varchar(255) DEFAULT NULL COMMENT '菜单父编码',
//            `p_id` varchar(255) DEFAULT NULL COMMENT '父菜单ID',
//            `name` varchar(255) DEFAULT NULL COMMENT '名称',
//            `url` varchar(255) DEFAULT NULL COMMENT '请求地址',
//            `is_menu` int(11) DEFAULT NULL COMMENT '是否是菜单(1.菜单。2.按钮)',
//            `level` int(11) DEFAULT NULL COMMENT '菜单层级',
//            `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
//            `status` int(11) DEFAULT NULL,
//  `icon` varchar(255) DEFAULT NULL,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String url;

    private String isMenu;

    private String level;

    private Long sort;

    private String status;

    private String icon;

    private String defaultType;

    private String component;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(name="parent_id")
    private Permission parent;

    @OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER,mappedBy="parent")
    private List<Permission> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public Permission getParent() {
        return parent;
    }

    public void setParent(Permission parent) {
        this.parent = parent;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
