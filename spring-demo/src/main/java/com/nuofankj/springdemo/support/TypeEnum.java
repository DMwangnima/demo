package com.nuofankj.springdemo.support;

import java.lang.reflect.*;
import java.util.*;

/**
 * 分隔符类型：
 * - 对象成员分隔符，格式 1-2
 * , array和list分隔符，格式1,2,3 或者[1,2,3]
 * {} 对象分隔符，在最外围的可以不加，组合对象需加，格式{1-2-3}
 * : ; map类型使用，格式 key:value1;key:value2;
 *
 * @ 多态分隔符，格式 XXX@{1-2-3}
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

            char c = str.charAt(0);
            return c;
        }
    },

    BOOLEAN_TYPE("boolean", "java.lang.Boolean") {
        @Override
        public Object analyse(Object o, String str) {

            Boolean aBoolean = null;
            if (str.equals("1")) {
                aBoolean = Boolean.valueOf(true);
            } else if (str.equals("0")) {
                aBoolean = Boolean.valueOf(false);
            } else {
                System.out.println("boolean类型不匹配");
            }
            return aBoolean;
        }
    },

    LIST_TYPE("java.util.List", null) {
        @Override
        public Object analyse(Object o, String str) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

            // 分割
            List<String> listTmp = splitFieldMember(str, ',');

            // 获取List的泛型类型
            List<Type> parameterizedType = getParameterizedType(o);
            List list = new ArrayList();
            for (String s : listTmp) {
                Object obj = TypeEnum.doAnalyse(parameterizedType.get(0), s);
                list.add(obj);
            }
            return list;
        }
    },

    MAP_TYPE("java.util.Map", null) {
        @Override
        public Object analyse(Object o, String str) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {

            Map map = new HashMap();
            List<String> strList = splitFieldMember(str, ';');

            // parameterizedType 此处为map的泛型参数
            List<Type> parameterizedType = getParameterizedType(o);
            // map key 的类型
            Type keyType = parameterizedType.get(0);
            // map value 的类型
            Type valType = parameterizedType.get(1);

            for (String s : strList) {

                List<String> keyValue = splitFieldMember(s, ':');

                // 键 的处理 命名参数优化出去
                String keyStr = keyValue.get(0);
                Object key = TypeEnum.doAnalyse(keyType, keyStr);

                // 值 的处理
                String valStr = keyValue.get(1);
                Object value = TypeEnum.doAnalyse(valType, valStr);
                map.put(key, value);
            }

            return map;
        }
    },

    OBJECT_TYPE(null, null) {
        @Override
        public Object analyse(Object o, String param) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

            // 此处先处理带@的多态情况
            Class<?> cls = getVarietyClass(param);

            // 数据成员以 - 分割，此处进行切割
            List<String> strList = splitFieldMember(param, '-');

            if (cls == null) {
                if (o instanceof Field) {
                    Field f = (Field) o;
                    cls = Class.forName(f.getType().getName());
                } else {
                    cls = (Class<?>) o;
                }
            }

            Object t = cls.getDeclaredConstructor().newInstance();

            Field[] fields = cls.getDeclaredFields();

            int index = 0;

            for (Field f : fields) {
                if (strList.get(index).equals("") || strList.get(index) == null) {
                    index++;
                    continue;
                }
                f.setAccessible(true);
                Object o1 = TypeEnum.doAnalyse(f, strList.get(index));
                f.set(t, o1);
                index++;
            }
            return t;
        }
    },

    ARRAY_TYPE(null, null) {
        @Override
        public Object analyse(Object o, String str) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

            Class<?> cls;
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

    public abstract Object analyse(Object o, String str) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    TypeEnum(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    // 取得对应TypeEnum并调用analyse解析出对应对象
    public static Object doAnalyse(Object o, String str) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {

        TypeEnum anEnum = getTypeEnum(o);
        Object analyse = anEnum.analyse(o, str);
        return analyse;
    }

    /**
     * 根据Object的类型获取对应的TypeEnum
     *
     * @param o
     * @return
     */
    private static TypeEnum getTypeEnum(Object o) {

        String typeName = null;
        TypeEnum anEnum = null;
        if (o instanceof ParameterizedType) {
            typeName = ((Type) o).getTypeName();
        } else if (o instanceof Field) {
            if (((Field) o).getType().isArray()) {
                anEnum = TypeEnum.ARRAY_TYPE;
            } else {
                typeName = ((Field) o).getType().getName();
            }
        } else {
            Class cls = (Class) o;
            if (cls.isArray()) {
                anEnum = TypeEnum.ARRAY_TYPE;
            } else {
                typeName = cls.getName();
            }
        }
        if (anEnum == null) {
            typeName = getRealName(typeName);
            for (TypeEnum typeEnum : TypeEnum.values()) {
                if (typeEnum.getName1() != null && typeEnum.getName1().equals(typeName)) {
                    anEnum = typeEnum;
                }
                if (typeEnum.getName2() != null && typeEnum.getName2().equals(typeName)) {
                    anEnum = typeEnum;
                }
            }
            if (anEnum == null) {
                anEnum = TypeEnum.OBJECT_TYPE;
            }
        }

        return anEnum;
    }

    /**
     * 获取泛型参数
     */
    public List<Type> getParameterizedType(Object o) throws ClassNotFoundException {
        List<Type> list = new ArrayList();
        if (o instanceof Field) {
            Field f = (Field) o;
            // 获取f字段的通用类型
            Type fc = f.getGenericType(); // 关键的地方得到其Generic的类型
            // 如果不为空并且是泛型参数的类型
            if (fc != null && fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) fc;
                Type[] types = pt.getActualTypeArguments();
                if (types != null && types.length > 0) {
                    for (int i = 0; i < types.length; i++) {
                        list.add(types[i]);
                    }
                }
            }
        }

        // 此处兼容当o为List<Object>的时候
        if (o instanceof Type) {
            Type t = (Type) o;
            if (TypeEnum.getTypeEnum(t) == TypeEnum.LIST_TYPE) {
                ParameterizedType type = (ParameterizedType) t;
                list.add(type.getActualTypeArguments()[0]);
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
     * 字符串切割成成员队列返回
     *
     * @param param
     * @param symbol
     * @return
     */
    public List<String> splitFieldMember(String param, char symbol) {

        // 如果有 @ 脱掉 xxx@
        param = cleanAite(param);

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

}