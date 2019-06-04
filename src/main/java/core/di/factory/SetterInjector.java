package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector {

	public SetterInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override Set<?> getInjectedBeans(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedMethods(clazz);
	}

	@Override Class<?> getBeanClass(Object injectedBean) {
		Method method = (Method) injectedBean;
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 1) {
			throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
		}
		return paramTypes[0];
	}

	@Override void inject(Object injectdBean, Object bean, BeanFactory beanFactory) {
		Method method = (Method) injectdBean;
		try {
			method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

		}
	}
}
