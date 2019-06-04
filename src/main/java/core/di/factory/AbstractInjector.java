package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Set;

public abstract class AbstractInjector implements Injector {
	private BeanFactory beanFactory;

	public AbstractInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public void inject(Class<?> clazz) {
		instantiateClass(clazz);
		Set<?> injectedBeans = getInjectedBeans(clazz);
		for (Object injectedBean : injectedBeans) {
			Class<?> beanClass = getBeanClass(injectedBean);
			inject(injectedBean, instantiateClass(beanClass), beanFactory);
		}
	}

	abstract Set<?> getInjectedBeans(Class<?> clazz);

	abstract Class<?> getBeanClass(Object injectedBean);

	abstract void inject(Object injectdBean, Object bean, BeanFactory beanFactory);

	private Object instantiateClass(Class<?> clazz) {
		return null;
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		return null;
	}
}
