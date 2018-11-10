package cn.nuofankj.myblog.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments")
@Inheritance(strategy = InheritanceType.JOINED)
public class Comments implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String articleId;
  private long parentId;
  private long replyId;
  private String name;
  private String email;
  private String content;
  private String sourceContent;
  private long createTime;
  private long deleteTime;
  private long status;
  private String isAuthor;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }


  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }


  public long getReplyId() {
    return replyId;
  }

  public void setReplyId(long replyId) {
    this.replyId = replyId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getSourceContent() {
    return sourceContent;
  }

  public void setSourceContent(String sourceContent) {
    this.sourceContent = sourceContent;
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


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public String getIsAuthor() {
    return isAuthor;
  }

  public void setIsAuthor(String isAuthor) {
    this.isAuthor = isAuthor;
  }

}
