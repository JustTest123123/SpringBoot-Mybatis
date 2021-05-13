package springtest;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springtest.xunhuanyilai.A;
import springtest.xunhuanyilai.B;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/23 下午11:49
 */
public class Config implements DisposableBean, BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public String get() {
        System.out.println("Config");
        return "get";
    }

    public static void main(String[] args) {
        Optional s = Optional.empty();
        ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext("classpath:application.xml");
        System.out.println(cpac);
        gets(3, t -> t > 6);

        cpac.start();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 1, 3);
        testPredicate(integers,e -> ( e & 1 )== 0);
        testPredicate(integers,e -> ( e & 1 )== 1);
        testPredicate(integers,e -> e > 3);

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("config destroy method");
    }

    public static void gets(Integer number, Test t) {
        System.out.println(t.app(number));
    }

    public static void testPredicate(List<Integer> list, Predicate<Integer> p) {
        System.out.println(list.stream().filter(e -> p.test(e)).collect(Collectors.toList()));
    }
}
    