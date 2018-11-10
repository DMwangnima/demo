package cn.nuofankj.myblog.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pages")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pages implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String type;
  private String md;
  private String html;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getMd() {
    return md;
  }

  public void setMd(String md) {
    this.md = md;
  }


  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

}
