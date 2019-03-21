package com.nuofankj.springdemo.support;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 分隔符类型：
 * - 对象成员分隔符，格式 1-2
 * , array和list分隔符，格式1,2,3 或者[1,2,3]
 * {} 对象分隔符，在最外围的可以不加，组合对象需加，格式{1-2f3}
 * : ; map类型使用，格式 key:value1;key:value2;
 *
 * @ 多态分隔符，格式 XXX@{1-2-3};
 * <p>
 * Tips:
 * Filed为自定义对象或者包含自定义对象的时候需要实现PolyObjectMapper接口
 * 使用多态的时候：多态类需要加@ClassMapper注解
 */
public enum TypeEnum {

    INT_TYPE("int", "java.lang.Integer") {
        @Override
        public Object analyse(Object o, String str) {

            return Integer.parseInt(str);
        }
    },

    STRING_TYPE("java.lang.String", null) {
        @Override
        public Object analyse(Object o, String str) {

            return str;
        }
    },

    FLOAT_TYPE("float", "java.lang.Float") {
        @Override
        public Object analyse(Object o, String str) {

            return Float.parseFloat(str);
        }
    },

    DOUBLE_TYPE("double", "java.lang.Double") {
        @Override
        public Object analyse(Object o, String str) {

            return Double.parseDouble(str);
        }
    },

    BYTE_TYPE("byte", "java.lang.Byte") {
        @Override
        public Object analyse(Object o, String str) {

            return Byte.parseByte(str);
        }
    },

    SHORT_TYPE("short", "java.lang.Short") {
        @Override
        public Object analyse(Object o, String str) {

            return Short.parseShort(str);
        }
    },

    LONG_TYPE("long", "java.lang.Long") {
        @Override
        public Object analyse(Object o, String str) {

            return Long.parseLong(str);
        }
    },

    CHAR_TYPE("char", null) {
        @Override
        public Object analyse(Object o, String str) {

            return str.charAt(0);
        }
    },

    BOOLEAN_TYPE("boolean", "java.lang.Boolean") {
        @Override
        public Object analyse(Object o, String str) {

            return str.equals("1");
        }
    },

    LIST_TYPE("java.util.List", null) {
        @Override
        public Object analyse(Object o, String str) {

            List list = new ArrayList();
            try {
                // 获取List的泛型类型
                List<Type> parameterizedType = getParameterizedType(o);
                for (String s : splitFieldMember(str, ',')) {
                    Object obj = TypeEnum.doAnalyse(parameterizedType.get(0), s);
                    list.add(obj);
                }
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("无法将字符[{}]转成List", str, e);
                throw new IllegalStateException(message.getMessage());
            }
            return list;
        }
    },

    MAP_TYPE("java.util.Map", null) {
        @Override
        public Object analyse(Object o, String str) {

            Map map = new HashMap();
            try {
                List<Type> parameterizedType = getParameterizedType(o);
                // map key 的类型
                Type keyType = parameterizedType.get(0);
                // map value 的类型
                Type valType = parameterizedType.get(1);

                List<String> strList = splitFieldMember(str, ';');
                for (String s : strList) {
                    List<String> keyValue = splitFieldMember(s, ':');
                    // 键 的处理
                    String keyStr = keyValue.get(0);
                    Object key = TypeEnum.doAnalyse(keyType, keyStr);
                    // 值 的处理
                    String valStr = keyValue.get(1);
                    Object value = TypeEnum.doAnalyse(valType, valStr);
                    map.put(key, value);
                }
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("无法将字符[{}]转成Map", str, e);
                throw new IllegalStateException(message.getMessage());
            }
            return map;
        }
    },

    ARRAY_TYPE(null, null) {
        @Override
        public Object analyse(Object o, String str) {

            try {
                Class<?> cls;
                // 取出数组内具体的class类型
                if (o instanceof Field) {
                    cls = ((Field) o).getType().getComponentType();
                } else {
                    cls = ((Class) o).getComponentType();
                }

                List<String> strList = splitFieldMember(str, ',');
                Object arr = Array.newInstance(cls, strList.size());
                int index = 0;
                for (String s : strList) {
                    Object obj = TypeEnum.doAnalyse(cls, s);
                    Array.set(arr, index, obj);
                    index++;
                }

                return arr;
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("无法将字符串[{}]转成数组", str, e);
                throw new IllegalStateException(message.getMessage());
            }
        }
    },

    ENUM(null, null) {
        @Override
        public Object analyse(Object o, String str) {

            try {
                // 枚举类型直接引用了spring的转换器
                return conversionService.convert(str, TypeDescriptor.valueOf(String.class), new TypeDescriptor((Field) o));
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("无法将字符串[{}]转成枚举", str, e);
                throw new IllegalStateException(message.getMessage());
            }
        }
    },

    OBJECT_TYPE(null, null) {
        @Override
        public boolean isMatch(String typeName) {
            return true;
        }

        @Override
        public Object analyse(Object o, String param) {

            try {
                // 此处先处理带@的多态情况
                Class<?> cls = getVarietyClass(param);
                if (cls == null) {
                    if (o instanceof Field) {
                        Field f = (Field) o;
                        cls = Class.forName(f.getType().getName());
                    } else {
                        cls = (Class<?>) o;
                    }
                }

                Object obj = cls.getDeclaredConstructor().newInstance();
                List<Field> fields = getFields(cls);
                // 如果有 @ 脱掉 xxx@
                param = cleanAite(param);
                // 数据成员以 - 分割，此处进行切割
                List<String> strList = splitFieldMember(param, '-');
                int index = 0;
                for (Field f : fields) {
                    if (strList.get(index).equals("") || strList.get(index) == null) {
                        index++;
                        continue;
                    }
                    f.setAccessible(true);
                    Object o1 = TypeEnum.doAnalyse(f, strList.get(index));
                    f.set(obj, o1);
                    index++;
                }

                return obj;
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("无法将字符串[{}]转成对象", param, e);
                throw new IllegalStateException(message.getMessage());
            }
        }
    },
    ;
    private String name1;

    private String name2;

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public static ClassMapperManager classMapperManager;

    public static ConversionService conversionService;

    public abstract Object analyse(Object o, String str);

    public boolean isMatch(String typeName) {

        typeName = getRealName(typeName);
        if (getName1() != null && getName1().equals(typeName)) {
            return true;
        }

        if (getName2() != null && getName2().equals(typeName)) {
            return true;
        }

        return false;
    }

    TypeEnum(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    // 取得对应TypeEnum并调用analyse解析出对应对象
    public static Object doAnalyse(Object o, String str) {

        return getTypeEnum(o).analyse(o, str);
    }

    /**
     * 根据Object的类型获取对应的TypeEnum
     *
     * @param obj
     * @return
     */
    private static TypeEnum getTypeEnum(Object obj) {

        String typeName = null;
        TypeEnum anEnum = null;
        if (obj instanceof ParameterizedType) {
            typeName = ((Type) obj).getTypeName();
        } else if (obj instanceof Field) {
            if (((Field) obj).getType().isArray()) {
                anEnum = TypeEnum.ARRAY_TYPE;
            } else {
                typeName = ((Field) obj).getType().getName();
                if (((Field) obj).getType().isEnum()) {
                    anEnum = TypeEnum.ENUM;
                }
            }
        } else {
            Class cls = (Class) obj;
            if (cls.isArray()) {
                anEnum = TypeEnum.ARRAY_TYPE;
            } else {
                typeName = cls.getName();
            }
        }
        if (anEnum == null) {
            for (TypeEnum typeEnum : TypeEnum.values()) {
                if (typeEnum.isMatch(typeName)) {
                    anEnum = typeEnum;
                    break;
                }
            }
        }

        return anEnum;
    }

    /**
     * 获取泛型参数
     */
    public List<Type> getParameterizedType(Object o) {

        List<Type> list = new ArrayList();
        if (o instanceof Field) {
            // 获取f字段的类型
            Type fc = ((Field) o).getGenericType();
            // 如果不为空并且是泛型参数的类型
            if (fc != null && fc instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) fc).getActualTypeArguments();
                if (types != null && types.length > 0) {
                    for (int i = 0; i < types.length; i++) {
                        list.add(types[i]);
                    }
                }
            }
        }

        // 此处兼容当o为List<Object>的时候，如结构 Map<Integer, List<Integer>>
        if (o instanceof Type) {
            Type t = (Type) o;
            if (TypeEnum.getTypeEnum(t) == TypeEnum.LIST_TYPE) {
                list.add(((ParameterizedType) t).getActualTypeArguments()[0]);
            }
        }

        return list;
    }

    public Class<?> getVarietyClass(String param) {

        Class<?> cls = null;
        if (param.contains("@")) {
            // 证明 @ 在最外围
            if (!param.split("@")[0].contains("-")) {
                // 多态处理
                cls = TypeEnum.classMapperManager.getMapperClass(param.split("@")[0]);
            }
        }
        return cls;
    }

    /**
     * 字符串切割成成员队列返回，例如：a-{a-b-c}-c，切割完便是 a、{a-b-c}、c 三个成员
     *
     * @param param
     * @param symbol
     * @return
     */
    public List<String> splitFieldMember(String param, char symbol) {

        // 如果有[]脱掉最外围的[]
        param = cleanLargeBrackets(param);

        // 如果有脱掉最外围的{}
        param = cleanMiddleBrackets(param);

        List<String> list = new ArrayList<>();

        int startIndex = -1;
        int leftCount = 0;
        int rightCount = 0;
        for (int current = 0; current < param.length(); current++) {

            char c = param.charAt(current);
            if (startIndex == -1) {
                startIndex = current;
                // 为了兼顾-b-c即a放空的情况
                if (c == symbol) {
                    list.add("");
                    startIndex = -1;
                    continue;
                }
            }

            if (c == '{') {
                leftCount++;
            }

            if (c == '}') {
                rightCount++;
            }

            if (c == symbol) {
                if (leftCount == rightCount) {
                    String fieldStr = param.substring(startIndex, current);
                    if (!fieldStr.equals("")) {
                        list.add(fieldStr);
                    }
                    leftCount = 0;
                    rightCount = 0;
                    startIndex = -1;
                }

                if (current == param.length() - 1) {
                    list.add("");
                    continue;
                }
            }

            if (current == param.length() - 1) {
                String fieldStr = param.substring(startIndex, current + 1);
                if (param.charAt(param.length() - 1) == symbol) {
                    fieldStr = "";
                }
                list.add(fieldStr);
            }
        }

        return list;
    }

    public String cleanAite(String param) {

        if (param.contains("@")) {
            if (!param.split("@")[0].contains("-")) {
                return param.substring(param.split("@")[0].length() + 1, param.length());
            }
        }

        return param;
    }


    /**
     * 去除最外围的 {}
     * 此处要处理好 {}一定为最外围的外衣
     *
     * @param param
     * @return
     */
    public String cleanMiddleBrackets(String param) {

        if (param.charAt(0) != '{') {
            return param;
        }

        int indexLast = 0;
        int left = 0;
        int right = 0;
        for (int e = 0; e <= param.lastIndexOf("}"); e++) {
            if (param.charAt(e) == '{') {
                left++;
            }
            if (param.charAt(e) == '}') {
                right++;
            }
            if (left == right) {
                indexLast = e;
                break;
            }
        }

        if (indexLast == param.length() - 1) {
            return param.substring(1, indexLast);
        }

        return param;
    }

    /**
     * 传进来的都是java.util.List<java.lang,Integer>类型，此处会屏蔽掉<....>的内容
     *
     * @param param
     * @return
     */
    private static String getRealName(String param) {

        if (param.indexOf("<") == -1) {
            return param;
        }
        int s = param.indexOf("<");
        return param.substring(0, s);
    }

    /**
     * 去除最外围的 []
     *
     * @param param
     * @return
     */
    public String cleanLargeBrackets(String param) {

        // 如果有脱掉最外围的[]
        if (!(param.indexOf("[") == 0 && param.lastIndexOf("]") == param.length() - 1)) {
            return param;
        }

        int e = param.lastIndexOf("]");
        int s = param.indexOf("[");
        String substring = param.substring(s + 1, e);
        return substring;
    }

    public List<Field> getFields(Class<?> clz) {

        List<Field> fieldList = new ArrayList<>();
        // 获取所有字段,public和protected和private,但是不包括父类字段
        Collections.addAll(fieldList, clz.getDeclaredFields());
        // 获取父类成员
        while (clz.getSuperclass() != Object.class) {
            clz = clz.getSuperclass();
            Collections.addAll(fieldList, clz.getDeclaredFields());
        }

        return fieldList;
    }
}