package cn.nuofankj.myblog.constant;

public enum SourceEnum {

    // 时光网来源
    TIME_SOURCE(1, "time"),

    HALIHALI_SOURCE(2, "halihali"),

    Q2029_SOURCE(3, "q2029"),
    ;

    int id;

    String name;

    SourceEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
