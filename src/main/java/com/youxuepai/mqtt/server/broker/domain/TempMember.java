package com.youxuepai.mqtt.server.broker.domain;

/**
 * 临时班级用户
 * 
 * @author cm_wang
 *
 */
public class TempMember {

  private Integer userId;
  private String loginName;
  private String realName;
  private Integer classId;
  private String role;
  private int status;
  private String topic;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getClassId() {
    return classId;
  }

  public void setClassId(Integer classId) {
    this.classId = classId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  @Override
  public String toString() {
    return "TempStudent [userId=" + userId + ", loginName=" + loginName + ", realName=" + realName
        + ", classId=" + classId + ", role=" + role + ", status=" + status + ", topic=" + topic
        + "]";
  }


}
