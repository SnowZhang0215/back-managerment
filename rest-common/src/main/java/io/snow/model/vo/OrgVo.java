package io.snow.model.vo;

public class OrgVo extends BaseVo {

    private Long id;

    private String orgId;

    private String orgName;

    private Long orgMaster;

    private Long orgSeniorManager1;

    public Long getOrgSeniorManager2() {
        return orgSeniorManager2;
    }

    public void setOrgSeniorManager2(Long orgSeniorManager2) {
        this.orgSeniorManager2 = orgSeniorManager2;
    }

    private Long orgSeniorManager2;

    private int openFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgMaster() {
        return orgMaster;
    }

    public void setOrgMaster(Long orgMaster) {
        this.orgMaster = orgMaster;
    }

    public Long getOrgSeniorManager1() {
        return orgSeniorManager1;
    }

    public void setOrgSeniorManager1(Long orgSeniorManager1) {
        this.orgSeniorManager1 = orgSeniorManager1;
    }

    public int getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(int openFlag) {
        this.openFlag = openFlag;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
