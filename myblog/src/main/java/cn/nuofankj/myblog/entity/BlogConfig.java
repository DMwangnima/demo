package cn.nuofankj.myblog.entity;

import javax.persistence.*;

@Entity
@Table(name = "blog_config")
@Inheritance(strategy = InheritanceType.JOINED)
public class BlogConfig {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String blogName;
  private String avatar;
  private String sign;
  private String wxpayQrcode;
  private String alipayQrcode;
  private String github;
  private String viewPassword;
  private String salt;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getBlogName() {
    return blogName;
  }

  public void setBlogName(String blogName) {
    this.blogName = blogName;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }


  public String getWxpayQrcode() {
    return wxpayQrcode;
  }

  public void setWxpayQrcode(String wxpayQrcode) {
    this.wxpayQrcode = wxpayQrcode;
  }


  public String getAlipayQrcode() {
    return alipayQrcode;
  }

  public void setAlipayQrcode(String alipayQrcode) {
    this.alipayQrcode = alipayQrcode;
  }


  public String getGithub() {
    return github;
  }

  public void setGithub(String github) {
    this.github = github;
  }


  public String getViewPassword() {
    return viewPassword;
  }

  public void setViewPassword(String viewPassword) {
    this.viewPassword = viewPassword;
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

}
