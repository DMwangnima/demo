package cn.nuofankj.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Article implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int aid;

  private String id;
  private String title;
  private String categoryId;
  private int createTime;
  private int deleteTime;
  private int updateTime;
  @Column(columnDefinition = "int default 0 comment '打卡的开始时间'", nullable = true)
  private int publishTime;
  private int status;
  private String content;
  private String htmlContent;
  private String cover;
  private String subMessage;
  private int pageview;
  private String isEncrypt;


  public int getAid() {
    return aid;
  }

  public void setAid(int aid) {
    this.aid = aid;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }


  public int getCreateTime() {
    return createTime;
  }

  public void setCreateTime(int createTime) {
    this.createTime = createTime;
  }


  public int getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(int deleteTime) {
    this.deleteTime = deleteTime;
  }


  public int getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(int updateTime) {
    this.updateTime = updateTime;
  }


  public int getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(int publishTime) {
    this.publishTime = publishTime;
  }


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getHtmlContent() {
    return htmlContent;
  }

  public void setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
  }


  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }


  public String getSubMessage() {
    return subMessage;
  }

  public void setSubMessage(String subMessage) {
    this.subMessage = subMessage;
  }


  public int getPageview() {
    return pageview;
  }

  public void setPageview(int pageview) {
    this.pageview = pageview;
  }


  public String getIsEncrypt() {
    return isEncrypt;
  }

  public void setIsEncrypt(String isEncrypt) {
    this.isEncrypt = isEncrypt;
  }

}
