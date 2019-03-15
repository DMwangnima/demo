package com.nuofankj.springdemo.common;

import com.nuofankj.springdemo.anno.Id;
import com.nuofankj.springdemo.anno.Index;
import com.nuofankj.springdemo.utility.ReflectionUtility;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 唯一标识(@Id)获取实例创建器
 */
public class GetterBuilder {

    public static Getter createIdGetter(Class<?> clz) {

        IdentityInfo info = new IdentityInfo(clz);
        Getter getter = null;
        if (info.isField()) {
            getter = new FieldGetter(info.field);
        } else {
            getter = new MethodGetter(info.method);
        }

        return getter;
    }

    public static Map<String, IndexGetter> createIndexGetters(Class<?> clz) {

        Field[] fields = ReflectionUtility.getDeclaredFieldSWith(clz, Index.class);
        Method[] methods = ReflectionUtility.getDeclaredGetMethodsWith(clz, Index.class);
        List<IndexGetter> getters = new ArrayList<>(fields.length + methods.length);
        for (Field field : fields) {
            IndexGetter indexGetter = new FieldIndexGetter(field);
            getters.add(indexGetter);
        }
        for (Method method : methods) {
            IndexGetter getter = new MethodIndexGetter(method);
            getters.add(getter);
        }

        Map<String, IndexGetter> result = new HashMap<>(getters.size());
        for (IndexGetter getter : getters) {
            String name = getter.getName();
            if (result.put(name, getter) != null) {
                FormattingTuple formattingTuple = MessageFormatter.format("资源类[{}]的索引名[{}]重复", clz, name);
                throw new RuntimeException(formattingTuple.getMessage());
            }
        }

        return result;
    }

    private static class IdentityInfo {

        private Field field;
        private Method method;

        public IdentityInfo(Class<?> clz) {

            Field[] fields = ReflectionUtility.getDeclaredFieldSWith(clz, Id.class);
            if (fields.length > 1) {
                FormattingTuple formattingTuple = MessageFormatter.format("类[{}]的属性唯一标志声明重复", clz);
                throw new RuntimeException(formattingTuple.getMessage());
            }
            if (fields.length == 1) {
                this.field = fields[0];
                this.method = null;
                return;
            }

            Method[] methods = ReflectionUtility.getDeclaredGetMethodsWith(clz, Id.class);
            if (methods.length > 1) {
                FormattingTuple formattingTuple = MessageFormatter.format("类[{}]的方法唯一标志声明重复", clz);
                throw new RuntimeException(formattingTuple.getMessage());
            }
            if (methods.length == 1) {
                this.method = methods[0];
                this.field = null;
                return;
            }

            FormattingTuple formattingTuple = MessageFormatter.format("类[{}]缺少唯一标志声明    ", clz);
            throw new RuntimeException(formattingTuple.getMessage());
        }

        public boolean isField() {
            if (field != null) {
                return true;
            }
            return false;
        }
    }

    /**
     * 属性识别器
     */
    private static class FieldGetter implements Getter {

        private Field field;

        public FieldGetter(Field field) {
            ReflectionUtility.makeAccessible(field);
            this.field = field;
        }

        @Override
        public Object getValue(Object value) {
            Object obj = null;
            try {
                obj = field.get(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return obj;
        }

        @Override
        public Class<?> getClz() {
            return field.getType();
        }
    }

    /**
     * f方法识别器
     */
    private static class MethodGetter implements Getter {

        private Method method;

        public MethodGetter(Method method) {
            ReflectionUtility.makeAccessible(method);
        }

        @Override
        public Object getValue(Object value) {
            Object obj = null;
            try {
                obj = method.invoke(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return obj;
        }

        @Override
        public Class<?> getClz() {
            return method.getReturnType();
        }
    }

    private static class MethodIndexGetter extends MethodGetter implements IndexGetter {

        private String name;
        private boolean unique;
        private Comparator comparator;

        public MethodIndexGetter(Method method) {
            super(method);
            Index index = method.getAnnotation(Index.class);
            this.name = index.getName();
            this.unique = index.isUnique();

            Class<Comparator> clz = (Class<Comparator>) index.comparatorClz();
            if (!clz.equals(Comparator.class)) {
                try {
                    this.comparator = clz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                comparator = null;
            }
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean isUnique() {
            return false;
        }

        @Override
        public Comparator getComparator() {
            return null;
        }

        @Override
        public boolean hasComparator() {
            return false;
        }
    }

    /**
     * 属性与索引值获取器
     */
    private static class FieldIndexGetter extends FieldGetter implements IndexGetter {

        private String name;
        private boolean unique;
        private Comparator comparator;

        public FieldIndexGetter(Field field) {
            super(field);
            Index index = field.getAnnotation(Index.class);
            this.name = index.getName();
            this.unique = index.isUnique();
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean isUnique() {
            return false;
        }

        @Override
        public Comparator getComparator() {
            return null;
        }

        @Override
        public boolean hasComparator() {
            return false;
        }
    }
}
