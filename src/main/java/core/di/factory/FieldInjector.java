package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class FieldInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

	public FieldInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override Set<?> getInjectedBeans(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedFields(clazz);
	}

	@Override void inject(Object injectdBean, Object bean, BeanFactory beanFactory) {
		Field field = (Field) injectdBean;
		try {
			field.setAccessible(true);
			field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

	@Override Class<?> getBeanClass(Object injectedBean) {
		Field field = (Field) injectedBean;
		return field.getType();
	}

}
