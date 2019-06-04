package core.di.factory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;

import java.util.Set;

public class ClasspathBeanDefinitionScanner {
	private final BeanDefinitionRegistry beanDefinitionRegistry;

	public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	@SuppressWarnings("unchecked")
	public void doScan(Object... basePackages) {
		Reflections reflections = new Reflections(basePackages);
		Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class);
		for (Class<?> clazz : beanClasses) {
			beanDefinitionRegistry.registerBeanDefinition(clazz,
					new BeanDefinition(clazz));
		}
	}

	private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<Controller> controllerClass, Class<Service> serviceClass,
			Class<Repository> repositoryClass) {
		return null;
	}
}
