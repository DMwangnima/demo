package cn.nuofankj.myblog.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friends")
@Inheritance(strategy = InheritanceType.JOINED)
public class Friends implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long aid;
  private String friendId;
  private String name;
  private String url;
  private long createTime;
  private long updateTime;
  private long deleteTime;
  private String status;
  private long typeId;


  public long getAid() {
    return aid;
  }

  public void setAid(long aid) {
    this.aid = aid;
  }


  public String getFriendId() {
    return friendId;
  }

  public void setFriendId(String friendId) {
    this.friendId = friendId;
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


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime % 1000;
  }


  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime % 1000;
  }


  public long getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(long deleteTime) {
    this.deleteTime = deleteTime % 1000;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long typeId) {
    this.typeId = typeId;
  }

}
