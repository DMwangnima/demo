package cn.nuofankj.myblog.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admin")
@Inheritance(strategy = InheritanceType.JOINED)
public class Admin implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long aid;
  private String userId;
  private String username;
  private String password;
  private String salt;
  private String accessToken;
  private long tokenExpiresIn;
  private long createTime;
  private String status;
  private long lastLoginTime;


  public long getAid() {
    return aid;
  }

  public void setAid(long aid) {
    this.aid = aid;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


  public long getTokenExpiresIn() {
    return tokenExpiresIn;
  }

  public void setTokenExpiresIn(long tokenExpiresIn) {
    this.tokenExpiresIn = tokenExpiresIn;
  }


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public long getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(long lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

}
