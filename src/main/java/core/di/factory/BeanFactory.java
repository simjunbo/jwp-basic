package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();
	private List<Injector> injectors;

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;

		injectors = Arrays.asList(
				new FieldInjector(this),
				new SetterInjector(this),
				new ConstructorInjector(this)
		);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public void initialize() {
		for (Class<?> clazz : preInstanticateBeans) {
			if (beans.get(clazz) == null) {
				logger.debug("instantiated Class : {}", clazz);
				inject(clazz);
			}
		}
	}

	private void inject(Class<?> clazz) {
		for (Injector injector : injectors) {
			injector.inject(clazz);
		}
	}
}
