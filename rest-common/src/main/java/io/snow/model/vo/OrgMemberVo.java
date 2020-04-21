package io.snow.model.vo;


public class OrgMemberVo extends BaseVo {

    private Long id;

    private String gameId;

    private String gameName;

    private Long orgId;

    private String orgName;

    private Long fightCapacity;//战斗力

    private Integer orgRoleType;//0:首领,1:高层，2:精英，3：成员，4：学员

    private Long systemUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getFightCapacity() {
        return fightCapacity;
    }

    public void setFightCapacity(Long fightCapacity) {
        this.fightCapacity = fightCapacity;
    }

    public Integer getOrgRoleType() {
        return orgRoleType;
    }

    public void setOrgRoleType(Integer orgRoleType) {
        this.orgRoleType = orgRoleType;
    }

    public Long getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Long systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}