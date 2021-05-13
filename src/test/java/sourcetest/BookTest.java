package sourcetest;

import com.example.controller.anno.LimitAnno;
import com.example.entity.FeildAnnoTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/19 下午4:35
 */
public class BookTest {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.example.entity.FeildAnnoTest");
        Field[] fields = clazz.getDeclaredFields();
        FeildAnnoTest book = (FeildAnnoTest) clazz.getConstructor().newInstance();
        for (Field field : fields) {
            // 判断字段上有无该注解
            if (field.isAnnotationPresent(LimitAnno.class)) {
                String name = field.getName();
                // 获取对应的字段的 set 方法
                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                System.out.println("methodName " + methodName);
                // 获取字段上的注解
                LimitAnno annotation = field.getAnnotation(LimitAnno.class);
                // 获取注解的值，如果注解中写的是 String name()，则通过 annotation.name() 获取
                String value = annotation.value();
                Class<?> type = field.getType();
                Method method = clazz.getMethod(methodName, type);
                System.out.println(type.getName());
                System.out.println("注解值  " + value);
                switch (type.getName()) {
                    case "java.lang.Integer":
                    case "int":
                        method.invoke(book, Integer.parseInt(value));
                        break;
                    case "java.lang.String":
                        method.invoke(book, value);
                        break;
                    case "java.lang.Byte":
                        method.invoke(book, Byte.parseByte(value));
                        break;
                    case "java.lang.Short":
                        method.invoke(book, Short.parseShort(value));
                        break;
                    case "java.lang.Float":
                        method.invoke(book, Float.parseFloat(value));
                        break;
                    case "java.lang.Double":
                        method.invoke(book, Double.parseDouble(value));
                        break;
                    case "java.lang.Long":
                        method.invoke(book, Long.parseLong(value));
                        break;
                    default:
                        method.invoke(book, Integer.valueOf(value));
                }
            }
        }
        System.out.println("book = " + book);
    }
}

    