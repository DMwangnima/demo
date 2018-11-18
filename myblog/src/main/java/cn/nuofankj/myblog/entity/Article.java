package cn.nuofankj.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Accessors(chain = true)
public class Article implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long aid;

  private String id;
  private String title;
  private String categoryId;
  private long createTime;
  private long deleteTime;
  private long updateTime;
  private long publishTime;
  // 状态，0-正常（发布），1-删除，2-记录（待发布）
  private long status;
  private String content;
  private String htmlContent;
  private String cover;
  private String subMessage;
  private long pageview;
  private int isEncrypt;


  public long getAid() {
    return aid;
  }

  public void setAid(long aid) {
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


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public long getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(long deleteTime) {
    this.deleteTime = deleteTime;
  }


  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }


  public long getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(long publishTime) {
    this.publishTime = publishTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
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


  public long getPageview() {
    return pageview;
  }

  public void setPageview(long pageview) {
    this.pageview = pageview;
  }


  public int getIsEncrypt() {
    return isEncrypt;
  }

  public void setIsEncrypt(int isEncrypt) {
    this.isEncrypt = isEncrypt;
  }

}
